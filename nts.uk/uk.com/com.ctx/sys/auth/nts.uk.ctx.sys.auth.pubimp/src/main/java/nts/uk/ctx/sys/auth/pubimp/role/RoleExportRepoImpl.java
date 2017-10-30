/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.role;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

/**
 * The Class RoleExportRepoImpl.
 */
//@Stateless
public class RoleExportRepoImpl implements RoleExportRepo{

	/** The role repo. */
//	@Inject
	private RoleRepository roleRepo;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.pub.role.RoleExportRepo#findById(java.lang.String)
	 */
	@Override
	public RoleExport findById(String roleId) {
		Optional<Role> opRole = roleRepo.findById(roleId);
		if (opRole.isPresent()) {
			Role role = opRole.get();
			return new RoleExport(role.getRoleId(), role.getRoleCode().v(), role.getName().v());
		}
		return null;
	}

}
