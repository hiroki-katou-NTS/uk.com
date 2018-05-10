package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApprovedImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverPersonImport;

public interface ApprovalRootStateAdapter {
	/**
	 * RequestList113
	 * @param startDate
	 * @param endDate
	 * @param employeeID
	 * @param companyID
	 * @param rootType
	 * @return
	 */
	public List<ApproveRootStatusForEmpImPort> getApprovalByEmplAndDate(GeneralDate startDate, GeneralDate endDate, String employeeID,String companyID,Integer rootType);
	
	public Map<String,List<ApprovalPhaseStateImport_New>> getApprovalRootContents(List<String> appIDs,String companyID);
	
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
	
	public List<String> doRemandForApprover(String companyID, String rootStateID, Integer order);

	public void doRemandForApplicant(String companyID, String rootStateID);
	
}
