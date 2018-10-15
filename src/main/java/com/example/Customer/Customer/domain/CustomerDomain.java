package com.example.Customer.Customer.domain;

public class CustomerDomain {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;


    public CustomerDomain(){

    }

    public CustomerDomain(String id,String name,int age){
     this.id=id;
     this.name=name;
     this.age=age;

    }
}
