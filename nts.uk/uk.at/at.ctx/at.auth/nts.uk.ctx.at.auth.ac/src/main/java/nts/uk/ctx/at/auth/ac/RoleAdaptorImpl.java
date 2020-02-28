package nts.uk.ctx.at.auth.ac;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.dom.adapter.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.adapter.RoleImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

@Stateless
public class RoleAdaptorImpl implements RoleAdaptor {

	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Override
	public Optional<RoleImport> findByRoleId(String roleId) {
		return roleExportRepo.findByRoleId(roleId).map(x -> new RoleImport(
				x.getCompanyId(), 
				x.getRoleId(), 
				x.getRoleCode(), 
				x.getRoleName(), 
				x.getAssignAtr(), 
				x.getEmployeeReferenceRange()));
	}

}
