package service;

import java.util.*;
import model.*;

public class ExpenseService {

    public static Map<String, Double> calculateBalances(Group group) {
        Map<String, Double> balances = new HashMap<>();

        for (User user : group.getMembers()) {
            balances.put(user.getName(), 0.0);
        }

        for (Expense exp : group.getExpenses()) {
            double split = exp.getAmount() / exp.getParticipants().size();

            for (User user : exp.getParticipants()) {
                if (!user.getName().equals(exp.getPaidBy().getName())) {
                    balances.put(user.getName(),
                            balances.get(user.getName()) - split);

                    balances.put(exp.getPaidBy().getName(),
                            balances.get(exp.getPaidBy().getName()) + split);
                }
            }
        }
        return balances;
    }

    public static List<String> simplifyDebts(Map<String, Double> balances) {
        List<String> result = new ArrayList<>();

        List<Map.Entry<String, Double>> creditors = new ArrayList<>();
        List<Map.Entry<String, Double>> debtors = new ArrayList<>();

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            if (entry.getValue() > 0) creditors.add(entry);
            else if (entry.getValue() < 0) debtors.add(entry);
        }

        for (Map.Entry<String, Double> debtor : debtors) {
            double debt = -debtor.getValue();

            for (Map.Entry<String, Double> creditor : creditors) {
                if (creditor.getValue() == 0) continue;

                double settle = Math.min(debt, creditor.getValue());

                if (settle > 0) {
                    result.add(debtor.getKey() + " pays " +
        creditor.getKey() + " Rs." +
        String.format("%.2f", settle));
                    debt -= settle;
                    creditor.setValue(creditor.getValue() - settle);
                }

                if (debt == 0) break;
            }
        }

        return result;
    }
}