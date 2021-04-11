package in.ashwinisurve.expenseManager.service;

import java.util.List;

import in.ashwinisurve.expenseManager.model.Expense;
import in.ashwinisurve.expenseManager.model.RecurringExpense;

public interface ExpenseService {

	List<Expense>findAll();
	
	Expense save(Expense expense);
	
	List<Expense> saveRecurring(RecurringExpense recurringExpense);
	
	Expense findById(Long id);
	
	void delete(Long id);

	List<Expense> findExpensesByUserId(Long userId);
}
