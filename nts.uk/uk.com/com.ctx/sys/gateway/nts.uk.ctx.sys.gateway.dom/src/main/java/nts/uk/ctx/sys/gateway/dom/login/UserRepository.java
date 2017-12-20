/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

/**
 * The Interface UserRepository.
 */
public interface UserRepository {

	/**
	 * Gets the by login id.
	 *
	 * @param loginId
	 *            the login id
	 * @return the by login id
	 */
	Optional<User> getByLoginId(String loginId);

	/**
	 * Gets the by associated person id.
	 *
	 * @param associatedPersonId
	 *            the associated person id
	 * @return the by associated person id
	 */
	Optional<User> getByAssociatedPersonId(String associatedPersonId);

}
