package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateService {
	
	public void insertAppRootType(String companyID, String employeeID, ApplicationType appType, GeneralDate date, String historyID, String appID);

	public void update(ApprovalRootState approvalRootState);
	
	public void delete(String rootStateID);
	
}
