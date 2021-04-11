package in.ashwinisurve.expenseManager.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashwinisurve.expenseManager.model.User;
import in.ashwinisurve.expenseManager.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
	@PostMapping("/authenticateUser")
	public ResponseEntity<String> save(@RequestBody User user, @RequestHeader("Authorization") String token) {
		User updatedUser = user;
		String updatedToken = token.split(" ")[1];
		updatedUser.setToken(updatedToken);
		List<User> savedUser = userService.findByEmailId(updatedUser.getEmailId());
		//logger.info("FindByEmailId: " + savedUser + savedUser.get(0).getId());
		if(savedUser.size() > 0) {
			logger.info("FindByEmailId: " + updatedToken + savedUser.get(0).getId());
			userService.updateUser(updatedToken, savedUser.get(0).getId());
		} else {
			userService.save(updatedUser);
		}
		
		return new ResponseEntity<String>("User saved successfully",HttpStatus.OK);
	}
}
