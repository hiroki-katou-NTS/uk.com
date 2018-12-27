package nts.uk.ctx.exio.ac.exo.sys.auth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleExportRepoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.sys.auth.RoleImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

@Stateless
public class RoleExportRpAdapterImpl implements RoleExportRepoAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public Optional<RoleImport> findByRoleId(String roleId) {
		Optional<RoleExport> roleOtp = roleExportRepo.findByRoleId(roleId);
		if (!roleOtp.isPresent())
			return Optional.empty();
		RoleExport role = roleOtp.get();
		return Optional.ofNullable(
				new RoleImport(role.getRoleId(), role.getRoleCode(), role.getRoleName(), role.getAssignAtr()));
	}
}
