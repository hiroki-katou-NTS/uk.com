package nts.uk.ctx.sys.auth.pub.grant;

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
	
}
