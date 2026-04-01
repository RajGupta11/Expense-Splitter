package model;

import java.util.List;

public class Expense {
    private String description;
    private double amount;
    private User paidBy;
    private List<User> participants;

    public Expense(String description, double amount, User paidBy, List<User> participants) {
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.participants = participants;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public String getDescription() {
        return description;
    }
}