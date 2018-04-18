package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

public interface ApproverRepository {

	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	List<Approver> getAllApproverByCode(String companyId, String approvalPhaseId);
	/**
	 * add All Approver
	 * @param lstApprover
	 */
	void addAllApprover(List<Approver> lstApprover);
	/**
	 * delete All Approver By Approval Phase Id
	 * @param companyId
	 * @param approvalPhaseId
	 */
	void deleteAllApproverByAppPhId(String companyId, String approvalPhaseId);
	/**
	 * updateEmployeeIdApprover
	 * @param updateApprover
	 */
	void updateEmployeeIdApprover(Approver updateApprover);
}
