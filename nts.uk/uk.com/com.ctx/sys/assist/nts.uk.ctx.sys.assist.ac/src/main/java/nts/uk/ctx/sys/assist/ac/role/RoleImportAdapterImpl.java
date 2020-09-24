package nts.uk.ctx.sys.assist.ac.role;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.role.RoleImportAdapter;
import nts.uk.ctx.sys.assist.dom.role.RoleImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

@Stateless
public class RoleImportAdapterImpl implements RoleImportAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Override
	public Optional<RoleImport> findByRoleId(String roleId) {
		return roleExportRepo.findByRoleId(roleId)
				.map(r -> new RoleImport(r.getRoleId(), r.getAssignAtr()));
	}
}
