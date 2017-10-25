/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.grant;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExport;
import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExportRepo;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleIndividualGrantAdapter;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;

/**
 * The Class RoleIndividualGrantAdapterImpl.
 */
@Stateless
public class RoleIndividualGrantAdapterImpl implements RoleIndividualGrantAdapter {

	/** The Role individual grant export repo. */
	@Inject
	private RoleIndividualGrantExportRepo roleIndividualGrantExportRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.login.adapter.RoleIndividualGrantAdapter#
	 * getByUserAndRole(java.lang.String,
	 * nts.uk.ctx.sys.gateway.dom.login.adapter.RoleType)
	 */
	@Override
	public RoleIndividualGrantImport getByUserAndRole(String userId, RoleType roleType) {
		RoleIndividualGrantExport export = roleIndividualGrantExportRepo.getByUserAndRoleType(userId, roleType.value);
		return new RoleIndividualGrantImport(export.getRoleId());
	}
}
