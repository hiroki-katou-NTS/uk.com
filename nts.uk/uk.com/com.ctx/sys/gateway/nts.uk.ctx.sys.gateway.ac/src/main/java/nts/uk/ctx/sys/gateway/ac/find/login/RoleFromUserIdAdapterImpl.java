package nts.uk.ctx.sys.gateway.ac.find.login;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter;

/**
 * The Class RoleFromUserIdAdapterImpl.
 */
@Stateless
public class RoleFromUserIdAdapterImpl implements RoleFromUserIdAdapter {

	/** The role from user id pub. */
	@Inject
	private RoleFromUserIdPub roleFromUserIdPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.adapter.RoleFromUserIdAdapter#getRoleFromUser(java.lang.String, java.lang.Integer, nts.arc.time.GeneralDate)
	 */
	@Override
	public String getRoleFromUser(String userId, Integer roleType, GeneralDate baseDate) {
		String roleId = roleFromUserIdPub.getRoleFromUserId(userId, roleType, baseDate);
		return roleId;
	}
}
