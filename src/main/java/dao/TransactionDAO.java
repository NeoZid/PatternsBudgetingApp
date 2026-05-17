package dao;

import java.util.List;
import java.util.Optional;

import model.Transactions;

public interface TransactionDAO {
	Optional<Transactions> getTransactionById(int id); 
	List<Transactions> getTransactionByUser(int userId);
	void saveTransaction(Transactions t);
	void updateTransaction(Transactions t);
	void deleteTransaction(int id);
}
