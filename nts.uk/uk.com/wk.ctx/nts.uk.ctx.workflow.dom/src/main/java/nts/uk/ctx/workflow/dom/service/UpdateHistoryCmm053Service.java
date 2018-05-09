package nts.uk.ctx.workflow.dom.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;

/**
 * @author sang.nv
 *
 */
public interface UpdateHistoryCmm053Service {
	/**
	 * Update History By Manager Setting
	 * 
	 * @param companyId
	 * @param historyId
	 * @param employeeId
	 * @param startDate
	 * @param departmentApproverId
	 * @param dailyApproverId
	 */
	void updateHistoryByManagerSetting(String companyId, String historyId, String employeeId, GeneralDate startDate,
			String departmentApproverId, String dailyApproverId);

	/**
	 * Update approver first phase
	 * @param companyId
	 * @param employeeIdApprover
	 * @param psAppRoot
	 */
	void updateApproverFirstPhase(String companyId, String employeeIdApprover, PersonApprovalRoot psAppRoot);
}
