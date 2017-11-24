package nts.uk.ctx.sys.auth.dom.user;

import java.util.Optional;

public interface UserRepository {
	/**
	 * Gets the by login id.
	 *
	 * @param loginId the login id
	 * @return the by login id
	 */
	Optional<User> getByLoginId(String loginId); 
	
	/**
	 * Gets the by associated person id.
	 *
	 * @param associatedPersonId the associated person id
	 * @return the by associated person id
	 */
	Optional<User> getByAssociatedPersonId(String associatedPersonId); 
	
	Optional<User> getByUserID(String userID);
	
	
	
}