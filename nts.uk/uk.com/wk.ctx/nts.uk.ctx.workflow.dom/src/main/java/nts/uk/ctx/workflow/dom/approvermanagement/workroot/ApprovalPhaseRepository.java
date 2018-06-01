package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

public interface ApprovalPhaseRepository {

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId);
	/**
	 * get Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @param approvalPhaseId
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalPhase(String companyId, String branchId, String approvalPhaseId);
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
	 * delete All Approval Phase By Branch Id
	 * @param companyId
	 * @param branchId
	 */
	void deleteAllAppPhaseByBranchId(String companyId, String branchId);
	/**
	 * delete Approval Phase By ApprovalPhaseId
	 * @param companyId
	 * @param branchId
	 * @param approvalPhaseId
	 */
	void deleteAppPhaseByAppPhId(String companyId, String branchId, String approvalPhaseId);
	/**
	 * Get approval first phase by branchId
	 * @param companyId
	 * @param branchId
	 * @param displayOrder
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalFirstPhase(String companyId, String branchId);
}
