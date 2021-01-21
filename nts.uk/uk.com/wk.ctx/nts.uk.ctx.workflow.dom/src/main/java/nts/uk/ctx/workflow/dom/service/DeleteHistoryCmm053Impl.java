package nts.uk.ctx.workflow.dom.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;

@Stateless
public class DeleteHistoryCmm053Impl implements DeleteHistoryCmm053Service {

	@Inject
	private PersonApprovalRootRepository repoPerson;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
//	@Inject
//	private ApprovalBranchRepository repoBranch;
	
	@Inject
	private CreateDailyApprover createDailyApprover;

	@Override
	public void deleteHistoryByManagerSetting(GeneralDate startDate, GeneralDate endDate, String companyId,
			String employeeId) {
		GeneralDate endDatePrevious = startDate.addDays(-1);
		List<PersonApprovalRoot> getPastHistory = this.repoPerson.getPastHistory(companyId, employeeId);
		Map<GeneralDate, List<PersonApprovalRoot>> groupedPsApproval = getPastHistory.stream()
				.collect(Collectors.groupingBy(item -> item.getApprRoot().getHistoryItems().get(0).end()));

		if (groupedPsApproval.containsKey(endDate)) {
			List<PersonApprovalRoot> deletePersonApproval = groupedPsApproval.get(endDate);
			for (PersonApprovalRoot deleteItem : deletePersonApproval) {
				String approvalId = deleteItem.getApprovalId();
				String historyId  = deleteItem.getApprRoot().getHistoryItems().get(0).getHistoryId();
				Optional<ApprovalPhase> approvalPhase = this.repoAppPhase.getApprovalFirstPhase(approvalId);
				if (approvalPhase.isPresent()) {
					int phaseOrder = approvalPhase.get().getPhaseOrder();
					// 「個人別就業承認ルート」に紐付く「分岐」「承認ルート」を削除する
					//Delete table Approver
					this.repoApprover.deleteAllApproverByAppPhId(approvalId, phaseOrder);
					//Delete table Approver
					this.repoAppPhase.deleteAllAppPhaseByApprovalId(approvalId);
					//Delete table Branch
					// this.repoBranch.deleteBranch(companyId, deleteItem.getApprRoot().getBranchId());
				}
				// 「個人別就業承認ルート」を削除する
				this.repoPerson.deletePsApprovalRoot(companyId, approvalId, employeeId, historyId);
			}
		}

		List<PersonApprovalRoot> currentApprovalRoot  = repoPerson.getByEndDate(companyId, employeeId, SystemAtr.WORK.value, endDate);
		List<PersonApprovalRoot> previousApprovalRoot = repoPerson.getByEndDate(companyId, employeeId, SystemAtr.WORK.value, endDatePrevious);
		// 削除した履歴の直前の「個人別就業承認ルート」が存在するかチェックする
		if (currentApprovalRoot.isEmpty() && !previousApprovalRoot.isEmpty()) {
			for (PersonApprovalRoot updateItem : previousApprovalRoot) {
				// 削除した履歴の直前のドメインモデル「就業承認ルート履歴」を更新する。
				PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateEdate(updateItem, endDate.toString().replace("/", "-"));
				repoPerson.updatePsApprovalRoot(psAppRoot);
			}
		}
		createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_DAY, startDate, startDate);
		createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_MONTH, startDate, startDate);
	}
}
