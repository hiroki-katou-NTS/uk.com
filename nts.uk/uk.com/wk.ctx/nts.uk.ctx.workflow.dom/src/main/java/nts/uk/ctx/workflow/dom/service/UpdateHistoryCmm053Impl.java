package nts.uk.ctx.workflow.dom.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.AppRootInstanceContent;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

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
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	@Inject
	private CreateDailyApprover createDailyApprover;
	
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
			} else{
				this.updateApproverFirstPhase(companyId, dailyApproverId, commonPs.get());
				this.updateApproverFirstPhase(companyId, departmentApproverId, monthlyPs.get());
			}
		} else {
			// ２．一個履歴の場合
			this.insertHistoryCmm053Service.updateOrInsertHistory(companyId, employeeId, historyId, startDate, endDate,
					commonPs, monthlyPs, departmentApproverId, dailyApproverId);
		}
		//履歴の開始日とシステム日付をチェックする
		GeneralDate systemDate = GeneralDate.today();
		if(startDate.beforeOrEquals(systemDate)){
			//指定社員の中間データを作成する（日別）
			AppRootInstanceContent result =  createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_DAY, startDate);
			if(!result.getErrorFlag().equals(ErrorFlag.NO_ERROR)){
				throw new BusinessException(result.getErrorMsgID());
			}
			//指定社員の中間データを作成する（月別）
			result = createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_MONTH, startDate);
			if(!result.getErrorFlag().equals(ErrorFlag.NO_ERROR)){
				throw new BusinessException(result.getErrorMsgID());
			}
		}
		
	}

	@Override
	public void updateApproverFirstPhase(String companyId, String employeeIdApprover, PersonApprovalRoot psAppRoot) {
		Optional<ApprovalPhase> approvalPhase = this.repoAppPhase.getApprovalFirstPhase(companyId,
				psAppRoot.getBranchId());
		if (approvalPhase.isPresent()) {
			ApprovalPhase updateApprovalPhase = approvalPhase.get();
			List<Approver> approverOlds       = updateApprovalPhase.getApprovers();
			Optional<Approver> firstApprover  = approverOlds.stream().filter(x -> x.getOrderNumber() == 0).findFirst();
			if (firstApprover.isPresent()) {
				firstApprover.get().setEmployeeId(employeeIdApprover);
				this.repoApprover.updateEmployeeIdApprover(firstApprover.get());
			}
		}
	}
}
