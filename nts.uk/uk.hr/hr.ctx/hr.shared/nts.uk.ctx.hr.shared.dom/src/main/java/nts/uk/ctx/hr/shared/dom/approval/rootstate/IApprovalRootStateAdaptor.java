package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface IApprovalRootStateAdaptor {
	
	public ApprovalRootContentHrExport getApprovalRootHr(String companyID, String employeeID, String targetType, GeneralDate date, Optional<Boolean> lowerApprove);


}
