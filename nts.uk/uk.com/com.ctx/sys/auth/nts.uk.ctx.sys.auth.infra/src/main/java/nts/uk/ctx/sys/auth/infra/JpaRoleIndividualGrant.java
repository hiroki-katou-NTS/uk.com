/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

@Stateless
public class JpaRoleIndividualGrant implements RoleIndividualGrantRepository{

	@Override
	public Optional<RoleIndividualGrant> findByUserAndRole(String userId, RoleType roleType) {
		return null;
	}

}
