package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

public interface ApprovalPhaseRepository {

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId);
	
	/**
	 * get All Approval Phase by Code include approvers
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhase> getAllIncludeApprovers(String companyId, String branchId);
	
	/**
	 * add All Approval Phase
	 * @param lstAppPhase
	 */
	void addAllApprovalPhase(List<ApprovalPhase> lstAppPhase);
	/**
	 * delete All Approval Phase By Branch Id
	 * @param companyId
	 * @param branchId
	 */
	void deleteAllAppPhaseByBranchId(String companyId, String branchId);
	
}
