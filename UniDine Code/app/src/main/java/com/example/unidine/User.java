package com.example.unidine;

public class User {
    String name;
    String email;
    int age;
    int radius;
    String food;
    boolean isAccountSetup;





    public User(String e, String n) {
        this.email = e;
        this.name = n;
        this.age = 0;
        this.radius = 0;
        this.food = "";
        this.isAccountSetup = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) { this.food = food;}

    public void setAccountSetup(boolean b) { this.isAccountSetup = b;}

    public boolean getAccountSetup() {
        return isAccountSetup;
    }
}
