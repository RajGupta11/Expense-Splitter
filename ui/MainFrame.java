package ui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import model.*;
import service.ExpenseService;

public class MainFrame extends JFrame {

    private Group group;
    private JTextArea output;
    private JTextField userField, descField, amountField;
    private JComboBox<User> payerBox;

    public MainFrame() {
        group = new Group("Trip");

        setTitle("Smart Expense Splitter");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(2,1));

        // USER PANEL
        JPanel userPanel = new JPanel();
        userField = new JTextField(10);
        JButton addUserBtn = new JButton("Add User");

        userPanel.add(new JLabel("User:"));
        userPanel.add(userField);
        userPanel.add(addUserBtn);

        // EXPENSE PANEL
        JPanel expensePanel = new JPanel();
        descField = new JTextField(10);
        amountField = new JTextField(6);
        payerBox = new JComboBox<>();
        JButton addExpenseBtn = new JButton("Add Expense");

        expensePanel.add(new JLabel("Desc:"));
        expensePanel.add(descField);
        expensePanel.add(new JLabel("Amount:"));
        expensePanel.add(amountField);
        expensePanel.add(new JLabel("Paid By:"));
        expensePanel.add(payerBox);
        expensePanel.add(addExpenseBtn);

        top.add(userPanel);
        top.add(expensePanel);

        // OUTPUT AREA (FIXED \n ISSUE)
        output = new JTextArea();
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(output);

        JButton showBtn = new JButton("Show Balances");

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(showBtn, BorderLayout.SOUTH);

        JButton clearBtn = new JButton("Clear");
clearBtn.addActionListener(e -> output.setText(""));

        // ✅ ADD USER (FIXED EMPTY + NEW LINE)
        addUserBtn.addActionListener(e -> {
            String name = userField.getText().trim();

            if (name.isEmpty()) {
                output.append("Enter valid name\n");
                return;
            }

            User user = new User(name);
            group.addMember(user);
            payerBox.addItem(user);

            output.append("Added user: " + name + "\n"); // FIXED newline
            userField.setText("");
        });

        // ✅ ADD EXPENSE (FIXED INPUT + FORMAT)
        addExpenseBtn.addActionListener(e -> {
            try {
                String desc = descField.getText().trim();
                String amtText = amountField.getText().trim();

                if (desc.isEmpty() || amtText.isEmpty()) {
                    output.append("Enter all fields\n");
                    return;
                }

                double amt = Double.parseDouble(amtText);
                User payer = (User) payerBox.getSelectedItem();

                if (payer == null) {
                    output.append("Add users first\n");
                    return;
                }

                Expense exp = new Expense(desc, amt, payer, group.getMembers());
                group.addExpense(exp);

                output.append("Expense added: " + desc + " Rs." + String.format("%.2f", amt) + "\n");
                if(desc.toLowerCase().contains("pizza")) {
    output.append("(Category: Food)\n");
}

               if(desc.toLowerCase().contains("pizza")) {
    output.append("(Category: Food)\n");
}
            } catch (Exception ex) {
                output.append("Enter valid amount (number only)\n");
            }
        });

        // ✅ SHOW BALANCES (FIXED ₹ + DECIMAL)
        showBtn.addActionListener(e -> {
            Map<String, Double> balances = ExpenseService.calculateBalances(group);

            output.append("\n----- Balances -----\n");

            balances.forEach((k, v) ->
                output.append(k + ": Rs." + String.format("%.2f", v) + "\n")
            );

            output.append("\n----- Settlements -----\n");

            for (String s : ExpenseService.simplifyDebts(balances)) {
                output.append(s + "\n");
            }
        });
    }
}