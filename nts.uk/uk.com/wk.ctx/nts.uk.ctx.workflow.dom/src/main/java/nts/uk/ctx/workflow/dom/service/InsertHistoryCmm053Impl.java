package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.AppRootInstanceContent;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

/**
 * @author sang.nv
 *
 */
@Stateless
public class InsertHistoryCmm053Impl implements InsertHistoryCmm053Service {

	@Inject
	private PersonApprovalRootRepository repoPerson;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	@Inject
	private UpdateHistoryCmm053Service updateHistory;
	@Inject
	private CreateDailyApprover createDailyApprover;

	/**
	 * 02.履歴追加を登録する
	 * Add history
	 * @param command
	 * @param historyId
	 * @param departmentApproverId
	 * @param dailyApproverId
	 */
	@Override
	public void insertHistoryByManagerSetting(String companyId, String historyId, String employeeId, GeneralDate startDate, String departmentApproverId,
			String dailyApproverId, boolean dailyDisplay) {
		String endDate = "9999-12-31";
		List<PersonApprovalRoot> psOlds = this.repoPerson.getPsAppRootLastest(companyId, employeeId,
				GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), 0);

		// 追加する履歴を最新の履歴の開始年月日と比較する
		if (!psOlds.isEmpty() && startDate.beforeOrEquals(psOlds.get(0).getApprRoot().getHistoryItems().get(0).start())) {
			//追加する履歴の開始年月日 >= 最新の履歴の開始年月日 が falseの場合
			throw new BusinessException("Msg_153");
		}

		List<PersonApprovalRoot> listPsPre = this.repoPerson.getPsApprovalRootBySdate(companyId, employeeId, startDate, 0);
		List<ApprovalPhase> listApprovalPhase = new ArrayList<>();
		if (!listPsPre.isEmpty()) {
			for (PersonApprovalRoot psApp : listPsPre) {
				listApprovalPhase.addAll(this.repoAppPhase.getAllApprovalPhasebyCode(psApp.getApprovalId()));
			}
		}
		// ドメインモデル「個人別就業承認ルート」．「承認フェーズ」．「順序」をチェックする
		if (!listApprovalPhase.isEmpty()) {
			List<ApprovalPhase> existApprovalPhase1 = new ArrayList<>();
			for (ApprovalPhase appPhase: listApprovalPhase){
				if(appPhase.getPhaseOrder() == 1){
					existApprovalPhase1.add(appPhase);
				}
			}
			// ドメインモデル「個人別就業承認ルート」．「承認フェーズ」．「順序」を追加する
			Optional<PersonApprovalRoot> commonPs  = this.repoPerson.getNewestCommonPsAppRoot(companyId, employeeId, 0);
			Optional<PersonApprovalRoot> monthlyPs = this.repoPerson.getNewestMonthlyPsAppRoot(companyId, employeeId);
			if (existApprovalPhase1.isEmpty()) {
				if (commonPs.isPresent()) {
					this.addApproverFirstPhase(companyId, dailyApproverId, commonPs.get());
				}
				if (monthlyPs.isPresent()) {
					this.addApproverFirstPhase(companyId, departmentApproverId, monthlyPs.get());
				}
			} else {
				this.updateOrInsertHistory(companyId, employeeId, historyId, startDate, endDate, commonPs, monthlyPs,
						departmentApproverId, dailyApproverId, dailyDisplay);
			}
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
			List<PersonApprovalRoot> insertPersonApproval = new ArrayList<>();
			if(dailyDisplay){//insert 2 record
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　1
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-10　の社員ID
				PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				this.addPersonApprovalRoot(companyId, dailyApproverId, departmentApproverId, psAppRoot);
			}else{//insert 1 record
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　2
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-7　の社員ID
				PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				insertPersonApproval.add(common);
			}

			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, 
					1, 2, SystemAtr.WORK.value, null, null);

			insertPersonApproval.add(monthly);

			// ドメインモデル「就業承認ルート」と紐付きドメインモデル「分岐」「承認ルート」をINSERTする(INSERT
			// domain「就業承認ルート」và domain 「分岐」「承認ルート」 liên kết)
			this.addHistoryByListPersonApprovalRoot(companyId, departmentApproverId, dailyApproverId, insertPersonApproval);
			//履歴の開始日とシステム日付をチェックする
			GeneralDate systemDate = GeneralDate.today();
			if(startDate.beforeOrEquals(systemDate)){
				//指定社員の中間データを作成する（日別）
				AppRootInstanceContent result =  createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_DAY, startDate, startDate);
				if(!result.getErrorFlag().equals(ErrorFlag.NO_ERROR)){
					throw new BusinessException(result.getErrorMsgID());
				}
				//指定社員の中間データを作成する（月別）
				result = createDailyApprover.createDailyApprover(employeeId, RecordRootType.CONFIRM_WORK_BY_MONTH, startDate, startDate);
				if(!result.getErrorFlag().equals(ErrorFlag.NO_ERROR)){
					throw new BusinessException(result.getErrorMsgID());
				}
			}
		}
	}

	/**
	 * １．バラバラ履歴の場合
	 *     1.1　最新履歴の承認者を更新する
	 *     1.2　前の履歴を変更して新しい履歴を追加する
	 * @param companyId
	 * @param employeeId
	 * @param historyId
	 * @param startDate
	 * @param endDate
	 * @param commonPs
	 * @param monthlyPs
	 * @param departmentApproverId
	 * @param dailyApproverId
	 */
	@Override
	public void updateOrInsertDiffStartDate(String companyId, String employeeId, String historyId, GeneralDate startDate,
			String endDate, Optional<PersonApprovalRoot> commonPs, Optional<PersonApprovalRoot> monthlyPs,
			String departmentApproverId, String dailyApproverId, boolean dailyDisplay){

		PersonApprovalRoot olderPsAppRoot  = null;
		GeneralDate endDatePrevious        = startDate.addDays(-1);
		List<PersonApprovalRoot> insertPsAppRoot = new ArrayList<>();
		//xac dinh ls moi, cu
		if (commonPs.get().getApprRoot().getHistoryItems().get(0).start()
				.after(monthlyPs.get().getApprRoot().getHistoryItems().get(0).start())) {
			olderPsAppRoot  = monthlyPs.get();
		} else {
			olderPsAppRoot  = commonPs.get();
		}

		//1.2　前の履歴を変更して新しい履歴を追加する
		PersonApprovalRoot updateOlderPsAppRoot = PersonApprovalRoot.updateEdate(olderPsAppRoot, endDatePrevious.toString().replace("/", "-"));
		repoPerson.updatePsApprovalRoot(updateOlderPsAppRoot);
		//1.1　最新履歴の承認者を更新する
		if (olderPsAppRoot.isCommon()) {//TH ls cu la common
			//them ls common moi, update ls monthly
			if(dailyDisplay){//insert 2 record
				// String branchId = UUID.randomUUID().toString();
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　1
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-10　の社員ID
				PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				this.addPersonApprovalRoot(companyId, dailyApproverId, departmentApproverId, psAppRoot);
			}else{//insert 1 record
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　2
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-7　の社員ID
				PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				insertPsAppRoot.add(common);
			}
			//update ls monthly
			updateHistory.updateRootCMM053(companyId, departmentApproverId, dailyApproverId, null, monthlyPs.get(), dailyDisplay);
			
		} else {//TH ls cu la monthly
			//them ls monthly moi, update ls com
			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null,
					startDate.toString().replace("/", "-"), endDate, 
					1, 2, SystemAtr.WORK.value, null, null);
			insertPsAppRoot.add(monthly);
			//update ls common
			updateHistory.updateRootCMM053(companyId, departmentApproverId, dailyApproverId, commonPs.get(), null, dailyDisplay);
		}
		this.addHistoryByListPersonApprovalRoot(companyId, departmentApproverId, dailyApproverId, insertPsAppRoot);
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
	@Override
	public void updateOrInsertHistory(String companyId, String employeeId, String historyId, GeneralDate startDate,
			String endDate, Optional<PersonApprovalRoot> commonPs, Optional<PersonApprovalRoot> monthlyPs,
			String departmentApproverId, String dailyApproverId, boolean dailyDisplay) {
		List<PersonApprovalRoot> insertPersonApproval = new ArrayList<>();
		//common
		if (commonPs.isPresent()) {//common co ls: update ls
			updateHistory.updateRootCMM053(companyId, departmentApproverId, dailyApproverId, commonPs.get(), null, dailyDisplay);
		} else {//common khong co ls: them moi ls
			if(dailyDisplay){//insert 2 record
				// String branchId = UUID.randomUUID().toString();
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　1
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-10　の社員ID
				PersonApprovalRoot psAppRoot = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				this.addPersonApprovalRoot(companyId, dailyApproverId, departmentApproverId, psAppRoot);
			}else{//insert 1 record
				// 条件： １．就業ルート区分：申請 AND 申請種類：共通ルート ２．承認者・順序　＝　2
				//承認者・確定者 = false
				//承認者・社員ID　＝　A2-7　の社員ID
				PersonApprovalRoot common = PersonApprovalRoot.createSimpleFromJavaType(companyId,
						UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
						endDate, 
						null, 0, SystemAtr.WORK.value, null, null);
				insertPersonApproval.add(common);
			}
		}
		//monthly
		if (monthlyPs.isPresent()) {//monthly co ls: update ls monthly
			updateHistory.updateRootCMM053(companyId, departmentApproverId, dailyApproverId, null, monthlyPs.get(), dailyDisplay);
		} else {
			// 条件： 1．就業ルート区分：確認 AND 確認ルート種類：月次確認 2．承認フェーズ.順序 ＝ 1
			PersonApprovalRoot monthly = PersonApprovalRoot.createSimpleFromJavaType(companyId,
					UUID.randomUUID().toString(), employeeId, historyId, null, startDate.toString().replace("/", "-"),
					endDate, 
					1, 2, SystemAtr.WORK.value, null, null);
			insertPersonApproval.add(monthly);
		}

		if (!insertPersonApproval.isEmpty()) {
			this.addHistoryByListPersonApprovalRoot(companyId, departmentApproverId, dailyApproverId, insertPersonApproval);
		}
	}

	
	/**
	 * AddistoryByListPersonApprovalRoot
	 * @param companyId
	 * @param departmentApproverId
	 * @param dailyApproverId
	 * @param insertPersonApproval
	 */
	private void addHistoryByListPersonApprovalRoot(String companyId, String departmentApproverId, String dailyApproverId,
			List<PersonApprovalRoot> insertPersonApproval) {
		int phaseOrder = 1;
		int approverOrder = 1;
		int browsingPhase = 0;
		for (PersonApprovalRoot psAppRoot : insertPersonApproval) {
			String approvalId        = psAppRoot.getApprovalId();
			// 承認者
			List<Approver> listApprover = new ArrayList<>();
			String employeeIdApprover   = psAppRoot.isCommon() ? dailyApproverId : departmentApproverId;
			listApprover.add(Approver.createSimpleFromJavaType(approverOrder,
					null, employeeIdApprover, ConfirmPerson.NOT_CONFIRM.value, null));
			this.repoApprover.addAllApprover(approvalId, phaseOrder, listApprover);

			// 承認フェーズ
			this.repoAppPhase.addApprovalPhase(ApprovalPhase.createSimpleFromJavaType(approvalId, 
					phaseOrder, 
					ApprovalForm.SINGLE_APPROVED.value, browsingPhase, ApprovalAtr.PERSON.value, listApprover));

		}
		// ドメインモデル「個人別就業承認ルート」をINSERTする
		this.repoPerson.addAllPsApprovalRoot(insertPersonApproval);
	}
	private void addPersonApprovalRoot(String companyId, String appr1, String appr2, PersonApprovalRoot psAppRoot) {
		int confirmPerson = ConfirmPerson.NOT_CONFIRM.value;
		int browsingPhase = 0;
		int phaseOrder = 1;
		int approverOrder1 = 1;
		int approverOrder2 = 2;

		String approvalId      = psAppRoot.getApprovalId();
		List<Approver> listApprover = new ArrayList<>();
		//承認者1 A2_10
		listApprover.add(Approver.createSimpleFromJavaType(approverOrder1, null, appr1, confirmPerson, null));
		//承認者2 A2_7
		listApprover.add(Approver.createSimpleFromJavaType(approverOrder2, null, appr2, confirmPerson, null));
		
		this.repoApprover.addAllApprover(approvalId, phaseOrder, listApprover);

		// 承認フェーズ
		this.repoAppPhase.addApprovalPhase(ApprovalPhase.createSimpleFromJavaType(approvalId, 
				phaseOrder, 
				ApprovalForm.SINGLE_APPROVED.value, browsingPhase, ApprovalAtr.PERSON.value, listApprover));

		// ドメインモデル「個人別就業承認ルート」をINSERTする
		this.repoPerson.addAllPsApprovalRoot(Arrays.asList(psAppRoot));
	}
	/**
	 * Add Approver First Phase
	 * @param companyId
	 * @param approvalPhaseId
	 * @param employeeIdApprover
	 * @param psAppRoot
	 */
	private void addApproverFirstPhase(String companyId, String employeeIdApprover, PersonApprovalRoot psAppRoot) {
		int phaseOrder = 1;
		int approverOrder = 0;
		int confirmPerson = 0;
		String approvalId = psAppRoot.getApprovalId();
		if(!StringUtil.isNullOrEmpty(employeeIdApprover, true)){
			// 承認者
			Approver approver = Approver.createSimpleFromJavaType(
					approverOrder, null, employeeIdApprover, confirmPerson, null);
			List<Approver> lstApprover = Arrays.asList(approver);
			this.repoApprover.addAllApprover(approvalId, phaseOrder, lstApprover);

			// 承認フェーズ
			this.repoAppPhase.addApprovalPhase(ApprovalPhase.createSimpleFromJavaType(approvalId, 
					phaseOrder, 
					ApprovalForm.SINGLE_APPROVED.value, 0, ApprovalAtr.PERSON.value, lstApprover));
		}
	}
}
