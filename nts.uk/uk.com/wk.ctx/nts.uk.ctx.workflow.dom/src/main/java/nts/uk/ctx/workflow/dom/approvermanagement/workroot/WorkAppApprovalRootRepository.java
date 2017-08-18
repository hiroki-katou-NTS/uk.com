package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

public interface WorkAppApprovalRootRepository {

	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId);
	/**
	 * get Approval Branch
	 * @param companyId
	 * @param branchId
	 * @param number
	 * @return
	 */
	Optional<ApprovalBranch> getApprovalBranch(String companyId, String branchId, int number);
	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId);
	/**
	 * get All Company Approval Root
	 * @param companyId
	 * @return
	 */
	List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId);
	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	List<Approver> getAllApproverByCode(String companyId, String approvalPhaseId);
}
