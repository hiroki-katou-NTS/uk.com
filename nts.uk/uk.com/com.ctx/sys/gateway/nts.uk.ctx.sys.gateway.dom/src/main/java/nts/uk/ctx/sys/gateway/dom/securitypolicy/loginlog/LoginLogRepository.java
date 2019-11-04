package nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog;


import java.util.List;

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
	/**
	 * delete Login Log
	 * @param ユーザID userId
	 * @param 成功失敗区分 successOrFail
	 * @param 操作区分 operation
	 */
	void deleteLoginLog(String userId,int successOrFail, int operation);
	/**
	 * delete List Login Log
	 * @param ユーザID一覧 lstUserId
	 * @param 成功失敗区分 successOrFail
	 * @param 操作区分 operation
	 */
	void deleteLstLoginLog(List<String> lstUserId,int successOrFail, int operation);
}
