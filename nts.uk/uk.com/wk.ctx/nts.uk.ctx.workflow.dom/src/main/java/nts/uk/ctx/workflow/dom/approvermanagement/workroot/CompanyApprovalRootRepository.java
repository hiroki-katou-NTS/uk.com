package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface CompanyApprovalRootRepository {

	/**
	 * get All Company Approval Root
	 * @param companyId
	 * @return
	 */
	List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId);
	/**
	 * get ComApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 * @return
	 */
	Optional<CompanyApprovalRoot> getComApprovalRoot(String companyId, String approvalId, String historyId);
	/**
	 * get Company Approval Root By End date
	 * @param companyId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType);
	/**
	 * add Company Approval Root
	 * @param comAppRoot
	 */
	void addComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * update Company Approval Root
	 * @param comAppRoot
	 */
	void updateComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * delete Company Approval Root
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 */
	void deleteComApprovalRoot(String companyId, String approvalId, String historyId);
}
