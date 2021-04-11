package in.ashwinisurve.expenseManager.service;

import java.util.List;

import in.ashwinisurve.expenseManager.model.User;

public interface UserService {
	User save(User user);
	
	List<User> findByEmailId(String emailId);
	
	String getToken(String token);

	void deleteOldUserIfPresent(String emailId);

	void updateUser(String token, Long id);
	
	Long findUserIdByToken(String token);
}
