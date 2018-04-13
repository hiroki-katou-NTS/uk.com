package nts.uk.ctx.at.record.ac.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.auth.RoleSetGrantedEmployeeAdapter;
import nts.uk.ctx.sys.auth.pub.grant.RoleSetGrantedEmployeePub;
@Stateless
public class RoleSetGrantedEmployeeAdapterImpl implements RoleSetGrantedEmployeeAdapter {
	@Inject
	RoleSetGrantedEmployeePub roleSetGrantedEmployeePub;

	@Override
	public boolean canApprovalOnBaseDate(String companyId, String employeeID, GeneralDate date) {
		return roleSetGrantedEmployeePub.canApprovalOnBaseDate(companyId, employeeID, date);
	}
}
