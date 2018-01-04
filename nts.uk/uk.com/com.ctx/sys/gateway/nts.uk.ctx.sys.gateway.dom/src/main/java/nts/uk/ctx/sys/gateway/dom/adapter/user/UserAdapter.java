/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.user;

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
}
