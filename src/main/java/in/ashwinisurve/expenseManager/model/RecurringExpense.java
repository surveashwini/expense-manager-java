package in.ashwinisurve.expenseManager.model;

import java.math.BigDecimal;

public class RecurringExpense {
	private BigDecimal recurringAmount;
	private String recurringExpense;
	private String duration;
	private Long userId;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRecurringExpense() {
		return recurringExpense;
	}
	public void setRecurringExpense(String recurringExpense) {
		this.recurringExpense = recurringExpense;
	}
	public BigDecimal getRecurringAmount() {
		return recurringAmount;
	}
	public void setRecurringAmount(BigDecimal recurringAmount) {
		this.recurringAmount = recurringAmount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	} 
}
