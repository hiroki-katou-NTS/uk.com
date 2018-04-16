package nts.uk.ctx.sys.auth.pub.grant;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author dxthuong
 * 
 * ユーザIDからロールを取得する
 */
public interface RoleFromUserIdPub {
	
	/**
	 * @param userId
	 * @param roleType
	 * @param baseData
	 * 
	 * @return roleID
	 */
	String getRoleFromUserId(String userId, int roleType, GeneralDate baseDate);
	
	/**
	 * 取得されたロールIDをログインユーザコンテキスト.「就業のロールID」に設定する
	 * @param userID
	 * @param companyID
	 * @param roleType
	 * @return
	 */
	public Optional<String> findByUserCompanyRoleType(String userID, String companyID, int roleType);
	
}
