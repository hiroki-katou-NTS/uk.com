package nts.uk.ctx.workflow.pub.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.service.export.ApplicationTypeExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStatePub {
	
	public ApprovalRootContentExport getApprovalRoot(String companyID, String employeeID, Integer appTypeValue, GeneralDate date);
	
	public void insertAppRootType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String historyID, String appID);
	
}
