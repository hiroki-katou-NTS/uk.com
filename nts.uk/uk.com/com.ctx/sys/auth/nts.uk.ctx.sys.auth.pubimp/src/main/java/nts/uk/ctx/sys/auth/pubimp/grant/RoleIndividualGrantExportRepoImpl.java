/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.grant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExport;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo;

/**
 * The Class RoleIndividualGrantExportRepoImpl.
 */
@Stateless
public class RoleIndividualGrantExportRepoImpl implements RoleIndividualGrantExportRepo {

	/** The role individual grant repository. */
	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepository;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo#getByUserAndRoleType(java.lang.String, java.lang.Integer)
	 */
	@Override
	public RoleIndividualGrantExport getByUserAndRoleType(String userId, Integer roleType) {
		RoleIndividualGrant roleIndividualGrant = roleIndividualGrantRepository
				.findByUserAndRole(userId, RoleType.valueOf(roleType)).get();
		return new RoleIndividualGrantExport(roleIndividualGrant.getRoleId());
	}

}
