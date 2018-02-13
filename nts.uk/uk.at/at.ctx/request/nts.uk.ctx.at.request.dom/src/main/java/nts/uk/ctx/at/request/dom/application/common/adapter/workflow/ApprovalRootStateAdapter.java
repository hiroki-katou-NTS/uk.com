package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApprovedImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverPersonImport;

public interface ApprovalRootStateAdapter {
	
	public ApprovalRootContentImport_New getApprovalRootContent(String companyID, String employeeID, Integer appTypeValue, GeneralDate appDate, String appID, Boolean isCreate);
	
	public void insertByAppType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID);
	
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue, GeneralDate appDate);
	
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate, String memo);
	
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate);
	
	public void doReleaseAllAtOnce(String companyID, String rootStateID);
	
	public ApproverApprovedImport_New getApproverApproved(String rootStateID); 
	
	public AgentPubImport getApprovalAgencyInformation(String companyID, List<String> approver);
	
	public List<String> getMailNotifierList(String companyID, String rootStateID);
	
	public void deleteApprovalRootState(String rootStateID);
	
	public Boolean doRelease(String companyID, String rootStateID, String employeeID);
	
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo);
	
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID);
	
	public ApproverPersonImport judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID);
	
}
