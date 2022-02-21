package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

public interface ApprovalPhaseRepository {

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String approvalId);

	List<ApprovalPhase> getAllApprovalPhaseByListId(List<String> approvalIds);

	/**
	 * get Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @param phaseOrder
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalPhase(String approvalId, int phaseOrder);
	/**
	 * get All Approval Phase by Code include approvers
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	List<ApprovalPhase> getAllIncludeApprovers(String approvalId);
	
	/**
	 * add All Approval Phase
	 * @param lstAppPhase
	 */
	void addAllApprovalPhase(List<ApprovalPhase> lstAppPhase);
	/**
	 * add Approval Phase
	 * @param appPhase
	 */
	void addApprovalPhase(ApprovalPhase appPhase);
	/**
	 * update Approval Phase
	 * @param appPhase
	 */
	void updateApprovalPhase(ApprovalPhase appPhase);
	/**
	 * delete All Approval Phase By approvalId
	 * @param companyId
	 * @param approvalId
	 */
	void deleteAllAppPhaseByApprovalId(String approvalId);
	/**
	 * delete Approval Phase By phaseOrder
	 * @param companyId
	 * @param approvalId
	 * @param phaseOrder
	 */
	void deleteAppPhaseByAppPhId(String approvalId, int phaseOrder);
	/**
	 * Get approval first phase by approvalId
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalFirstPhase(String approvalId);
}
