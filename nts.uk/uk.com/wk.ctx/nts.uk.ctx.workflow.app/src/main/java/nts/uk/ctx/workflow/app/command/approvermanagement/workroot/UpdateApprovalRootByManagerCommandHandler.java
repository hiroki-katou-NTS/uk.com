package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranchRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateApprovalRootByManagerCommandHandler extends CommandHandler<UpdateApprovalRootByManagerCommand> {

	@Inject
	private PersonApprovalRootRepository repoPerson;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	@Inject
	private ApprovalBranchRepository repoBranch;

	private final int NEW_MODE = 0;
	private final int UPDATE_MODE = 1;

	@Override
	protected void handle(CommandHandlerContext<UpdateApprovalRootByManagerCommand> context) {
		UpdateApprovalRootByManagerCommand command = context.getCommand();
		String historyId            = UUID.randomUUID().toString();
		String departmentApproverId = command.getDepartmentApproverId();
		String dailyApproverId      = command.getDailyApproverId();
		if (!command.isHasAuthority()) {
			dailyApproverId = departmentApproverId;
		}
		if (command.getExecuteMode() == NEW_MODE) {
			this.registerHistory(command, historyId, departmentApproverId, dailyApproverId);
		} else if (command.getExecuteMode() == UPDATE_MODE) {
			// 履歴がある場合
			if (command.isHasHistory()) {
				this.updateHistory(command, historyId, departmentApproverId, dailyApproverId);
			} else {
				this.registerHistory(command, historyId, departmentApproverId, dailyApproverId);
			}
		} else {
			this.deleteHistory(command);
		}
	}

	/**
	 * Register history
	 * @param command
	 * @param historyId
	 * @param departmentApproverId
	 * @param dailyApproverId
	 */
	private void registerHistory(UpdateApprovalRootByManagerCommand command, String historyId,
			String departmentApproverId, String dailyApproverId) {
		String companyId      = AppContexts.user().companyId();
		String employeeId     = command.getEmployeeId();
		GeneralDate startDate = command.getStartDate();
		String endDate        = "9999-12-31";
		List<PersonApprovalRoot> psOlds = this.repoPerson.getPsAppRootLastest(companyId, employeeId, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"));

		if (!psOlds.isEmpty()) {
			PersonApprovalRoot psApp = psOlds.get(0);
			// 追加する履歴を最新の履歴の開始年月日と比較する
			if (startDate.before(psApp.getEmploymentAppHistoryItems().get(0).start())) {
				throw new BusinessException("Msg_153");
			}
		}
		List<PersonApprovalRoot> listPsPre = this.repoPerson.getPsApprovalRootBySdate(companyId, employeeId, startDate);
		List<ApprovalPhase> listApprovalPhase = new ArrayList<>();
		if (!listPsPre.isEmpty()) {
			for (PersonApprovalRoot psApp : listPsPre) {
				Optional<ApprovalPhase> approvalPhase = this.repoAppPhase.getApprovalFirstPhase(companyId,
						psApp.getBranchId());
				if (approvalPhase.isPresent()) {
					listApprovalPhase.add(approvalPhase.get());
				}
			}
		}
		// ドメインモデル「個人別就業承認ルート」．「承認フェーズ」．「順序」をチェックする
		if (!listApprovalPhase.isEmpty()) {
			// ドメインモデル「個人別就業承認ルート」．「承認フェーズ」．「順序」を追加する
			Optional<PersonApprovalRoot> commonPs  = this.repoPerson.getNewestCommonPsAppRoot(companyId, employeeId);
			Optional<PersonApprovalRoot> monthlyPs = this.repoPerson.getNewestMonthlyPsAppRoot(companyId, employeeId);
			this.updateOrInsertHistory(companyId, employeeId, historyId, startDate, endDate, commonPs, monthlyPs,
					departmentApproverId, dailyApproverId);
		} else {
			if (!psOlds.isEmpty()) {
				GeneralDate endDatePrevious = startDate.addDays(-1);
				// 取得したドメインモデル「就業承認ルート履歴」を更新する。（履歴の終了日を追加する履歴の開始日-1日した日付にする）
				for (PersonApprovalRoot updateItem : psOlds) {
					PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateEdate(updateItem,
							endDatePrevious.toString().replace("/", "-"));
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}
			}

			// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, UUID.randomUUID().toString(), null, null, 0);

			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, UUID.randomUUID().toString(), null, 1, 2);

			List<PersonApprovalRoot> insertPersonApproval = new ArrayList<>();
			insertPersonApproval.add(common);
			insertPersonApproval.add(monthly);

			// ドメインモデル「就業承認ルート」と紐付きドメインモデル「分岐」「承認ルート」をINSERTする(INSERT
			// domain「就業承認ルート」và domain 「分岐」「承認ルート」 liên kết)
			this.addHistory(companyId, departmentApproverId, dailyApproverId, insertPersonApproval);
		}
	}

	/**
	 * Add new history
	 * @param companyId
	 * @param departmentApproverId
	 * @param dailyApproverId
	 * @param insertPersonApproval
	 */
	private void addHistory(String companyId, String departmentApproverId, String dailyApproverId,
			List<PersonApprovalRoot> insertPersonApproval) {
		int orderNumber         = 1;
		int approverOrderNumber = 0;
		int approvalAtr         = 0;
		int confirmPerson       = 0;
		int approvalForm        = 1;
		int browsingPhase       = 0;
		String jobTitleId       = null;
		List<ApprovalBranch> lstBranch = new ArrayList<>();

		for (PersonApprovalRoot psAppRoot : insertPersonApproval) {
			String approverId      = UUID.randomUUID().toString();
			String approvalPhaseId = UUID.randomUUID().toString();
			String branchId        = psAppRoot.getBranchId();

			// 承認者
			List<Approver> listApprover = new ArrayList<>();
			String employeeIdApprover = isCommonPsApproval(psAppRoot) ? dailyApproverId : departmentApproverId;
			listApprover.add(Approver.createSimpleFromJavaType(companyId, branchId, approvalPhaseId, approverId,
					jobTitleId, employeeIdApprover, approverOrderNumber, approvalAtr, confirmPerson));
			repoApprover.addAllApprover(listApprover);

			// 承認フェーズ
			repoAppPhase.addApprovalPhase(ApprovalPhase.createSimpleFromJavaType(companyId, branchId, approvalPhaseId,
					approvalForm, browsingPhase, orderNumber, listApprover));

			ApprovalBranch branch = new ApprovalBranch(companyId, branchId, 1);
			lstBranch.add(branch);
		}
		// 分岐
		repoBranch.addAllBranch(lstBranch);

		// ドメインモデル「個人別就業承認ルート」をINSERTする
		this.repoPerson.addAllPsApprovalRoot(insertPersonApproval);
	}

	/**
	 * Update history
	 * @param command
	 * @param historyId
	 * @param departmentApproverId
	 * @param dailyApproverId
	 */
	private void updateHistory(UpdateApprovalRootByManagerCommand command, String historyId, String departmentApproverId, String dailyApproverId) {
		String companyId  = AppContexts.user().companyId();
		String employeeId = command.getEmployeeId();
		String endDate    = "9999-12-31";
		GeneralDate startDate = command.getStartDate();
		Optional<PersonApprovalRoot> commonPs  = this.repoPerson.getNewestCommonPsAppRoot(companyId, employeeId);
		Optional<PersonApprovalRoot> monthlyPs = this.repoPerson.getNewestMonthlyPsAppRoot(companyId, employeeId);

		if (commonPs.isPresent() && monthlyPs.isPresent()) {
			//１．バラバラ履歴の場合
			if (commonPs.get().getEmploymentAppHistoryItems().get(0).start()
					.compareTo(monthlyPs.get().getEmploymentAppHistoryItems().get(0).start()) != 0) {
				this.updateOrInsertDiffStartDate(companyId, employeeId, historyId, startDate, endDate, commonPs,
						monthlyPs, departmentApproverId, dailyApproverId);
			}
		} else {
			// ２．一個履歴の場合
			this.updateOrInsertHistory(companyId, employeeId, historyId, startDate, endDate, commonPs, monthlyPs,
					departmentApproverId, dailyApproverId);
		}
	}

	/**
	 * １．バラバラ履歴の場合
	 *     1.1　最新履歴の承認者を更新する
	 *     1.2　前の履歴を変更して新しい履歴を追加する
	 */
	private void updateOrInsertDiffStartDate(String companyId, String employeeId, String historyId, GeneralDate startDate,
			String endDate, Optional<PersonApprovalRoot> commonPs, Optional<PersonApprovalRoot> monthlyPs,
			String departmentApproverId, String dailyApproverId){

		PersonApprovalRoot newestPsAppRoot = null;
		PersonApprovalRoot olderPsAppRoot  = null;
		GeneralDate endDatePrevious        = startDate.addDays(-1);
		List<PersonApprovalRoot> insertPsAppRoot = new ArrayList<>();

		if (commonPs.get().getEmploymentAppHistoryItems().get(0).start()
				.after(monthlyPs.get().getEmploymentAppHistoryItems().get(0).start())) {
			newestPsAppRoot = commonPs.get();
			olderPsAppRoot  = monthlyPs.get();
		} else {
			newestPsAppRoot = monthlyPs.get();
			olderPsAppRoot  = commonPs.get();
		}

		//1.1　最新履歴の承認者を更新する
		String employeeIdApprover = isCommonPsApproval(newestPsAppRoot) ? dailyApproverId : departmentApproverId;
		this.updateApproverFirstPhase(companyId, employeeIdApprover, newestPsAppRoot);

		//1.2　前の履歴を変更して新しい履歴を追加する
		PersonApprovalRoot updateOlderPsAppRoot = PersonApprovalRoot.updateEdate(olderPsAppRoot,
				endDatePrevious.toString().replace("/", "-"));
		repoPerson.updatePsApprovalRoot(updateOlderPsAppRoot);
		
		if (isCommonPsApproval(olderPsAppRoot)) {
			// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null,
					startDate.toString().replace("/", "-"), endDate, UUID.randomUUID().toString(), null, null,
					0);
			insertPsAppRoot.add(common);
		} else {
			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null,
					startDate.toString().replace("/", "-"), endDate, UUID.randomUUID().toString(), null, 1, 2);
			insertPsAppRoot.add(monthly);
		}
		this.addHistory(companyId, departmentApproverId, dailyApproverId, insertPsAppRoot);
	}

	/**
	 * ２．一個履歴の場合 
	 *     1.1　履歴の承認者を変更する 
	 *     1.2　新しい履歴を追加する
	 * @param companyId
	 * @param employeeId
	 * @param historyId
	 * @param startDate
	 * @param endDate
	 * @param commonPs
	 * @param monthlyPs
	 * @param dailyApproverId
	 * @param departmentApproverId
	 */
	private void updateOrInsertHistory(String companyId, String employeeId, String historyId, GeneralDate startDate,
			String endDate, Optional<PersonApprovalRoot> commonPs, Optional<PersonApprovalRoot> monthlyPs,
			String departmentApproverId, String dailyApproverId) {
		List<PersonApprovalRoot> personApproval       = new ArrayList<>();
		List<PersonApprovalRoot> insertPersonApproval = new ArrayList<>();
		if (commonPs.isPresent()) {
			personApproval.add(commonPs.get());
		} else {
			// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, UUID.randomUUID().toString(), null, null, 0);
			insertPersonApproval.add(common);
		}

		if (monthlyPs.isPresent()) {
			personApproval.add(monthlyPs.get());
		} else {
			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, UUID.randomUUID().toString(), null, 1, 2);
			insertPersonApproval.add(monthly);
		}

		if (!personApproval.isEmpty()) {
			for (PersonApprovalRoot psAppRoot : personApproval) {
				String employeeIdApprover = isCommonPsApproval(psAppRoot) ? dailyApproverId : departmentApproverId;
				this.updateApproverFirstPhase(companyId, employeeIdApprover, psAppRoot);
			}
		}

		if (!insertPersonApproval.isEmpty()) {
			this.addHistory(companyId, departmentApproverId, dailyApproverId, insertPersonApproval);
		}
	}

	/**
	 * Update approver first phase
	 * @param companyId
	 * @param employeeIdApprover
	 * @param psAppRoot
	 */
	private void updateApproverFirstPhase(String companyId, String employeeIdApprover, PersonApprovalRoot psAppRoot) {
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

	/**
	 * Check person approval
	 * 
	 * @param psAppRoot
	 * @return
	 */
	private boolean isCommonPsApproval(PersonApprovalRoot psAppRoot) {
		boolean isCommmon = false;
		if (!Objects.isNull(psAppRoot)) {
			if (psAppRoot.getEmploymentRootAtr().value == 0 && Objects.isNull(psAppRoot.getApplicationType())) {
				isCommmon = true;
			} else if (psAppRoot.getEmploymentRootAtr().value == 2 && psAppRoot.getConfirmationRootType().value == 1) {
				isCommmon = false;
			}
		}
		return isCommmon;
	}

	/**
	 * deleteHistory
	 * 
	 * @param command
	 */
	private void deleteHistory(UpdateApprovalRootByManagerCommand command) {
		GeneralDate endDateDelete   = command.getEndDate();
		GeneralDate startDate       = command.getStartDate();
		GeneralDate endDatePrevious = startDate.addDays(-1);
		String companyId  = AppContexts.user().companyId();
		String employeeId = command.getEmployeeId();
		List<PersonApprovalRoot> getPastHistory = this.repoPerson.getPastHistory(companyId, command.getEmployeeId());
		Map<GeneralDate, List<PersonApprovalRoot>> groupedPsApproval = getPastHistory.stream()
				.collect(Collectors.groupingBy(item -> item.getEmploymentAppHistoryItems().get(0).end()));

		if (groupedPsApproval.containsKey(endDateDelete)) {
			List<PersonApprovalRoot> deletePersonApproval = groupedPsApproval.get(endDateDelete);
			for (PersonApprovalRoot deleteItem : deletePersonApproval) {
				String approvalId = deleteItem.getApprovalId();
				String historyId  = deleteItem.getEmploymentAppHistoryItems().get(0).getHistoryId();
				String branchId   = deleteItem.getBranchId();
				Optional<ApprovalPhase> approvalPhase = this.repoAppPhase.getApprovalFirstPhase(companyId, branchId);
				if (approvalPhase.isPresent()) {
					String phaseId = approvalPhase.get().getApprovalPhaseId();
					// 「個人別就業承認ルート」に紐付く「分岐」「承認ルート」を削除する
					this.repoApprover.deleteAllApproverByAppPhId(companyId, phaseId);
					this.repoAppPhase.deleteApprovalFirstPhase(companyId, branchId);
					this.repoBranch.deleteBranch(companyId, branchId);
				}
				// 「個人別就業承認ルート」を削除する
				this.repoPerson.deletePsApprovalRoot(companyId, approvalId, employeeId, historyId);
			}
		}

		List<PersonApprovalRoot> getAllPastHistory = this.repoPerson.getAllPsApprovalRoot(companyId,
				command.getEmployeeId());
		List<PersonApprovalRoot> currentApprovalRoot = getAllPastHistory.stream()
				.filter(x -> x.getEmploymentAppHistoryItems().get(0).end().compareTo(endDateDelete) == 0)
				.collect(Collectors.toList());
		// 削除した履歴の直前の「個人別就業承認ルート」が存在するかチェックする
		if (currentApprovalRoot.isEmpty()) {
			List<PersonApprovalRoot> previousApprovalRoot = getAllPastHistory.stream()
					.filter(x -> x.getEmploymentAppHistoryItems().get(0).end().compareTo(endDatePrevious) == 0)
					.collect(Collectors.toList());
			if (!previousApprovalRoot.isEmpty()) {
				for (PersonApprovalRoot updateItem : previousApprovalRoot) {
					// 削除した履歴の直前のドメインモデル「就業承認ルート履歴」を更新する。
					PersonApprovalRoot psAppRoot = PersonApprovalRoot.updateEdate(updateItem,
							endDateDelete.toString().replace("/", "-"));
					repoPerson.updatePsApprovalRoot(psAppRoot);
				}
			}
		}
	}
}
