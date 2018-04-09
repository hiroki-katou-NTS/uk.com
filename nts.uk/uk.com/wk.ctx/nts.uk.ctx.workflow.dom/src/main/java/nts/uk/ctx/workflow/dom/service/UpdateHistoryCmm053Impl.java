package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;

/**
 * @author sang.nv
 *
 */
@Stateless
public class UpdateHistoryCmm053Impl implements UpdateHistoryCmm053Service {

	@Inject
	private PersonApprovalRootRepository repoPerson;
	@Inject
	private InsertHistoryCmm053Service insertHistoryCmm053Service;
	
	@Override
	public void updateHistoryByManagerSetting(String companyId, String historyId, String employeeId, GeneralDate startDate,
			String departmentApproverId, String dailyApproverId) {
		String endDate    = "9999-12-31";
		Optional<PersonApprovalRoot> commonPs  = this.repoPerson.getNewestCommonPsAppRoot(companyId, employeeId);
		Optional<PersonApprovalRoot> monthlyPs = this.repoPerson.getNewestMonthlyPsAppRoot(companyId, employeeId);

		if (commonPs.isPresent() && monthlyPs.isPresent()) {
			// １．バラバラ履歴の場合
			if (commonPs.get().getEmploymentAppHistoryItems().get(0).start()
					.compareTo(monthlyPs.get().getEmploymentAppHistoryItems().get(0).start()) != 0) {
				this.insertHistoryCmm053Service.updateOrInsertDiffStartDate(companyId, employeeId, historyId, startDate,
						endDate, commonPs, monthlyPs, departmentApproverId, dailyApproverId);
			}
		} else {
			// ２．一個履歴の場合
			this.insertHistoryCmm053Service.updateOrInsertHistory(companyId, employeeId, historyId, startDate, endDate,
					commonPs, monthlyPs, departmentApproverId, dailyApproverId);
		}
	}
}
