package nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog;


import nts.arc.time.GeneralDateTime;

/**
 * The Interface LoginLogRepository.
 */
public interface LoginLogRepository {
	
	/**
	 * Gets the login log by conditions.
	 *
	 * @param userId the user id
	 * @param startTime the start time
	 * @return the login log by conditions
	 */
	Integer getLoginLogByConditions(String userId, GeneralDateTime startTime);
	
	/**
	 * Adds the.
	 *
	 * @param loginLog the login log Dto
	 */
	void add(LoginLog loginLog);

}
