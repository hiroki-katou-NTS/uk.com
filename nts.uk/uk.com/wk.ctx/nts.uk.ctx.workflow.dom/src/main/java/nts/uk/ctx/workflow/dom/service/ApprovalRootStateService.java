package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateService {
	
	public void insertAppRootType(String companyID, String employeeID, ApplicationType appType, GeneralDate date, String appID);
	
	public void delete(String rootStateID);
	
}
