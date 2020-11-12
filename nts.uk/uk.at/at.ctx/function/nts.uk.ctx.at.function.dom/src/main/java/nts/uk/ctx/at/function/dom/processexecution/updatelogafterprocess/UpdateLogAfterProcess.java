package nts.uk.ctx.at.function.dom.processexecution.updatelogafterprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.executionlog.ScheduleErrorLogAdapter;
import nts.uk.ctx.at.function.dom.adapter.executionlog.ScheduleErrorLogImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.AlarmCategoryFn;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.ExecutionLogAdapterFn;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.ExecutionLogErrorDetailFn;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily.AppDataInfoDailyImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily.CreateperApprovalDailyAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.AppDataInfoMonthlyImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.CreateperApprovalMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoImport;
import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforAdapter;
import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforImported;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.shr.com.i18n.TextResource;

// TODO: Auto-generated Javadoc
/**
 * 各処理の後のログ更新処理.
 *
 * @author tutk
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class UpdateLogAfterProcess {
	
	/** The employee manage adapter. */
	@Inject
	private EmployeeManageAdapter employeeManageAdapter;

	/** The err message info adapter. */
	@Inject
	private ErrMessageInfoAdapter errMessageInfoAdapter;

	/** The schedule error log adapter. */
	@Inject
	private ScheduleErrorLogAdapter scheduleErrorLogAdapter;

	/** The createper approval daily adapter. */
	@Inject
	private CreateperApprovalDailyAdapter createperApprovalDailyAdapter;

	/** The createper approval monthly adapter. */
	@Inject
	private CreateperApprovalMonthlyAdapter createperApprovalMonthlyAdapter;

	/** The proc exec log repo. */
	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;

	/** The execution log adapter fn. */
	@Inject
	private ExecutionLogAdapterFn executionLogAdapterFn;
	
	@Inject
	private AggrPeriodInforAdapter aggrPeriodInforAdapter;

	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ更新.アルゴリズム.各処理の後のログ更新処理.各処理の後のログ更新処理
	 *
	 * @param processExecutionTask the process execution task
	 * @param companyId the company id
	 * @param execItemCd the exec item cd
	 * @param procExec the proc exec
	 * @param procExecLog the proc exec log
	 * @param isException the is exception
	 * @param isStopExec the is stop exec
	 * @param errorMessage the error message
	 */
	public void updateLogAfterProcess(ProcessExecutionTask processExecutionTask, String companyId, String execItemCd,
	                                  UpdateProcessAutoExecution procExec, ProcessExecutionLog procExecLog, boolean isException, boolean isStopExec,
	                                  String errorMessage) {
		// 就業担当者の社員ID（List）を取得する - RQ526
//		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());

		String execId = procExecLog.getExecId();
		boolean isHasErrorBusiness = false;
		List<ScheduleErrorLogImport> listScheduleErrorLog = new ArrayList<>();
		List<AppDataInfoDailyImport> listAppDataInfoDaily = new ArrayList<>();
		List<AppDataInfoMonthlyImport> listAppDataInfoMonthly = new ArrayList<>();
		List<ErrMessageInfoImport> listErr = new ArrayList<>();
		switch (processExecutionTask) {
		// INPUT「実行項目」をチェックする
		case SCH_CREATION:
			// ドメインモデル「スケジュール作成エラーログ」を取得する
			listScheduleErrorLog = scheduleErrorLogAdapter.findByExecutionId(execId);
			if (!listScheduleErrorLog.isEmpty()) {
				isHasErrorBusiness = true;
			}
			break;
		case APP_ROUTE_U_DAI:
			// ドメインモデル「承認中間データエラーメッセージ情報（日別実績）」を取得する
			listAppDataInfoDaily = createperApprovalDailyAdapter.getAppDataInfoDailyByExeID(execId);
			if (!listAppDataInfoDaily.isEmpty()) {
				isHasErrorBusiness = true;
			}
			break;
		case APP_ROUTE_U_MON:
			// ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を取得する
			listAppDataInfoMonthly = createperApprovalMonthlyAdapter.getAppDataInfoMonthlyByExeID(execId);
			if (!listAppDataInfoMonthly.isEmpty()) {
				isHasErrorBusiness = true;
			}
			break;
		case AL_EXTRACTION:
			// case アラーム抽出
			// INPUT「エラーメッセージ」をチェックする
				isHasErrorBusiness = false;
			break;
		case AGGREGATION_OF_ARBITRARY_PERIOD:
			// case 任意項目の集計
			// step ドメインモデル「任意期間集計エラーメッセージ情報」を取得する
			List<AggrPeriodInforImported> listAggrPeriodInforImported = this.aggrPeriodInforAdapter.findAggrPeriodInfor(execId);
			if (!listAggrPeriodInforImported.isEmpty()) {
				isHasErrorBusiness = true;
			}
			break;
		case DELETE_DATA:
		case SAVE_DATA:
		case EXTERNAL_ACCEPTANCE:
		case EXTERNAL_OUTPUT:
			// INPUT「エラーメッセージ」を取得する (input get message error)
			isHasErrorBusiness = true;
			break;
		default: // DAILY_CREATION DAILY_CALCULATION RFL_APR_RESULT MONTHLY_AGGR
			// ドメインモデル「エラーメッセージ情報」を取得する
			listErr = errMessageInfoAdapter.getAllErrMessageInfoByID(execId, getContent(processExecutionTask));
			if (!listErr.isEmpty()) {
				isHasErrorBusiness = true;
			}
			break;
		}

		// step 取得できた場合
		if (isHasErrorBusiness) {
			// step パラメータ「Exceptionの有無」を判断
			if (isException) { // exceptionがある場合
				// step ドメインモデル「更新処理自動実行ログ」を更新する (update domain 「更新処理自動実行ログ」)
				procExecLog.getTaskLogList().forEach(task -> {
					if (processExecutionTask.value == task.getProcExecTask().value) {
						task.setLastEndExecDateTime(GeneralDateTime.now());
						task.setErrorSystem(true);
						task.setErrorBusiness(true);
						task.setStatus(Optional.ofNullable(isStopExec ? EndStatus.FORCE_END : EndStatus.SUCCESS));
					}
				});
				this.procExecLogRepo.update(procExecLog);

			} else { // exceptionがない場合
				// step ドメインモデル「更新処理自動実行ログ」を更新する (update domain 「更新処理自動実行ログ」)
				procExecLog.getTaskLogList().forEach(task -> {
					if (processExecutionTask.value == task.getProcExecTask().value) {
						task.setLastEndExecDateTime(GeneralDateTime.now());
						task.setErrorSystem(false);
						task.setErrorBusiness(true);
						task.setStatus(Optional.ofNullable(isStopExec ? EndStatus.FORCE_END : EndStatus.SUCCESS));
					}
				});
				this.procExecLogRepo.update(procExecLog);

			}
		} else {// 取得できない場合
			// パラメータ「Exceptionの有無」を判断
			if (isException) {
				// ドメインモデル「更新処理自動実行ログ」を更新する
				procExecLog.getTaskLogList().forEach(task -> {
					if (processExecutionTask.value == task.getProcExecTask().value) {
						task.setLastEndExecDateTime(GeneralDateTime.now());
						task.setErrorSystem(true);
						task.setErrorBusiness(false);
						task.setStatus(Optional.ofNullable(isStopExec ? EndStatus.FORCE_END : EndStatus.SUCCESS));
					}
				});
				this.procExecLogRepo.update(procExecLog);

			} else {
				// ドメインモデル「更新処理自動実行ログ」を更新する
				procExecLog.getTaskLogList().forEach(task -> {
					if (processExecutionTask.value == task.getProcExecTask().value) {
						task.setLastEndExecDateTime(GeneralDateTime.now());
						task.setErrorSystem(false);
						task.setErrorBusiness(false);
						task.setStatus(Optional.ofNullable(isStopExec ? EndStatus.FORCE_END : EndStatus.SUCCESS));
					}
				});
				this.procExecLogRepo.update(procExecLog);

			}
		}
		// Step INPUT「中断フラグ」をチェック
		if (isStopExec) {
			// ◆実行項目 = 「スケジュール作成」
			if (processExecutionTask == ProcessExecutionTask.SCH_CREATION) {
				// Step 各処理の終了状態 ＝ [日別作成、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [日別計算、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認結果反映、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [月別集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「日別作成」
			else if (processExecutionTask == ProcessExecutionTask.DAILY_CREATION) {
				// Step 各処理の終了状態 ＝ [日別計算、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認結果反映、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [月別集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「日別計算」
			else if (processExecutionTask == ProcessExecutionTask.DAILY_CALCULATION) {
				// Step 各処理の終了状態 ＝ [承認結果反映、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [月別集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}
			//◆実行項目 = 「承認結果の反映」
			else if (processExecutionTask == ProcessExecutionTask.RFL_APR_RESULT) {
				// Step 各処理の終了状態 ＝ [月別集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}
			//◆実行項目 = 「月別実績の集計」
			else if (processExecutionTask == ProcessExecutionTask.MONTHLY_AGGR) {
				// Step 各処理の終了状態 ＝ [アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「アラーム抽出」
			else if (processExecutionTask == ProcessExecutionTask.AL_EXTRACTION) {
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「承認ルート更新（日次）」
			else if (processExecutionTask == ProcessExecutionTask.APP_ROUTE_U_DAI) {
				// Step 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「承認ルート更新（月次）」
			else if (processExecutionTask == ProcessExecutionTask.APP_ROUTE_U_MON) {
				// Step 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「外部出力」
			else if (processExecutionTask == ProcessExecutionTask.EXTERNAL_OUTPUT) {
				// Step 各処理の終了状態　＝　[アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部受入、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_ACCEPTANCE, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「外部受入」
			else if (processExecutionTask == ProcessExecutionTask.EXTERNAL_ACCEPTANCE) {
				// Step 各処理の終了状態　＝　[日別作成、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[日別計算、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[承認結果反映、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[月別集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[承認ルート更新（日次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[スケジュールの作成、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[任意項目の集計、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AGGREGATION_OF_ARBITRARY_PERIOD, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[外部出力、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.EXTERNAL_OUTPUT, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[アラーム抽出、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ保存、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SAVE_DATA, EndStatus.NOT_IMPLEMENT);
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}
			// ◆実行項目 = 「データ保存」
			else if (processExecutionTask == ProcessExecutionTask.SAVE_DATA) {
				// Step 各処理の終了状態　＝　[データ削除、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DELETE_DATA, EndStatus.NOT_IMPLEMENT);
			}

			// ドメインモデル「更新処理自動実行ログ」を更新する
			this.procExecLogRepo.update(procExecLog);
		}

	}

	/**
	 * Gets the content.
	 *
	 * @param processExecutionTask the process execution task
	 * @return the content
	 */
	private int getContent(ProcessExecutionTask processExecutionTask) {
		if (processExecutionTask == ProcessExecutionTask.DAILY_CREATION) {
			return 0;
		} else if (processExecutionTask == ProcessExecutionTask.DAILY_CALCULATION) {
			return 1;
		} else if (processExecutionTask == ProcessExecutionTask.RFL_APR_RESULT) {
			return 2;
		}
		return 3;
	}

	/**
	 * Gets the category.
	 *
	 * @param processExecutionTask the process execution task
	 * @return the category
	 */
	private AlarmCategoryFn getCategory(ProcessExecutionTask processExecutionTask) {
		return EnumAdaptor.valueOf(processExecutionTask.value + 1, AlarmCategoryFn.class);
	}

	/**
	 * Gets the execution log error detail.
	 *
	 * @param processExecutionTask the process execution task
	 * @param listScheduleErrorLog the list schedule error log
	 * @param listAppDataInfoDaily the list app data info daily
	 * @param listAppDataInfoMonthly the list app data info monthly
	 * @param listErr the list err
	 * @param isException the is exception
	 * @param isHasErrorBusiness the is has error business
	 * @param listManagerId the list manager id
	 * @param errorMessage the error message
	 * @return the execution log error detail
	 */
	private List<ExecutionLogErrorDetailFn> getExecutionLogErrorDetail(ProcessExecutionTask processExecutionTask,
			List<ScheduleErrorLogImport> listScheduleErrorLog, List<AppDataInfoDailyImport> listAppDataInfoDaily,
			List<AppDataInfoMonthlyImport> listAppDataInfoMonthly, List<ErrMessageInfoImport> listErr,
			boolean isException, boolean isHasErrorBusiness, List<String> listManagerId, String errorMessage) {
		List<ExecutionLogErrorDetailFn> listExecutionLogErrorDetail = new ArrayList<>();
		// 取得したエラーメッセージ情報（（※1）
		if (isHasErrorBusiness) {
			if (processExecutionTask == ProcessExecutionTask.SCH_CREATION) { // 実行項目 = 「スケジュール作成」
				for (ScheduleErrorLogImport scheduleErrorLogImport : listScheduleErrorLog) {
					listExecutionLogErrorDetail.add(new ExecutionLogErrorDetailFn(
							scheduleErrorLogImport.getErrorContent(), scheduleErrorLogImport.getEmployeeId()));
				}
			} else if (processExecutionTask == ProcessExecutionTask.AL_EXTRACTION) {
				for (String manager : listManagerId) {
					listExecutionLogErrorDetail.add(new ExecutionLogErrorDetailFn(errorMessage, manager));
				}
			} else if (processExecutionTask == ProcessExecutionTask.APP_ROUTE_U_DAI) { // 実行項目 = 「承認ルート更新（日次）」
				for (AppDataInfoDailyImport appDataInfoDailyImport : listAppDataInfoDaily) {
					listExecutionLogErrorDetail.add(new ExecutionLogErrorDetailFn(
							appDataInfoDailyImport.getErrorMessage(), appDataInfoDailyImport.getEmployeeId()));
				}
			} else if (processExecutionTask == ProcessExecutionTask.APP_ROUTE_U_MON) { // 実行項目 = 「承認ルート更新（月次）」
				for (AppDataInfoMonthlyImport appDataInfoMonthlyImport : listAppDataInfoMonthly) {
					listExecutionLogErrorDetail.add(new ExecutionLogErrorDetailFn(
							appDataInfoMonthlyImport.getErrorMessage(), appDataInfoMonthlyImport.getEmployeeId()));
				}
			} else { // 実行項目 = 「日別作成・日別計算 or 承認結果の反映 or 月別実績の集計」
				for (ErrMessageInfoImport errMessageInfoImport : listErr) {
					listExecutionLogErrorDetail.add(new ExecutionLogErrorDetailFn(
							errMessageInfoImport.getMessageError(), errMessageInfoImport.getEmployeeID()));
				}
			}

		}
		// パラメータ「就業担当社員ID」, メッセージ「Msg_1339」or「Msg_1552」 ）（List）
		if (isException) {
			for (String manager : listManagerId) {
				listExecutionLogErrorDetail
						.add(new ExecutionLogErrorDetailFn(TextResource.localize(errorMessage), manager));
			}
		}

		return listExecutionLogErrorDetail;
	}

	/**
	 * Update each task status.
	 *
	 * @param procExecLog the proc exec log
	 * @param execTask the exec task
	 * @param status the status
	 */
	private void updateEachTaskStatus(ProcessExecutionLog procExecLog, ProcessExecutionTask execTask,
			EndStatus status) {
		procExecLog.getTaskLogList().forEach(task -> {
			if (execTask.value == task.getProcExecTask().value) {
				task.setStatus(Optional.ofNullable(status));
			}
		});
	}
}
