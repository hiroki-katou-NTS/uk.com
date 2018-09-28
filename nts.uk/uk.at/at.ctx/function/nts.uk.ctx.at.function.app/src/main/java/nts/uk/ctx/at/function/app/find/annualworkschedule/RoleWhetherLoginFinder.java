package nts.uk.ctx.at.function.app.find.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.role.RoleExportRpAdapter;

@Stateless
public class RoleWhetherLoginFinder {
	@Inject
	private RoleExportRpAdapter roleExportRepoAdapter;

	public RoleWhetherLoginDto getCurrentLoginerRole() {
		RoleWhetherLoginDto role = new RoleWhetherLoginDto();
		role.setEmployeeCharge(roleExportRepoAdapter.getCurrentLoginerRole().isEmployeeCharge());
		return role;
	}
}
