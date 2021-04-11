package in.ashwinisurve.expenseManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.ashwinisurve.expenseManager.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query("SELECT exp FROM Expense exp where exp.userId = :userId") 
	List<Expense> findExpensesByUserId(Long userId);

}
