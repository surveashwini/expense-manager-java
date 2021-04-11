package in.ashwinisurve.expenseManager.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.ashwinisurve.expenseManager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u.token FROM User u where u.token = :token") 
    String findByToken(@Param("token") String token);
	
	@Query("SELECT u FROM User u where u.emailId = :emailId") 
	List<User> findByEmailId(@Param("emailId") String emailId);
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET token = :token WHERE (id = :id)") 
	void updateUser(@Param("token") String token, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM User u where u.emailId = :emailId") 
    void deleteUsersByEmailId(@Param("emailId") String emailId);

	@Query("SELECT u.id FROM User u where u.token = :token") 
	Long findUserIdByToken(@Param("token") String token);
}
