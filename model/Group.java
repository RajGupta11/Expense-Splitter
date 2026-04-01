package model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<User> members = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public String getName() {
        return name;
    }
}