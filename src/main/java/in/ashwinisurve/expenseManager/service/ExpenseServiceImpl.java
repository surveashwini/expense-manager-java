package in.ashwinisurve.expenseManager.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashwinisurve.expenseManager.model.Expense;
import in.ashwinisurve.expenseManager.model.RecurringExpense;
import in.ashwinisurve.expenseManager.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	ExpenseRepository expenseRepository;

	@Override
	public List<Expense> findAll() {
		return expenseRepository.findAll();
	}

	@Override
	public Expense save(Expense expense) {
		expenseRepository.save(expense);
		return expense;
	}
	
	@Override
	public Expense findById(Long id) {
		if(expenseRepository.findById(id).isPresent()) {
			return expenseRepository.findById(id).get();
		}
		return null;
	}
	
	
	@Override
	public void delete(Long id) {
		Expense expense = findById(id);
		expenseRepository.delete(expense);
	}

	@Override
	public List<Expense> saveRecurring(RecurringExpense recurringExpense) {
		int month = LocalDate.now().getMonthValue();
		
		int daysCount = calculateDaysCount(recurringExpense);
		int count = 12 - month;
		count = (daysCount == 7) ? count * 4 : count;
		List<LocalDate> dates = calculateDates(LocalDate.now(), count, daysCount);
		List<Expense> expenses = new ArrayList<Expense>();
		for(int i = 0;i < dates.size(); i++) {
			Expense expenseInstance = new Expense();
			expenseInstance.setExpense(recurringExpense.getRecurringExpense());
			expenseInstance.setAmount(recurringExpense.getRecurringAmount());
			expenseInstance.setExpenseDate(dates.get(i).toString());
			expenseInstance.setUserId(recurringExpense.getUserId());
			expenses.add(expenseInstance);
		}
		expenseRepository.saveAll(expenses);
		return expenses;
	}
	
	private int calculateDaysCount(RecurringExpense recurringExpense) {
		int daysCount = 0;
		if(recurringExpense.getDuration().equals("weekly")) {
			daysCount = 7;
		} else if(recurringExpense.getDuration().equals("monthly")){
			daysCount = 30;
		} else if(recurringExpense.getDuration().equals("yearly")) {
			daysCount = 365;
		}
		return daysCount;
		
	}

	private static List<LocalDate> calculateDates(LocalDate localDate, int count, int daysCount) {
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for(int i = 0;i < count; i++) {
			LocalDate result = localDate.with(getTemporalAdjustersConfig(daysCount));
			dates.add(result);
			localDate = result;
		}
		
		return dates;	
	}

	private static TemporalAdjuster getTemporalAdjustersConfig(int daysCount) {
		if(daysCount == 7 ) {
			return TemporalAdjusters.next(DayOfWeek.MONDAY);
		} else if(daysCount == 30) {
			return TemporalAdjusters.firstDayOfNextMonth();
		} else if(daysCount == 365) {
			return TemporalAdjusters.firstDayOfNextYear();
		}
	    return null;
	}

	@Override
	public List<Expense> findExpensesByUserId(Long userId) {
		return expenseRepository.findExpensesByUserId(userId);
		
	}
	

}
