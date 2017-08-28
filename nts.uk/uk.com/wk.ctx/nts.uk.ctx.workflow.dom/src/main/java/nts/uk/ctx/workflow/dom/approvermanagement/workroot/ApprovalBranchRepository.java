package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Optional;

public interface ApprovalBranchRepository {

	/**
	 * get Approval Branch
	 * @param companyId
	 * @param branchId
	 * @param number
	 * @return
	 */
	Optional<ApprovalBranch> getApprovalBranch(String companyId, String branchId, int number);
}
