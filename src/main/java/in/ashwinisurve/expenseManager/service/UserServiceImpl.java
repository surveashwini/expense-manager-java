package in.ashwinisurve.expenseManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashwinisurve.expenseManager.model.User;
import in.ashwinisurve.expenseManager.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User save(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	public String getToken(String token) {
		return userRepository.findByToken(token);
	}

	@Override
	public List<User> findByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	@Override
	public void deleteOldUserIfPresent(String emailId) {
		userRepository.deleteUsersByEmailId(emailId);
	}

	@Override
	public void updateUser(String token, Long id) {
		userRepository.updateUser(token, id);
	}

	@Override
	public Long findUserIdByToken(String token) {
		return userRepository.findUserIdByToken(token);
	}
	
}
