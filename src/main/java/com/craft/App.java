package com.craft;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    // Hide Hibernate's INFO/WARN startup messages (they show in red but are not errors).
    // This must come BEFORE the EntityManagerFactory line below.
    private static final Logger HIBERNATE_LOG = Logger.getLogger("org.hibernate");
    static {
        HIBERNATE_LOG.setLevel(Level.SEVERE); // only real errors will still be shown
    }

    // Created ONCE for the whole app (it is expensive to build)
    public static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("studentPU");

    // A new EntityManager for each unit of work
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // =====================================================
    // C - CREATE
    // =====================================================
    public static void createStudent(Student student) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(student);              // INSERT is sent on commit
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // =====================================================
    // R - READ
    // =====================================================

    // Read ONE student by primary key
    public static Student getStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        Student student = entityManager.find(Student.class, id); // null if not found
        entityManager.close();
        return student;
    }

    // Read ALL students (JPQL)
    public static List<Student> getAllStudents() {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Student> query =
                entityManager.createQuery("SELECT s FROM Student s", Student.class);
        List<Student> students = query.getResultList();
        entityManager.close();
        return students;
    }

    // =====================================================
    // U - UPDATE
    // =====================================================

    // Update (1): name only
    public static void updateStudentName(String newName, int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        // find() returns a MANAGED entity: JPA is now watching it
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            System.out.println("No student with id " + id);
            entityManager.getTransaction().rollback();
            entityManager.close();
            return;
        }

        // Just change the field. No persist() or merge() needed.
        student.setName(newName);

        // On commit, Hibernate notices the change ("dirty checking")
        // and runs the UPDATE statement for us.
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Update (2): name AND tech
    public static void updateStudentNameAndTech(String newName, String newTech, int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            System.out.println("No student with id " + id);
            entityManager.getTransaction().rollback();
            entityManager.close();
            return;
        }

        student.setName(newName);
        student.setTech(newTech);   // NOTE: the lab handout says setName(newTech) here - that is a bug

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Update (3): with a JPQL UPDATE query
    public static int updateStudentTech(int id, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Query query = em.createQuery(
                "UPDATE Student s SET s.tech = :newTech WHERE s.id = :id");
        query.setParameter("newTech", newTech);
        query.setParameter("id", id);
        int updatedCount = query.executeUpdate(); // returns how many rows changed

        tx.commit();
        em.close();
        return updatedCount;
    }

    // =====================================================
    // D - DELETE
    // =====================================================
    public static void deleteStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        // remove() only works on a MANAGED entity, so find() it first
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            System.out.println("No student with id " + id);
            entityManager.getTransaction().rollback();
            entityManager.close();
            return;
        }

        entityManager.remove(student);               // DELETE is sent on commit
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // =====================================================
    // DEMO: all four CRUD operations
    // =====================================================
    public static void main(String[] args) {

        // ---------- C: CREATE ----------
        System.out.println("\n========== C: CREATE ==========");
        Student s1 = new Student();
        s1.setName("Mohammed Ali");
        s1.setTech("Python");
        createStudent(s1);

        Student s2 = new Student();
        s2.setName("Firaol Mengistu");
        s2.setTech("Java");
        createStudent(s2);

        System.out.println(">>> Created: " + s1);
        System.out.println(">>> Created: " + s2);

        // ---------- R: READ ----------
        System.out.println("\n========== R: READ ==========");
        System.out.println(">>> One student (id " + s1.getId() + "): " + getStudentById(s1.getId()));
        System.out.println(">>> All students:");
        getAllStudents().forEach(s -> System.out.println("      " + s));

        // ---------- U: UPDATE ----------
        System.out.println("\n========== U: UPDATE ==========");
        updateStudentName("Alemayehu", s1.getId());
        System.out.println(">>> After name update:       " + getStudentById(s1.getId()));

        updateStudentNameAndTech("Abebe Kebede", "Java", s1.getId());
        System.out.println(">>> After name+tech update:  " + getStudentById(s1.getId()));

        int rows = updateStudentTech(s1.getId(), "Spring Boot");
        System.out.println(">>> JPQL updated " + rows + " row(s):   " + getStudentById(s1.getId()));

        // ---------- D: DELETE ----------
        System.out.println("\n========== D: DELETE ==========");
        deleteStudentById(s2.getId());
        System.out.println(">>> Looking for id " + s2.getId() + " after delete: " + getStudentById(s2.getId()));
        System.out.println(">>> All students now:");
        getAllStudents().forEach(s -> System.out.println("      " + s));

        emf.close();
    }
}