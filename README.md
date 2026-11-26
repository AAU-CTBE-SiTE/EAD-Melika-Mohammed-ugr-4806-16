# Lab 9 – Spring Microservices: RestClient (Customer + Order)

**Aim:** get the orders of a given customer by inter-service communication.

Two Spring Boot apps, each with its own database. `customer_service` asks `order_service` for a customer's orders over HTTP, using a `RestClient`.

| Service | Port | Database | Repository |
|---|---|---|---|
| `customer_service` | 8080 | MySQL (`customer_db`, table `customers`) | `CustomerRepo extends JpaRepository<Customer, Long>` |
| `order_service` | 9090 | MongoDB (`order_db`, collection `orders`) | `OrdersRepo extends MongoRepository<Orders, String>` |

```
Postman ──GET /api/customers/{id}/orders──► customer_service :8080 ──► MySQL (find customer)
                                                   │
                                                   │ RestClient
                                                   ▼
                                  order_service :9090 /api/orders/customer/{id} ──► MongoDB
```

## Configuration (application.properties)

**customer_service**

```properties
spring.application.name=customer_service
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.error.include-message=always
```

**order_service**

```properties
spring.application.name=order_service
server.port=9090
spring.data.mongodb.uri=mongodb://localhost:27017/order_db
spring.mongodb.uri=mongodb://localhost:27017/order_db
```

The second Mongo line is for Spring Boot 4, which renamed the property. Boot 3 ignores it.

## Project layout

```
customer_service/src/main/java/com/site/customer_service
├── CustomerServiceApplication.java
├── config/CustomerConfig.java         @Bean RestClient (baseUrl http://localhost:9090)
├── controller/CustomerController.java CRUD + getCustomerOrders (RestClient injected)
├── dto/OrderDto.java                  shape of an order coming back from order_service
├── dto/CustomerOrdersResponse.java    customer + totalOrders + orders
├── entity/Customer.java               @Entity: id, name, email
├── repo/CustomerRepo.java             JpaRepository
└── service/CustomerService.java       save, get all, get by id, update, delete

order_service/src/main/java/com/site/order_service
├── OrderServiceApplication.java
├── controller/OrdersController.java   save, get all, get by customer id
├── entity/Orders.java                 @Document: id, customerId, productName, quantity, price
├── repo/OrdersRepo.java               MongoRepository + findByCustomerId
└── service/OrdersService.java
```

`customer_service` has no Orders entity, because orders aren't stored in MySQL. `OrderDto` is a plain class with the same fields so RestClient can turn the JSON from `order_service` into Java objects.

## How to run

1. Start MySQL (port 3306) and MongoDB (port 27017). The databases get created on first use.
2. Put your own MySQL password in `customer_service/src/main/resources/application.properties`.
3. Run `OrderServiceApplication` (port 9090), then `CustomerServiceApplication` (port 8080).

## Testing in Postman

Import `lab9.postman_collection.json` and run the requests top to bottom. "Save customer" stores the new id in `{{customerId}}` for the other requests.

By hand:

**1. Save a customer** – `POST http://localhost:8080/api/customers`

```json
{ "name": "Abebe Kebede", "email": "abebe@gmail.com" }
```

Response: `{ "id": 1, "name": "Abebe Kebede", "email": "abebe@gmail.com" }`

**2. Get all / get by id** – `GET http://localhost:8080/api/customers` and `GET http://localhost:8080/api/customers/1`

**3. Save orders for customer 1** – `POST http://localhost:9090/api/orders`

```json
{ "customerId": 1, "productName": "Laptop", "quantity": 1, "price": 55000 }
```

**4. Orders straight from order_service** – `GET http://localhost:9090/api/orders/customer/1`

**5. Aim: customer + their orders** – `GET http://localhost:8080/api/customers/1/orders`

```json
{
  "customer": { "id": 1, "name": "Abebe Kebede", "email": "abebe@gmail.com" },
  "totalOrders": 2,
  "orders": [
    { "id": "6745a1c2e4b0f12a3c9d8e71", "customerId": 1, "productName": "Laptop", "quantity": 1, "price": 55000.0 },
    { "id": "6745a1d0e4b0f12a3c9d8e72", "customerId": 1, "productName": "Phone",  "quantity": 2, "price": 18000.0 }
  ]
}
```

`totalOrders` is how many orders the customer placed; `orders` is what they ordered.

## Notes

- `CustomerConfig` sets `baseUrl("http://localhost:9090")`, and `getCustomerOrders` still passes the full URL to `.uri(...)`. When `.uri()` gets a full URL, RestClient uses it as is.
- If `order_service` isn't running, step 5 returns 500 (`Connection refused`).
- An unknown customer id returns 404 with "Customer not found with id: ...".
