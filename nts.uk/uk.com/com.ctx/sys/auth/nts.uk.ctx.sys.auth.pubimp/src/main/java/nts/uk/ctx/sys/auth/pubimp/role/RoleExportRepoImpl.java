/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pubimp.role;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

/**
 * The Class RoleExportRepoImpl.
 */
@Stateless
public class RoleExportRepoImpl implements RoleExportRepo{

	/** The role repo. */
	@Inject
	private RoleRepository roleRepo;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.auth.pub.role.RoleExportRepo#findByListRoleId(java.lang.String, java.util.List)
	 */
	@Override
	public List<RoleExport> findByListRoleId(String companyId, List<String> lstRoleId) {
		List<Role> lstRole = roleRepo.findByListRoleId(companyId, lstRoleId);
		if (!lstRole.isEmpty()) {
			return lstRole.stream().map(role -> {
				return new RoleExport(role.getRoleId(), role.getRoleCode().v(), role.getName().v());
			}).collect(Collectors.toList());
		}
		return null;
	}

}
