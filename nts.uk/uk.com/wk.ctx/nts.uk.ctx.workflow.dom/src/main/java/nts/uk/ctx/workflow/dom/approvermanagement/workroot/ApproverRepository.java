package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

public interface ApproverRepository {

	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	List<Approver> getAllApproverByCode(String approvalId, int phaseOrder);
	/**
	 * add All Approver
	 * @param lstApprover
	 */
	void addAllApprover(String approvalId, int phaseOrder, List<Approver> lstApprover);
	/**
	 * delete All Approver By Approval Phase Id
	 * @param companyId
	 * @param approvalPhaseId
	 */
	void deleteAllApproverByAppPhId(String approvalId, int phaseOrder);
	/**
	 * updateEmployeeIdApprover
	 * @param updateApprover
	 */
	void updateEmployeeIdApprover(String approvalId, int phaseOrder, Approver updateApprover);
}
