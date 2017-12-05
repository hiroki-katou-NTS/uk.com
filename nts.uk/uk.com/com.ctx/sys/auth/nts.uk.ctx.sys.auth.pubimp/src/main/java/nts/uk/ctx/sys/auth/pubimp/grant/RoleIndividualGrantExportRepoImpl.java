/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.grant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExport;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class RoleIndividualGrantExportRepoImpl.
 */
@Stateless
public class RoleIndividualGrantExportRepoImpl implements RoleIndividualGrantExportRepo {

	/** The role individual grant repository. */
	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepository;

	@Override
	public RoleIndividualGrantExport getByUserAndRoleType(String userId, Integer roleType) {
		Optional<RoleIndividualGrant> roleIndividualGrant = roleIndividualGrantRepository
				.findByUserAndRole(userId, RoleType.valueOf(roleType));
		return new RoleIndividualGrantExport(roleIndividualGrant.get().getRoleId());
	}

	@Override
	public RoleIndividualGrantExport getByUser(String userId, GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleIndividualGrantExport getByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
