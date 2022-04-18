package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import lombok.extern.log4j.Log4j;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.AsyncTask;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.MonthAggrForEmpsAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproveResultImport;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectManagerFromRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.ApprovalMailSendCheck;

@Stateless
@Log4j
public class ApproveAppProcedure {
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApprovalMailSendCheck approvalMailSendCheck;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private AppReflectManagerFromRecord appReflectManager;
	
	@Resource
	private ManagedExecutorService executerService;
	
	@Inject
	private MonthAggrForEmpsAdaptor monthAggrForEmpsAdaptor;
	
	@Inject
    private TransactionService transaction;
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.アルゴリズム.申請承認する時の手続き.申請承認する時の手続き
	 * @param companyID 会社ID
	 * @param appLst 申請リスト：List<申請>
	 * @param appHdsubRecLst 振休振出同時申請管理リスト：List<振休振出同時申請管理>
	 * @param approverID 承認者ID
	 * @param opApprovalComment Optional<承認コメント>
	 * @param appTypeSettingLst 申請種類別設定リスト：List<申請種類別設定>
	 * @param errorCheckFlg 排他エラースルーフラグ：boolean
	 * @param isInsert 新規作成か：boolean
	 * @return
	 */
	public ApproveAppProcedureOutput approveAppProcedure(String companyID, List<Application> appLst, List<AppHdsubRec> appHdsubRecLst, String approverID,
			Optional<String> opApprovalComment, List<AppTypeSetting> appTypeSettingLst, boolean errorCheckFlg, boolean isInsert) {
		List<String> failList = new ArrayList<>();
		List<String> approveFailLst = new ArrayList<>();
		List<String> deleteLst = new ArrayList<>();
		List<String> failServerList = new ArrayList<>();
		List<String> successList = new ArrayList<>();
		List<Application> appCompleteApproveLst = new ArrayList<>();
		// 振休振出同時申請の振休申請リストを求める
		List<String> absenceLeaveAppIDLst = appHdsubRecLst.stream()
				.filter(x -> x.getSyncing()==SyncState.SYNCHRONIZING).map(x -> x.getAbsenceLeaveAppID()).collect(Collectors.toList());
		// INPUT「申請リスト」をループする
		for(Application app : appLst) {
			// 申請を承認する
			ApproveAppOutput approveAppOutput = this.approveApp(companyID, app, approverID, opApprovalComment.orElse(null), errorCheckFlg, isInsert);
			if(approveAppOutput.isApprovalCompleteFlg()) {
				appCompleteApproveLst.add(app);
			}
			// 処理中の「申請．申請ID」が求めた「振休申請リスト」に含まれているかチェックする
			if(absenceLeaveAppIDLst.contains(app.getAppID())) {
				continue;
			}
			// 取得した「排他エラー」をチェックする
			if(approveAppOutput.isExclusiveError() || approveAppOutput.isAppDeleteError()) {
				if(approveAppOutput.isExclusiveError()) {
					// 「承認失敗申請IDリスト」に処理中の申請IDを追加する
					approveFailLst.add(app.getAppID());
				}
				if(approveAppOutput.isAppDeleteError()) {
					// 「申請データ削除済みリスト」に処理中の申請IDを追加する
					deleteLst.add(app.getAppID());
				}
			} else {
				// 承認後のメール送信処理
				AppTypeSetting appTypeSetting = appTypeSettingLst.stream().filter(x -> x.getAppType()==app.getAppType()).findAny().get();
				MailResult mailResult = this.sendEmailAfterApproval(
						appTypeSetting, 
						app, 
						approveAppOutput.getOpApprovalPhaseNumber().orElseGet(null), 
						approveAppOutput.isApprovalCompleteFlg());
				failList.addAll(mailResult.getFailList());
				failServerList.addAll(mailResult.getFailServerList());
				successList.addAll(mailResult.getSuccessList());
			}
		}
		// 申請を承認後の手続き
		AsyncTask task = AsyncTask.builder().keepsTrack(false).threadName(this.getClass().getName() + ".reflect-app: ")
				.build(() -> {
					this.procedureAfterApprove(appCompleteApproveLst, companyID);
				});
		this.executerService.submit(task);
		
		// 取得した内容を返す
		return new ApproveAppProcedureOutput(
				failList.stream().distinct().collect(Collectors.toList()), 
				approveFailLst.stream().distinct().collect(Collectors.toList()), 
				deleteLst.stream().distinct().collect(Collectors.toList()), 
				failServerList.stream().distinct().collect(Collectors.toList()), 
				successList.stream().distinct().collect(Collectors.toList()));
	}
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.アルゴリズム.申請を承認後の手続き.申請を承認後の手続き
	 * @param appCompleteApproveLst 申請リスト：List<申請>
	 * @param companyID
	 */
	public void procedureAfterApprove(List<Application> appCompleteApproveLst, String companyID) {
		// đối ứng tạm, đợi 3s chớ xử lý update trạng thái đơn xin hoàn thành, **** sau
		// sẽ xóa
		try {
			log.info("\n wait 3s");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 承認完了フラグがtrueになっている申請をグループする
		Map<String, List<Application>> empAppMap = appCompleteApproveLst.stream().collect(Collectors.groupingBy(x -> x.getEmployeeID()));
		for(Entry<String, List<Application>> empApp : empAppMap.entrySet()) {
			List<Application> appByEmpLst = empApp.getValue();
			// 反映する「対象期間」をもとめる
			GeneralDate minStartDate = appByEmpLst.stream().map(x -> x.getOpAppStartDate().get().getApplicationDate()).sorted().findFirst().get();
			GeneralDate maxEndDate = appByEmpLst.stream().map(x -> x.getOpAppEndDate().get().getApplicationDate()).sorted(Comparator.reverseOrder()).findFirst().get();
			// 社員の申請を反映
			appReflectManager.reflectAppOfAppDate(IdentifierUtil.randomUniqueId(), empApp.getKey(), ExecutionTypeExImport.RERUN,
								new DatePeriod(minStartDate, maxEndDate));
		}
		// 「対象社員リスト」から「月次集計社員リスト」を作成する
		List<String> applicantLst = empAppMap.keySet().stream().collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(applicantLst)) {
			CacheCarrier carrier = new CacheCarrier();
			// 社員の月別実績を集計する
			List<AtomTask> listAtomTask = monthAggrForEmpsAdaptor.aggregate(carrier, companyID, applicantLst, false);
			listAtomTask.forEach(x -> transaction.execute(x));
		}
	}
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.アルゴリズム.申請を承認する.申請を承認する
	 * @param companyID 会社ID
	 * @param app 申請
	 * @param approverID 承認者ID
	 * @param approvalComment 承認コメント
	 * @param errorCheckFlg 排他エラースルーフラグ
	 * @param isInsert 新規作成か
	 * @return
	 */
	public ApproveAppOutput approveApp(String companyID, Application app, String approverID, String approvalComment, boolean errorCheckFlg, boolean isInsert) {
		if(!isInsert) {
			// 「1.排他チェック」を実施する
			String exclusiveCheck = detailBeforeUpdate.exclusiveCheckLogic(companyID, app.getAppID(), app.getVersion());
			if(Strings.isNotBlank(exclusiveCheck)) {
				if(errorCheckFlg) {
					if(exclusiveCheck.equals("Msg_197")) {
						// OUTPUTを返す
						return new ApproveAppOutput(false, true, false, Optional.empty(), Optional.empty());
					}
					if(exclusiveCheck.equals("Msg_198")) {
						// OUTPUTを返す
						return new ApproveAppOutput(false, false, true, Optional.empty(), Optional.empty());
					}
				}
				// エラーメッセージを表示して処理終了する
				throw new BusinessException(exclusiveCheck);
			}
		}
		// 2.承認する(ApproveService)
		ApproveResultImport approveResultImport = approvalRootStateAdapter.doApprove(app.getAppID(), approverID, approvalComment);
		// 2.承認全体が完了したか
		Boolean allApprovalFlg = approvalRootStateAdapter.isApproveAllComplete(approveResultImport.getApprovalRootState());
		// 申請を更新する
		if(allApprovalFlg.equals(Boolean.TRUE)){
			for(ReflectionStatusOfDay reflectionStatusOfDay : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				reflectionStatusOfDay.setActualReflectStatus(ReflectedState.WAITREFLECTION);
				reflectionStatusOfDay.setActualReflectStatus(ReflectedState.WAITREFLECTION);
			}
			applicationRepository.update(app);
		} else {
			for(ReflectionStatusOfDay reflectionStatusOfDay : app.getReflectionStatus().getListReflectionStatusOfDay()) {
				reflectionStatusOfDay.setActualReflectStatus(ReflectedState.NOTREFLECTED);
				reflectionStatusOfDay.setActualReflectStatus(ReflectedState.NOTREFLECTED);
			}
			applicationRepository.update(app);
		}
		// 取得した内容を返す
		return new ApproveAppOutput(
				allApprovalFlg, 
				false, 
				false,
				Optional.of(approveResultImport.getApprovalPhaseNumber()), 
				Optional.of(approveResultImport.getApprovalRootState()));
	}
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.アルゴリズム.承認後のメール送信処理.承認後のメール送信処理
	 * @param appTypeSetting 申請種類別設定
	 * @param app 申請
	 * @param approvalPhaseNumber 承認フェーズ枠番
	 * @param approvalCompleteFlg 承認完了フラグ
	 * @return
	 */
	public MailResult sendEmailAfterApproval(AppTypeSetting appTypeSetting, Application app, Integer approvalPhaseNumber, boolean approvalCompleteFlg) {
		List<String> successList = new ArrayList<>();
		List<String> failList = new ArrayList<>();
		List<String> failServerList = new ArrayList<>();
		// 承認処理後にメールを承認者に送信判定
		List<String> approverLst = approvalMailSendCheck.sendMailApprover(appTypeSetting, app, approvalPhaseNumber);
		if(!CollectionUtil.isEmpty(approverLst)) {
			// 承認者へ送る（新規登録、更新登録、承認）
			MailResult mailResultApprover = otherCommonAlgorithm.sendMailApproverApprove(approverLst, app);
			successList.addAll(mailResultApprover.getSuccessList());
			failList.addAll(mailResultApprover.getFailList());
			failServerList.addAll(mailResultApprover.getFailServerList());
		}
		// 承認処理後にメールを自動送信するか判定
		String applicant = approvalMailSendCheck.sendMailApplicant(appTypeSetting, app, approvalCompleteFlg);
		if(Strings.isNotBlank(applicant)) {
			// 申請者へ送る（承認）
			MailResult mailResultApplicant = otherCommonAlgorithm.sendMailApplicantApprove(app);
			successList.addAll(mailResultApplicant.getSuccessList());
			failList.addAll(mailResultApplicant.getFailList());
			failServerList.addAll(mailResultApplicant.getFailServerList());
		}
		// 取得した内容を返す
		return new MailResult(
				successList.stream().distinct().collect(Collectors.toList()), 
				failList.stream().distinct().collect(Collectors.toList()), 
				failServerList.stream().distinct().collect(Collectors.toList()));
	}
	
}
