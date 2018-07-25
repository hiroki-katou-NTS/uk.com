package nts.uk.ctx.sys.auth.dom.registration.user.service;

import nts.arc.time.GeneralDate;

/**
 * The Interface RegistrationUserService.
 */
public interface RegistrationUserService {

	/**
	 * Check system admin.
	 *
	 * @param userID the user ID
	 * @param validityPeriod the validity period
	 * @return true, if successful
	 */
	boolean checkSystemAdmin(String userID, GeneralDate validityPeriod);

	/**
	 * Check password policy.
	 *
	 * @param userId the user id
	 * @param pass the pass
	 * @param contractCode the contract code
	 * @return the check before change pass output
	 */
	CheckBeforeChangePassOutput checkPasswordPolicy(String userId, String pass, String contractCode);

}
