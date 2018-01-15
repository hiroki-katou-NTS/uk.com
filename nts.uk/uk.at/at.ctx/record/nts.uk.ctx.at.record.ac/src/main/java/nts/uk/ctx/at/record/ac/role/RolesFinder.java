package nts.uk.ctx.at.record.ac.role;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.role.Role;
import nts.uk.ctx.at.record.dom.workrecord.role.RoleAdapter;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;

@Stateless
public class RolesFinder implements RoleAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public List<Role> getRolesById(String companyId, List<String> roleIds) {
		return roleExportRepo.findByListRoleId(companyId, roleIds).stream().map(item -> {
			return new Role(item.getRoleId(), item.getRoleCode(), item.getRoleName());
		}).collect(Collectors.toList());
	}

}
