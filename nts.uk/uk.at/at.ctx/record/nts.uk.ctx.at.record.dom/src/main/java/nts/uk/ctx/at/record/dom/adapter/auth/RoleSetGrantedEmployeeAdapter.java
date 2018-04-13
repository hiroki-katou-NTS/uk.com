package nts.uk.ctx.at.record.dom.adapter.auth;

import nts.arc.time.GeneralDate;

public interface RoleSetGrantedEmployeeAdapter {
	public boolean canApprovalOnBaseDate(String companyId, String employeeID, GeneralDate date);
}
