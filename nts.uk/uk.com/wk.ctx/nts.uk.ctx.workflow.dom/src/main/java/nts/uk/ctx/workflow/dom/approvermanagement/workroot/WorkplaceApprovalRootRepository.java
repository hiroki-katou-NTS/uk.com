package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkplaceApprovalRootRepository {

	/**
	 * get All Workplace Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @return
	 */
	List<WorkplaceApprovalRoot> getAllWpApprovalRoot(String companyId, String workplaceId);
	/**
	 * get WpApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param workplaceId
	 * @param historyId
	 * @return
	 */
	Optional<WorkplaceApprovalRoot> getWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId);
	/**
	 * get Workplace Approval Root By End date
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, GeneralDate endDate, Integer applicationType);
	/**
	 * add Workplace Approval Root
	 * @param wpAppRoot
	 */
	void addWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * update Workplace Approval Root
	 * @param wpAppRoot
	 */
	void updateWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @param historyId
	 */
	void deleteWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId);
}
