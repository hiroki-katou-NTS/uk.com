package nts.uk.ctx.sys.gateway.dom.login.adapter;

import nts.arc.time.GeneralDate;

public interface RoleFromUserIdAdapter {

	/**
	 * Gets the role from user.
	 *
	 * @param userId the user id
	 * @param roleType the role type
	 * @param baseDate the base date
	 * @return the role from user
	 */
	String getRoleFromUser(String userId,Integer roleType,GeneralDate baseDate);
	
	String getRoleFromUser(String userId,Integer roleType,GeneralDate baseDate, String comId);
}
