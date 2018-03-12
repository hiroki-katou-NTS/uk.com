package nts.uk.ctx.at.function.dom.adapter.role;

import java.util.List;

public interface RoleIdFromUserAdapter {
	/**
	 * @param userId
	 * @return list roleId 
	 */
	List<String> getRoleIdFromUserId(String userId);
}
