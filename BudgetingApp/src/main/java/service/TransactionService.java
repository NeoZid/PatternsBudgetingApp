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
}
