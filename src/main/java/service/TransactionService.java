package service;

import java.util.List;

import dao.TransacDaoImpl;
import model.Transactions;

public class TransactionService {
	private TransacDaoImpl transacDaoImpl = new TransacDaoImpl();
	
	public double getTotalBalance(int userId) {
		List<Transactions> transactions = transacDaoImpl.getTransactionByUser(userId);
		double totalBalance = transactions.stream().mapToDouble(t -> t.getAmount()).sum();
		
		return totalBalance;
	}
	
	public double getTotalIncome(int userId) {
		List<Transactions> transactions = transacDaoImpl.getTransactionByUser(userId);
		double totalIncome = transactions.stream()
				.filter(t -> t.getAmount()>0)
				.mapToDouble(t -> t.getAmount())
				.sum();
		
		return totalIncome;
	}
	
	public double getTotalExpense(int userId) {
		List<Transactions> transactions = transacDaoImpl.getTransactionByUser(userId);
		double totalExpense = transactions.stream()
				.filter(t -> t.getAmount() < 0)
				.mapToDouble(t -> t.getAmount())
				.sum();
		
		return totalExpense;
	}

	public List<Transactions> getTransactionsByUser(int userId) {
		return transacDaoImpl.getTransactionByUser(userId);
	}

	public boolean saveTransaction(double amount, String date,String description,
                                   String type, int userId, int categoryId) {
		if(amount == 0 || date == null || description.isEmpty()
                || type.isBlank() || userId == 0 || categoryId == 0) {
            return false;
        }

        transacDaoImpl.saveTransaction(new Transactions(amount, date, description, type, userId, categoryId));
        return  true;
	}

	public void deleteTransaction(int transacId) {
		transacDaoImpl.deleteTransaction(transacId);
		
	}

	public boolean updateTransaction(double amount, String date,String description,
                                     String type, int userId, int categoryId) {
        if(amount == 0 || date == null || description.isEmpty()
                || type.isBlank() || userId == 0 || categoryId == 0) {
            return false;
        }

        transacDaoImpl.updateTransaction(new Transactions(amount, date,
                description, type, userId, categoryId));
        return true;
	}
}
