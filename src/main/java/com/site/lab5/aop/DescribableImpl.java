package com.site.lab5.aop;

public class DescribableImpl implements Describable {
    @Override
    public String describe() {
        return "This bean was extended by an AOP introduction";
    }
}