/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.user;

import java.util.List;
import java.util.Optional;


/**
 * The Interface UserAdapter.
 */
public interface UserAdapter {
	
	/**
	 * Find user by contract and login id.
	 *
	 * @param contractCode the contract code
	 * @param loginId the login id
	 * @return the optional
	 */
	Optional<UserImport> findUserByContractAndLoginId(String contractCode, String loginId);
	
	/**
	 * Gets the user by associate id.
	 *
	 * @param associatePersonId the associate person id
	 * @return the user by associate id
	 */
	Optional<UserImport> findUserByAssociateId(String associatePersonId);
	
	
	/**
	 * Gets the list users by list person ids.
	 *
	 * @param listPersonIds the list person ids
	 * @return the list users by list person ids
	 */
	List<UserImport> getListUsersByListPersonIds(List<String> listPersonIds);
	
	
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the optional
	 */
	Optional<UserImport> findByUserId(String userId);
	
	/**
	 * requestlist 313 adapter
	 * @param userId
	 * @return
	 */
	Optional<UserInforExImport> getByEmpID(String empID);

}
