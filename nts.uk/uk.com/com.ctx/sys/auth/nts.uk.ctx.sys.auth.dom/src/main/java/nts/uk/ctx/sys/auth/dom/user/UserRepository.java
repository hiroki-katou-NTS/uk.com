package nts.uk.ctx.sys.auth.dom.user;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface UserRepository {
	/**
	 * Gets the by login id.
	 *
	 * @param loginId the login id
	 * @return the by login id
	 */
	 List<User> getByLoginId(String loginId); 
	
	/**
	 * Gets the by associated person id.
	 *
	 * @param associatedPersonId the associated person id
	 * @return the by associated person id
	 */
	Optional<User> getByAssociatedPersonId(String associatedPersonId); 
	
	Optional<User> getByUserID(String userID);
	
	List<User> findByKey(String key, boolean Special, boolean Multi);
	
	List<User> searchUser(String userIDName , GeneralDate date);
	
	List<User> getByListUser(List<String> userID);
	
	List<User> getAllUser();

	void addNewUser(User newUser);
	
}