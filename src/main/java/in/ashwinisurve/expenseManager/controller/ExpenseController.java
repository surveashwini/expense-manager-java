package in.ashwinisurve.expenseManager.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashwinisurve.expenseManager.model.Expense;
import in.ashwinisurve.expenseManager.model.RecurringExpense;
import in.ashwinisurve.expenseManager.service.ExpenseService;
import in.ashwinisurve.expenseManager.service.UserService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;
	
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(ExpenseController.class.getName());
	
	
	@GetMapping("/expenses")
	public ResponseEntity<List<Expense>> get(@RequestHeader("Authorization") String token) {
		String updatedToken = token.split(" ")[1];
		
		Long userId = userService.findUserIdByToken(updatedToken);
		logger.info("Logger Name: " + userId + updatedToken);
		List<Expense> expenses = expenseService.findExpensesByUserId(userId);
		return new ResponseEntity<List<Expense>>(expenses, HttpStatus.OK);
	}
	
	@PostMapping("/expenses")
	public ResponseEntity<Expense> save(@RequestBody Expense expense,@RequestHeader("Authorization") String token) {
		String updatedToken = token.split(" ")[1];
		
		Long userId = userService.findUserIdByToken(updatedToken);
		logger.info("Logger Name: " + userId + updatedToken);
		expense.setUserId(userId);
		
		LocalDate date = LocalDate.parse(expense.getExpenseDate());
		expense.setExpenseDate(date.toString());
		
		Expense expenseOne = expenseService.save(expense);
		return new ResponseEntity<Expense>(expenseOne, HttpStatus.OK);
	}
	
	@DeleteMapping("/expenses/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		expenseService.delete(id);
		return new ResponseEntity<String>("Expense is deleted successfully", HttpStatus.OK);
	}
	
	@PostMapping("/recurringexpenses")
	public ResponseEntity<List<Expense>> saveRecurring(@RequestBody RecurringExpense recurringExpense,@RequestHeader("Authorization") String token) {
		
		
		String updatedToken = token.split(" ")[1];
		Long userId = userService.findUserIdByToken(updatedToken);
		logger.info("Recurring: " + userId + updatedToken);
		recurringExpense.setUserId(userId);
		List<Expense> expenses = expenseService.saveRecurring(recurringExpense);
		return new ResponseEntity<List<Expense>>(expenses, HttpStatus.OK);
	}

}
