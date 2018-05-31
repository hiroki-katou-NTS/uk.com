package nts.uk.ctx.at.record.app.find.workrecord.authfuncrest;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.workrecord.emplrole.EmploymentRoleAdapter;
import nts.uk.ctx.at.record.dom.workrecord.role.RoleAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentRoleFinder {

	@Inject
	private EmploymentRoleAdapter employmentRoleAdapter;

	@Inject
	private RoleAdapter roleAdapter;

	public List<EmployeeRoleDto> findEmploymentRoles() {
		String companyId = AppContexts.user().companyId();
		List<String> roleIds = employmentRoleAdapter.getEmploymentRoleByCompany(companyId).stream().map(item -> {
			return item.getRoleId();
		}).collect(Collectors.toList());
		if (roleIds.isEmpty()) {
			throw new BusinessException("Msg_398");
		}
		List<EmployeeRoleDto> results = roleAdapter.getRolesById(companyId, roleIds).stream().map(item -> {
			return new EmployeeRoleDto(item.getRoleId(), item.getRoleCode(), item.getRoleName());
		}).collect(Collectors.toList());
		if (results.isEmpty()) {
			throw new BusinessException("Msg_398");
		}
		return results;
	}

}
