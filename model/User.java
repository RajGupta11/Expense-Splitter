package model;

public class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //  IMPORTANT FIX
    @Override
    public String toString() {
        return name;
    }
}