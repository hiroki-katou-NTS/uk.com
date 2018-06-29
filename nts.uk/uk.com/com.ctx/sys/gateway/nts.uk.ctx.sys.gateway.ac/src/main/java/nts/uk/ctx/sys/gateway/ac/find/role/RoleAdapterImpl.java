/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.role;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.grant.RoleIndividualGrantExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleImport;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleIndividualGrantImport;

@Stateless
public class RoleAdapterImpl implements RoleAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public List<RoleImport> getAllById(String roleId) {
		List<RoleExport> listExport = this.roleExportRepo.findById(roleId);
		return listExport.stream().map(c -> {
			return new RoleImport(c.getRoleId(), c.getCompanyId());
		}).collect(Collectors.toList());
	}

}
