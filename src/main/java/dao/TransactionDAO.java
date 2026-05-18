package dao;

import java.util.List;
import java.util.Optional;

import model.Transactions;


/**
 * DAO interface for managing Transaction database operations.
 */
public interface TransactionDAO {
    /**
     * Retrieves a transaction by its ID.
     * @param id the transaction ID
     * @return an Optional containing the transaction if found, empty otherwise
     */
	Optional<Transactions> getTransactionById(int id);
    /**
     * Retrieves all transactions belonging to a specific user.
     * @param userId the user ID
     * @return list of transactions for the user
     */
	List<Transactions> getTransactionByUser(int userId);

    /**
     * Saves a new transaction to the database.
     * @param t the transaction to save
     */
	void saveTransaction(Transactions t);

    /**
     * Updates an existing transaction in the database.
     * @param t the transaction with updated values
     */
	void updateTransaction(Transactions t);
    /**
     * Deletes a transaction by its ID.
     * @param id the ID of the transaction to delete
     */
	void deleteTransaction(int id);
}
