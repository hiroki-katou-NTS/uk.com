package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
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
	/**
	 * add all branch
	 * @param lstBranch
	 */
	void addAllBranch(List<ApprovalBranch> lstBranch);
	/**
	 * delete Branch
	 * @param branchId
	 */
	void deleteBranch(String companyId, String branchId);
}
