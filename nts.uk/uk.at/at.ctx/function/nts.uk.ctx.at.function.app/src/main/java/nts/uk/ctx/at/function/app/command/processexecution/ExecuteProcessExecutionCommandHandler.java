package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
//import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.task.parallel.ManagedParallelWithContext.ControlOption;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.gul.error.ThrowableAnalyzer;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.app.command.processexecution.approuteupdatedaily.AppRouteUpdateDailyService;
import nts.uk.ctx.at.function.app.command.processexecution.approuteupdatedaily.OutputAppRouteDaily;
import nts.uk.ctx.at.function.app.command.processexecution.approuteupdatemonthly.AppRouteUpdateMonthlyService;
import nts.uk.ctx.at.function.app.command.processexecution.approuteupdatemonthly.OutputAppRouteMonthly;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.appreflectmanager.AppReflectManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.appreflectmanager.ProcessStateReflectImport;
import nts.uk.ctx.at.function.dom.adapter.dailymonthlyprocessing.DailyMonthlyprocessAdapterFn;
import nts.uk.ctx.at.function.dom.adapter.dailymonthlyprocessing.ExeStateOfCalAndSumImportFn;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.AlarmCategoryFn;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.ExecutionLogAdapterFn;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.ExecutionLogErrorDetailFn;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.ExecutionLogImportFn;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.WorkInfoOfDailyPerFnImport;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.createextractionprocess.CreateExtraProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing.ExecAlarmListProcessingService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing.OutputExecAlarmListPro;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionScopeClassification;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecType;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.listempautoexec.ListEmpAutoExec;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreateScheduleYear;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetMonth;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.function.dom.processexecution.updateprocessexecsetting.changepersionlist.ChangePersionList;
import nts.uk.ctx.at.function.dom.processexecution.updateprocessexecsetting.changepersionlist.ListLeaderOrNotEmp;
import nts.uk.ctx.at.function.dom.processexecution.updateprocessexecsetting.changepersionlistforsche.ChangePersionListForSche;
import nts.uk.ctx.at.function.dom.statement.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.record.dom.affiliationinformation.wkplaceinfochangeperiod.WkplaceInfoChangePeriod;
import nts.uk.ctx.at.record.dom.affiliationinformation.wktypeinfochangeperiod.WkTypeInfoChangePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultEmployeeDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DailyCalculationEmployeeService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.CalExeSettingInfor;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ObjectPeriod;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.SetInforReflAprResult;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.SettingInforForDailyCreation;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.CalAndAggClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.DailyRecreateClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutedMenu;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommandHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeErrorLogHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheduleErrorLogGeterCommand;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.RebuildTargetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.RebuildTargetDetailsAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ResetAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpHisAdaptor;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.task.schedule.UkJobScheduler;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
@Slf4j
public class ExecuteProcessExecutionCommandHandler extends AsyncCommandHandler<ExecuteProcessExecutionCommand> {

	@Inject
	private ProcessExecutionRepository procExecRepo;

	@Inject
	private ExecutionTaskSettingRepository execSettingRepo;

	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;

	@Inject
	private ProcessExecutionLogHistRepository procExecLogHistRepo;

	@Inject
	private ExecutionTaskLogRepository execTaskLogRepo;

	@Inject
	private LastExecDateTimeRepository lastExecDateTimeRepo;

	@Inject
	private EmpCalAndSumExeLogRepository empCalSumRepo;

	@Inject
	private ClosureRepository closureRepo;

	/** The Closure service. */
	@Inject
	private ClosureService closureService;

	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private ScheduleCreatorExecutionCommandHandler scheduleExecution;

	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	@Inject
	private WorkplaceWorkRecordAdapter workplaceAdapter;

	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;

	@Inject
	private ExecutionLogRepository executionLogRepository;

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;
	@Inject
	private MonthlyAggregationEmployeeService monthlyService;
	@Inject
	private ClosureEmploymentService closureEmploymentService;
	@Inject
	private ProcessExecutionLogManageRepository processExecLogManaRepo;
	@Inject
	private CreateDailyResultEmployeeDomainService createDailyService;
	@Inject
	private DailyCalculationEmployeeService dailyCalculationEmployeeService;
	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;
	@Inject
	private UkJobScheduler scheduler;
	@Inject
	private WorkplaceWorkRecordAdapter workplaceWorkRecordAdapter;

	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;

	// requestList477
	@Inject
	private ExecutionLogAdapterFn executionLogAdapterFn;

	// request list 526
	@Inject
	private EmployeeManageAdapter employeeManageAdapter;

	@Inject
	private AppReflectManagerAdapter appReflectManagerAdapter;

	@Inject
	private ManagedParallelWithContext managedParallelWithContext;

	@Inject
	private AppRouteUpdateDailyService appRouteUpdateDailyService;

	@Inject
	private AppRouteUpdateMonthlyService appRouteUpdateMonthlyService;

	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	@Inject
	private DailyMonthlyprocessAdapterFn dailyMonthlyprocessAdapterFn;

	@Inject
	private ChangePersionList changePersionList;

	@Inject
	private ChangePersionListForSche changePersionListForSche;
	
	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	
	@Inject
	private WkplaceInfoChangePeriod wkplaceInfoChangePeriod;
	
	@Inject
	private WkTypeInfoChangePeriod wkTypeInfoChangePeriod;
	
	@Inject
	private BusinessTypeOfEmpHisAdaptor businessTypeOfEmpHisAdaptor;
	
	@Inject
	private ListEmpAutoExec listEmpAutoExec;
	
	public static int MAX_DELAY_PARALLEL = 0;

	/**
	 * 更新処理を開始する
	 * 
	 * @param execType
	 *            実行タイプ
	 * @param execId
	 *            実行ID
	 * @param execItemCd
	 *            更新処理自動実行項目コード
	 * @param companyId
	 *            会社ID
	 */
	// 実行処理
	@Override
	public void handle(CommandHandlerContext<ExecuteProcessExecutionCommand> context) {
		System.out.println("Run batch service !");

		// val asyncContext = context.asAsync();
		context.asAsync().getDataSetter().setData("taskId", context.asAsync().getTaskId());
		ExecuteProcessExecutionCommand command = context.getCommand();
		String execItemCd = command.getExecItemCd();
		String companyId = command.getCompanyId();
		// String execId = command.getExecId();
		// vi ExecuteProcessExecCommandHandler dang loi nen dung tam random execId
		String execId = IdentifierUtil.randomUniqueId();
		int execType = command.getExecType();
		// ドメインモデル「更新処理自動実行」を取得する
		ProcessExecution procExec = null;
		Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId,
				execItemCd);
		if (procExecOpt.isPresent()) {
			procExec = procExecOpt.get();
		}
		// ドメインモデル「実行タスク設定」を取得する
		ExecutionTaskSetting execSetting = null;
		if (execType == 0) {
			Optional<ExecutionTaskSetting> execSettingOpt = this.execSettingRepo.getByCidAndExecCd(companyId,
					execItemCd);
			if (execSettingOpt.isPresent()) {
				execSetting = execSettingOpt.get();
			}
		}

		// ドメインモデル「更新処理自動実行管理」を取得する NO.4
		ProcessExecutionLogManage processExecutionLogManage = null;
		Optional<ProcessExecutionLogManage> logByCIdAndExecCdOpt = this.processExecLogManaRepo
				.getLogByCIdAndExecCd(companyId, execItemCd);
		if (logByCIdAndExecCdOpt.isPresent()) {
			processExecutionLogManage = logByCIdAndExecCdOpt.get();
		}

		/*
		 * // ドメインモデル「更新処理自動実行ログ」を取得する ProcessExecutionLog procExecLog = null;
		 * Optional<ProcessExecutionLog> procExecLogOpt =
		 * this.procExecLogRepo.getLogByCIdAndExecCd(companyId, execItemCd, execId); if
		 * (procExecLogOpt.isPresent()) { procExecLog = procExecLogOpt.get(); }
		 * 
		 */

		// ドメインモデル「更新処理前回実行日時」を取得する
		LastExecDateTime lastExecDateTime = null;
		Optional<LastExecDateTime> lastDateTimeOpt = Optional.empty();
		if (procExec != null) {
			lastDateTimeOpt = lastExecDateTimeRepo.get(procExec.getCompanyId(), procExec.getExecItemCd().v());
		}

		if (lastDateTimeOpt.isPresent()) {
			lastExecDateTime = lastDateTimeOpt.get();
		}
		if (execType == 0) {
			// ドメインモデルの取得結果をチェックする
			if (procExec == null || execSetting == null || processExecutionLogManage == null
					|| lastExecDateTime == null) {
				return;
			}
		} else {
			if (procExec == null || processExecutionLogManage == null || lastExecDateTime == null) {
				return;
			}
		}
		// アルゴリズム「就業計算と集計実行ログ作成判定処理」を実行する
		// ・ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.日別実績の作成・計算区分」
		// boolean dailyPerfCls =
		// procExec.getExecSetting().getDailyPerf().isDailyPerfCls();
		// // ・ドメインモデル「更新処理自動実行.実行設定.承認結果反映」
		// boolean reflectResultCls = procExec.getExecSetting().isReflectResultCls();
		// // ・ドメインモデル「更新処理自動実行.実行設定.承認結果反映」
		// boolean monthlyAggCls = procExec.getExecSetting().isMonthlyAggCls();
		EmpCalAndSumExeLog empCalAndSumExeLog = null;
		// if (dailyPerfCls || reflectResultCls || monthlyAggCls) {
		// ドメインモデル「就業計算と集計実行ログ」を追加する
		empCalAndSumExeLog = new EmpCalAndSumExeLog(execId, command.getCompanyId(),
				new YearMonth(GeneralDate.today().year() * 100 + 1), ExecutedMenu.SELECT_AND_RUN, GeneralDateTime.now(),
				null, AppContexts.user().employeeId(), 1, IdentifierUtil.randomUniqueId(),
				CalAndAggClassification.AUTOMATIC_EXECUTION);
		this.empCalSumRepo.add(empCalAndSumExeLog);
		// }

		// アルゴリズム「実行前登録処理」を実行する
		// 実行前登録処理
		ProcessExecutionLog procExecLog = this.preExecutionRegistrationProcessing(companyId, execItemCd, execId,
				processExecutionLogManage, execType);

		/*
		 * /* ドメインモデル「更新処理自動実行ログ」を更新する 現在の実行状態 ＝ 実行 全体の終了状態 ＝ NULL
		 * 
		 * procExecLog.setCurrentStatus(CurrentExecutionStatus.RUNNING);
		 * procExecLog.setOverallStatus(null); this.procExecLogRepo.update(procExecLog);
		 */

		/*
		 * 各処理を実行する 【パラメータ】 実行ID ＝ 取得した実行ID
		 * 取得したドメインモデル「更新処理自動実行」、「実行タスク設定」、「更新処理自動実行ログ」の情報
		 */
		log.info(":更新処理自動実行_START_" + command.getExecItemCd() + "_" + GeneralDateTime.now());
		this.doProcesses(context, empCalAndSumExeLog, execId, procExec, procExecLog, companyId);

		processExecutionLogManage = this.processExecLogManaRepo.getLogByCIdAndExecCd(companyId, execItemCd).get();
		// アルゴリズム「自動実行登録処理」を実行する
		this.updateDomains(execItemCd, execType, companyId, execId, execSetting, procExecLog, lastExecDateTime,
				processExecutionLogManage);
	}

	private void updateDomains(String execItemCd, int execType, String companyId, String execId,
			ExecutionTaskSetting execSetting, ProcessExecutionLog procExecLog, LastExecDateTime lastExecDateTime,
			ProcessExecutionLogManage processExecutionLogManage) {
		// ドメインモデル「更新処理自動実行ログ履歴」を取得する
		Optional<ProcessExecutionLogHistory> processExecutionLogHistory = procExecLogHistRepo.getByExecId(companyId,
				execItemCd, execId);
		if (!processExecutionLogHistory.isPresent()) {
			// ドメインモデル「更新処理自動実行管理」を更新する
			if (!this.isAbnormalTermEachTask(procExecLog) && (processExecutionLogManage.getOverallStatus() == null
					|| !processExecutionLogManage.getOverallStatus().isPresent())) {
				processExecutionLogManage.setOverallStatus(EndStatus.SUCCESS);
			} else if (this.isAbnormalTermEachTask(procExecLog)) {
				processExecutionLogManage.setOverallStatus(EndStatus.ABNORMAL_END);
			}
			processExecutionLogManage.setCurrentStatus(CurrentExecutionStatus.WAITING);
			this.processExecLogManaRepo.update(processExecutionLogManage);

			List<ExecutionTaskLog> taskLogList = this.execTaskLogRepo.getAllByCidExecCdExecId(companyId, execItemCd,
					execId);
			if (CollectionUtil.isEmpty(taskLogList)) {
				this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
			} else {
				this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
			}

			/*
			 * ドメインモデル「更新処理自動実行ログ」を更新する
			 * 
			 * 【実行タイプ ＝ 1（即時実行）の場合】 前回実行日時 ＝ システム日時 前回実行日時（即時実行を含めない） ＝ システム日時
			 * 
			 * 【実行タイプ ＝ 0（開始時刻）の場合】 前回実行日時 ＝ システム日時
			 * 
			 * if (execType == 1) { procExecLog.setLastExecDateTime(GeneralDateTime.now());
			 * procExecLog.setLastExecDateTimeEx(GeneralDateTime.now()); } else if (execType
			 * == 0) { procExecLog.setLastExecDateTime(GeneralDateTime.now()); }
			 * this.procExecLogRepo.update(procExecLog);
			 * 
			 * 
			 * ドメインモデル「更新処理自動実行ログ履歴」を新規登録する
			 * 
			 * 会社ID ＝ 更新処理自動実行ログ.会社ID 実行ID ＝ 取得した実行ID コード ＝ 更新処理自動実行ログ.コード 前回実行日時 ＝
			 * 更新処理自動実行ログ.前回実行日時 各処理の終了状態(List) ＝ 更新処理自動実行ログ.各処理の終了状態(List) 全体の終了状態 ＝
			 * 更新処理自動実行ログ.全体の終了状態 全体のエラー詳細 ＝ 更新処理自動実行ログ.全体のエラー詳細 各処理の期間 ＝ 更新処理自動実行ログ.各処理の期間
			 */

			this.procExecLogHistRepo.insert(new ProcessExecutionLogHistory(new ExecutionCode(execItemCd), companyId,
					processExecutionLogManage.getOverallError(),
					(processExecutionLogManage.getOverallStatus() != null
							&& processExecutionLogManage.getOverallStatus().isPresent())
									? processExecutionLogManage.getOverallStatus().get()
									: null,
					processExecutionLogManage.getLastExecDateTime(),
					(procExecLog.getEachProcPeriod() != null && procExecLog.getEachProcPeriod().isPresent())
							? procExecLog.getEachProcPeriod().get()
							: null,
					procExecLog.getTaskLogList(), execId));
		} else {
			// Optional<EmpCalAndSumExeLog> empCalAndSumExeLog =
			// this.empCalSumRepo.getByEmpCalAndSumExecLogID(execId);

			// ドメインモデル「就業計算と集計実行ログ」を更新する
			this.empCalSumRepo.updateStatus(execId, ExeStateOfCalAndSum.STOPPING.value);

		}
		// パラメータ.実行タイプのチェック
		if (execType == 1) {
			return;
		}

		/*
		 * ドメインモデル「実行タスク設定」を更新する
		 * 
		 * 次回実行日時 ＝ 次回実行日時を作成する。 ※補足資料⑤参照
		 */
		if (execSetting != null) {
			// execSetting.setNextExecDateTime();
			String scheduleId = execSetting.getScheduleId();
			if (execSetting.isRepeat()) {
				Optional<GeneralDateTime> nextFireTime = this.scheduler.getNextFireTime(scheduleId);
				execSetting.setNextExecDateTime(nextFireTime);
			}
			this.execSettingRepo.update(execSetting);
		}

		/*
		 * ドメインモデル「更新処理前回実行日時」を更新する 前回実行日時 ＝ システム日時
		 */
		lastExecDateTime.setLastExecDateTime(GeneralDateTime.now());
		this.lastExecDateTimeRepo.update(lastExecDateTime);
	}

	@Inject
	private AppDataInfoDailyRepository appDataInfoDailyRepo;

	@Inject
	private AppDataInfoMonthlyRepository appDataInfoMonthlyRepo;

	/**
	 * 各処理を実行する
	 * 
	 * @param execId
	 *            実行ID
	 * @param procExec
	 *            更新処理自動実行
	 * @param execSetting
	 *            実行タスク設定
	 * @param procExecLog
	 *            更新処理自動実行ログ
	 */

	// true interrupt false non interrupt
	// 各処理の分岐
	private boolean doProcesses(CommandHandlerContext<ExecuteProcessExecutionCommand> context,
			EmpCalAndSumExeLog empCalAndSumExeLog, String execId, ProcessExecution procExec,
			ProcessExecutionLog procExecLog, String companyId) {
		// Initialize status [未実施] for each task
		initAllTaskStatus(procExecLog, EndStatus.NOT_IMPLEMENT);
		/*
		 * スケジュールの作成 【パラメータ】 実行ID 取得したドメインモデル「更新処理自動実行」、「実行タスク設定」、「更新処理自動実行ログ」の情報
		 */
		if (!this.createSchedule(context, execId, procExec, procExecLog)) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			// 各処理の終了状態 ＝ [スケジュールの作成、強制終了]
			if (procExec.getProcessExecType() == ProcessExecType.RE_CREATE) {
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.ABNORMAL_END);
			} else {
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.FORCE_END);
			}
			// 各処理の終了状態 ＝ [日別作成、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [日別計算、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認結果反映、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [月別集計、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [アラーム抽出、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
			return true;
		} else if (!this.createDailyData(context, empCalAndSumExeLog, execId, procExec, procExecLog)) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			// 各処理の終了状態 ＝ [日別作成、強制終了]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.FORCE_END);
			// 各処理の終了状態 ＝ [日別計算、強制終了]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.FORCE_END);
			// 各処理の終了状態 ＝ [承認結果反映、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [月別集計、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [アラーム抽出、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
			return true;
		} else if (this.reflectApprovalResult(execId, procExec, procExecLog, companyId)) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			// 各処理の終了状態 ＝ [承認結果反映、強制終了]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.FORCE_END);
			// 各処理の終了状態 ＝ [月別集計、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [アラーム抽出、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
			return true;
		} else if (this.monthlyAggregation(execId, procExec, procExecLog, companyId, context)) {
			// 各処理の終了状態 ＝ [月別集計、強制終了]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.FORCE_END);
			// 各処理の終了状態 ＝ [アラーム抽出、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
			return true;
		} else if (this.alarmExtraction(execId, procExec, procExecLog, companyId, context)) {
			// 各処理の終了状態 ＝ [アラーム抽出、強制終了]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.FORCE_END);
			// 各処理の終了状態 ＝ [承認ルート更新（日次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
			return true;
		}
		// 就業担当者の社員ID（List）を取得する : RQ526
		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
		List<ExecutionTaskLog> taskLogLists = procExecLog.getTaskLogList();
		// ドメインモデル「更新処理自動実行ログ」を取得しチェックする（中断されている場合は更新されているため、最新の情報を取得する）
		Optional<ProcessExecutionLog> processExecutionLog = procExecLogRepo.getLogByCIdAndExecCd(companyId,
				context.getCommand().getExecItemCd(), execId);

		boolean checkErrAppDaily = false;
		OutputAppRouteDaily outputAppRouteDaily = new OutputAppRouteDaily(); 
		try {
			// 承認ルート更新（日次）
			outputAppRouteDaily = this.appRouteUpdateDailyService.checkAppRouteUpdateDaily(execId, procExec, procExecLog);
			if(outputAppRouteDaily.isCheckStop()) {
				// 各処理の終了状態 ＝ [承認ルート更新（日次）、強制終了]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.FORCE_END);
				// 各処理の終了状態 ＝ [承認ルート更新（月次）、未実施]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);
				return true;
			}
		} catch (Exception e) {
			checkErrAppDaily = true;
			appDataInfoDailyRepo.addAppDataInfoDaily(new AppDataInfoDaily("System", execId, new ErrorMessageRC(TextResource.localize("Msg_1339"))));
		}

		if (procExec.getExecSetting().getAppRouteUpdateDaily().getAppRouteUpdateAtr() == NotUseAtr.USE) {
			List<AppDataInfoDaily> listErrorApprovalDaily = appDataInfoDailyRepo.getAppDataInfoDailyByExeID(execId);
			if (!listErrorApprovalDaily.isEmpty()) {
				checkErrAppDaily = true;
			}

			ExecutionLogImportFn paramDaily = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpIdDaily = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			paramDaily.setCompanyId(companyId);
			// 管理社員ID ＝
			paramDaily.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			paramDaily.setFinishDateTime(GeneralDateTime.now());

			// 実行内容 ＝ スケジュール作成
			paramDaily.setExecutionContent(AlarmCategoryFn.APPROVAL_DAILY);
			// ドメインモデル「エラーメッセージ情報」を取得する
			if (!checkErrAppDaily) {
				if (processExecutionLog.isPresent()) {
					paramDaily.setTargerEmployee(Collections.emptyList());
					paramDaily.setExistenceError(0);
					// アルゴリズム「実行ログ登録」を実行する 2290
					executionLogAdapterFn.updateExecuteLog(paramDaily);
				}
			} else {
				if (processExecutionLog.isPresent()) {
					// ドメインモデル「更新処理自動実行ログ」を更新する
					for (int i = 0; i < processExecutionLog.get().getTaskLogList().size(); i++) {
						ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
						if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.APP_ROUTE_U_DAI.value) {
							executionTaskLog.setStatus(Optional.ofNullable(EndStatus.ABNORMAL_END));
							this.procExecLogRepo.update(procExecLog);
						}
					}
					// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
					paramDaily.setCompanyId(companyId);
					// 管理社員ID ＝
					paramDaily.setManagerId(listManagementId);
					// エラーの有無 ＝ エラーあり
					paramDaily.setExistenceError(1);
					// 実行内容 ＝ 月別実績の集計
					paramDaily.setExecutionContent(AlarmCategoryFn.APPROVAL_DAILY);
					if (listErrorApprovalDaily.isEmpty()) {
						if(!outputAppRouteDaily.isCheckError1552Daily()) {
							for (String managementId : listManagementId) {
								listErrorAndEmpIdDaily.add(
										new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
							}
						}
					} else {
						for (AppDataInfoDaily appDataInfoDaily : listErrorApprovalDaily) {
							listErrorAndEmpIdDaily.add(new ExecutionLogErrorDetailFn(
									appDataInfoDaily.getErrorMessage().v(), appDataInfoDaily.getEmployeeId()));
						}
					}
					paramDaily.setTargerEmployee(listErrorAndEmpIdDaily);
					// アルゴリズム「実行ログ登録」を実行する 2290
					executionLogAdapterFn.updateExecuteLog(paramDaily);
				}
			}
		}

		boolean checkErrAppMonth = false;
		OutputAppRouteMonthly outputAppRouteMonthly = new OutputAppRouteMonthly(); 
		try {
			// 承認ルート更新（月次）
			outputAppRouteMonthly = this.appRouteUpdateMonthlyService.checkAppRouteUpdateMonthly(execId, procExec, procExecLog);
			if(outputAppRouteMonthly.isCheckStop()) {
				// 各処理の終了状態 ＝ [承認ルート更新（月次）、強制終了]
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.FORCE_END);
				return true;
			}
		} catch (Exception e) {
			checkErrAppMonth = true;
			appDataInfoMonthlyRepo.addAppDataInfoMonthly(new AppDataInfoMonthly("System", execId, new ErrorMessageRC(TextResource.localize("Msg_1339"))));
		}
		if (procExec.getExecSetting().getAppRouteUpdateMonthly() == NotUseAtr.USE) {
			List<AppDataInfoMonthly> listErrorApprovalMonthly = appDataInfoMonthlyRepo
					.getAppDataInfoMonthlyByExeID(execId);
			if (!listErrorApprovalMonthly.isEmpty()) {
				checkErrAppMonth = true;
			}

			ExecutionLogImportFn paramMonthly = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpIdMonthly = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			paramMonthly.setCompanyId(companyId);
			// 管理社員ID ＝
			paramMonthly.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			paramMonthly.setFinishDateTime(GeneralDateTime.now());

			// 実行内容 ＝ スケジュール作成
			paramMonthly.setExecutionContent(AlarmCategoryFn.APPROVAL_MONTHLY);
			// ドメインモデル「エラーメッセージ情報」を取得する
			if (!checkErrAppMonth) {
				if (processExecutionLog.isPresent()) {
					paramMonthly.setTargerEmployee(Collections.emptyList());
					paramMonthly.setExistenceError(0);
					// アルゴリズム「実行ログ登録」を実行する 2290
					executionLogAdapterFn.updateExecuteLog(paramMonthly);
				}
			} else {
				if (processExecutionLog.isPresent()) {
					// ドメインモデル「更新処理自動実行ログ」を更新する
					for (int i = 0; i < processExecutionLog.get().getTaskLogList().size(); i++) {
						ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
						if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.APP_ROUTE_U_MON.value) {
							executionTaskLog.setStatus(Optional.ofNullable(EndStatus.ABNORMAL_END));
							this.procExecLogRepo.update(procExecLog);
						}
					}
					// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
					paramMonthly.setCompanyId(companyId);
					// 管理社員ID ＝
					paramMonthly.setManagerId(listManagementId);
					// エラーの有無 ＝ エラーあり
					paramMonthly.setExistenceError(1);
					// 実行内容 ＝ 月別実績の集計
					paramMonthly.setExecutionContent(AlarmCategoryFn.APPROVAL_MONTHLY);
					if (listErrorApprovalMonthly.isEmpty()) {
						if(!outputAppRouteMonthly.isCheckError1552Monthly()) {
							for (String managementId : listManagementId) {
								listErrorAndEmpIdMonthly.add(
										new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
							}
						}
					} else {
						for (AppDataInfoMonthly appDataInfoMonthly : listErrorApprovalMonthly) {
							listErrorAndEmpIdMonthly.add(new ExecutionLogErrorDetailFn(
									appDataInfoMonthly.getErrorMessage().v(), appDataInfoMonthly.getEmployeeId()));
						}
					}
					paramMonthly.setTargerEmployee(listErrorAndEmpIdMonthly);
					// アルゴリズム「実行ログ登録」を実行する 2290
					executionLogAdapterFn.updateExecuteLog(paramMonthly);
				}
			}
		}
		return false;
	}

	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	/**
	 * スケジュールの作成
	 * 
	 * @param execId
	 *            実行ID
	 * @param procExec
	 *            更新処理自動実行
	 * @param execSetting
	 *            実行タスク設定
	 * @param procExecLog
	 *            更新処理自動実行ログ
	 */
	private boolean createSchedule(CommandHandlerContext<ExecuteProcessExecutionCommand> context, String execId,
			ProcessExecution procExec, ProcessExecutionLog procExecLog) {

		if (context.asAsync().hasBeenRequestedToCancel()) {
			return false;
		}
		boolean checkError1552  = false;
		// Login user context
		LoginUserContext loginContext = AppContexts.user();
		// ドメインモデル「更新処理自動実行ログ」を更新する
		this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, null);
		String companyId = context.getCommand().getCompanyId();
		String execItemCd = context.getCommand().getExecItemCd();
		List<ExecutionTaskLog> taskLogList = this.execTaskLogRepo.getAllByCidExecCdExecId(companyId, execItemCd,
				execId);
		if (CollectionUtil.isEmpty(taskLogList)) {
			this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		} else {
			this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		}
		this.procExecLogRepo.update(procExecLog);
		AsyncTaskInfo handle = null;

		// 就業担当者の社員ID（List）を取得する : RQ526
		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
		boolean runSchedule = false;
		try {
			// 個人スケジュール作成区分の判定
			if (!procExec.getExecSetting().getPerSchedule().isPerSchedule()) {
				this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.NOT_IMPLEMENT);
				this.procExecLogRepo.update(procExecLog);
				return true;
			}
			log.info("更新処理自動実行_個人スケジュール作成_START_" + context.getCommand().getExecItemCd() + "_" + GeneralDateTime.now());

			// 新入社員作成区分（Boolean）←属性「新入社員を作成」
			boolean checkCreateEmployee = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
					.isCreateEmployee();
			// 期間の計算
			DatePeriod calculateSchedulePeriod = this.calculateSchedulePeriod(procExec, procExecLog,
					checkCreateEmployee);

			/*
			 * 対象社員を取得
			 */
			// List<String> sidList = new ArrayList<>();
			// sidList.add(loginContext.employeeId()); // Add login SID to test, remove when
			// implement this algorithm

			List<ProcessExecutionScopeItem> workplaceIdList = procExec.getExecScope().getWorkplaceIdList();
			List<String> workplaceIds = new ArrayList<String>();
			workplaceIdList.forEach(x -> {
				workplaceIds.add(x.getWkpId());
			});
			// 更新処理自動実行の実行対象社員リストを取得する
			List<String> listEmp = listEmpAutoExec.getListEmpAutoExec(companyId, calculateSchedulePeriod,
					procExec.getExecScope().getExecScopeCls(), Optional.of(workplaceIds), Optional.empty());

			if (context.asAsync().hasBeenRequestedToCancel()) {
				return false;
			}
			/*
			 * 作成対象の判定
			 */
			// 全員の場合
			if (procExec.getExecSetting().getPerSchedule().getTarget()
					.getCreationTarget().value == TargetClassification.ALL.value) {
				// 対象社員を取得 -

				ScheduleCreatorExecutionCommand scheduleCommand = getScheduleCreatorExecutionAllEmp(execId, procExec,
						loginContext, calculateSchedulePeriod, listEmp);

				try {
					handle = this.scheduleExecution.handle(scheduleCommand);
					log.info("更新処理自動実行_個人スケジュール作成_END_" + context.getCommand().getExecItemCd() + "_"
							+ GeneralDateTime.now());
					if (checkStop(execId)) {
						return false;
					}
					runSchedule = true;
				} catch (Exception e) {
					// 再実行の場合にExceptionが発生したかどうかを確認する。
					if (procExec.getProcessExecType() == ProcessExecType.RE_CREATE) {
						return false;
					}
				}
			}
			// 異動者・新入社員のみ作成の場合
			else {
				// DatePeriod period =
				// procExecLog.getEachProcPeriod().get().getScheduleCreationPeriod().get();
				// ・社員ID（異動者、勤務種別変更者、休職者・休業者）（List）
				List<String> reEmployeeList = new ArrayList<>();
				// 社員ID（新入社員）（List）
				List<String> newEmployeeList = new ArrayList<>();
				// 社員ID（休職者・休業者）（List）
				List<String> temporaryEmployeeList = new ArrayList<>();
				// 対象社員を絞り込み -> Đổi tên (異動者・勤務種別変更者リスト作成処理（スケジュール用）)
				// this.filterEmployeeList(procExec, empIds, reEmployeeList, newEmployeeList,
				// temporaryEmployeeList);
				changePersionListForSche.filterEmployeeList(procExec, listEmp, reEmployeeList, newEmployeeList,
						temporaryEmployeeList);
				if (!CollectionUtil.isEmpty(reEmployeeList) && !CollectionUtil.isEmpty(newEmployeeList)) {

				} else {
					// 社員ID（新入社員）（List）のみ and 社員ID（新入社員以外）（List）
					if (!CollectionUtil.isEmpty(newEmployeeList) && !CollectionUtil.isEmpty(temporaryEmployeeList)) {
						try {
							ScheduleCreatorExecutionCommand scheduleCreatorExecutionOneEmp2 = this
									.getScheduleCreatorExecutionOneEmp(execId, procExec, loginContext,
											calculateSchedulePeriod, newEmployeeList);
							// AsyncCommandHandlerContext<ScheduleCreatorExecutionCommand> ctxNew = new
							// AsyncCommandHandlerContext<ScheduleCreatorExecutionCommand>(scheduleCreatorExecutionOneEmp);
							// ctxNew.setTaskId(context.asAsync().getTaskId());
							handle = this.scheduleExecution.handle(scheduleCreatorExecutionOneEmp2);
							ScheduleCreatorExecutionCommand scheduleCreatorExecutionOneEmp3 = this
									.getScheduleCreatorExecutionOneEmp(execId, procExec, loginContext,
											calculateSchedulePeriod, temporaryEmployeeList);
							if (checkStop(execId)) {
								log.info("更新処理自動実行_個人スケジュール作成_END_" + context.getCommand().getExecItemCd() + "_"
										+ GeneralDateTime.now());
								return false;
							}
							handle = this.scheduleExecution.handle(scheduleCreatorExecutionOneEmp3);
							log.info("更新処理自動実行_個人スケジュール作成_END_" + context.getCommand().getExecItemCd() + "_"
									+ GeneralDateTime.now());
							if (checkStop(execId)) {
								return false;
							}
							runSchedule = true;
						} catch (Exception e) {
							// 再実行の場合にExceptionが発生したかどうかを確認する。
							if (procExec.getProcessExecType() == ProcessExecType.RE_CREATE) {
								return false;
							}
						}

					}

					// 社員ID（異動者、勤務種別変更者）（List）のみ
					if (!CollectionUtil.isEmpty(reEmployeeList)) {
						// 異動者、勤務種別変更者、休職者・休業者の期間の計算
						GeneralDate endDate = basicScheduleRepository.findMaxDateByListSid(reEmployeeList);
						if (endDate != null) {
							DatePeriod periodDate = this.getMinPeriodFromStartDate(companyId);
							ScheduleCreatorExecutionCommand scheduleCreatorExecutionOneEmp1 = this
									.getScheduleCreatorExecutionOneEmp(execId, procExec, loginContext,
											calculateSchedulePeriod, reEmployeeList);
							scheduleCreatorExecutionOneEmp1.getScheduleExecutionLog()
									.setPeriod(new DatePeriod(periodDate.start(), endDate));
							try {
								handle = this.scheduleExecution.handle(scheduleCreatorExecutionOneEmp1);
								log.info("更新処理自動実行_個人スケジュール作成_END_" + context.getCommand().getExecItemCd() + "_"
										+ GeneralDateTime.now());
								if (checkStop(execId)) {
									return false;
								}
								runSchedule = true;
							} catch (Exception e) {
								// 再実行の場合にExceptionが発生したかどうかを確認する。
								if (procExec.getProcessExecType() == ProcessExecType.RE_CREATE) {
									return false;
								}
							}
						}
					}

				}
			}
		} catch (Exception e) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.ABNORMAL_END);
			ScheduleErrorLogGeterCommand scheduleErrorLogGeterCommand = new ScheduleErrorLogGeterCommand();
			scheduleErrorLogGeterCommand.setCompanyId(companyId);
			scheduleErrorLogGeterCommand.setExecutionId(execId);
			scheduleErrorLogGeterCommand.setToDate(GeneralDate.today());
			// ドメインモデル「スケジュール作成エラーログ」を取得する
//			List<ScheduleErrorLog> listError = this.scheCreExeErrorLogHandler.getListError(scheduleErrorLogGeterCommand,
//					AppContexts.user().employeeId());
//
//			ExecutionLogImportFn param = new ExecutionLogImportFn();
//			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();

//			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
//			param.setCompanyId(companyId);
//			// 管理社員ID ＝
//			param.setManagerId(listManagementId);
//			// 実行完了日時 ＝ システム日時
//			param.setFinishDateTime(GeneralDateTime.now());
//			// エラーの有無 ＝ エラーあり
//			param.setExistenceError(1);
//			// 実行内容 ＝ スケジュール作成
//			param.setExecutionContent(AlarmCategoryFn.CREATE_SCHEDULE);
			// 実行ログエラー詳細 ＝ 取得したエラーメッセージ情報（社員ID, エラーメッセージ ）（List）

			this.scheCreExeErrorLogHandler.addError(scheduleErrorLogGeterCommand, "System", "Msg_1552");
			checkError1552 = true;
//			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
//			executionLogAdapterFn.updateExecuteLog(param);
		}
		TaskDataSetter dataSetter = context.asAsync().getDataSetter();
		if (handle != null) {
			dataSetter.updateData("taskId", handle.getId());
		}
		int timeOut = 1;
		boolean isInterruption = false;
		if (runSchedule) {
			while (true) {
				// find execution log by id
				Optional<ScheduleExecutionLog> domainOpt = this.scheduleExecutionLogRepository
						.findById(loginContext.companyId(), execId);
				if (domainOpt.isPresent()) {
					if (domainOpt.get().getCompletionStatus().value == CompletionStatus.COMPLETION_ERROR.value) {
						this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION,
								EndStatus.ABNORMAL_END);
						break;
					}
					if (domainOpt.get().getCompletionStatus().value == CompletionStatus.INTERRUPTION.value) {
						isInterruption = true;
						break;
					}
					if (domainOpt.get().getCompletionStatus().value == CompletionStatus.DONE.value) {
						this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.SUCCESS);
						break;
					}

				}
				if (timeOut == 2400) {
					this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.ABNORMAL_END);
					break;
				}
				timeOut++;
				// set thread sleep 10s để cho xử lý schedule insert xong data rồi
				// mới cho xử lý của anh Nam (KIF001) chạy
				// nếu không màn KIF001 sẽ get data cũ của màn schedule để insert
				// vào => như thế sẽ sai
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else {
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.SUCCESS);
			log.info("更新処理自動実行_個人スケジュール作成_END_" + context.getCommand().getExecItemCd() + "_"+ GeneralDateTime.now());
		}

		this.procExecLogRepo.update(procExecLog);
		ScheduleErrorLogGeterCommand scheduleErrorLogGeterCommand = new ScheduleErrorLogGeterCommand();
		scheduleErrorLogGeterCommand.setCompanyId(companyId);
		scheduleErrorLogGeterCommand.setExecutionId(execId);
		scheduleErrorLogGeterCommand.setToDate(GeneralDate.today());
		scheduleErrorLogRepository.findByExecutionId(execId);
		// ドメインモデル「スケジュール作成エラーログ」を取得する
		List<ScheduleErrorLog> listError = this.scheduleErrorLogRepository.findByExecutionId(execId);
		if (listError != null && !listError.isEmpty()) {
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();

			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// エラーの有無 ＝ エラーあり
			param.setExistenceError(1);
			// 実行内容 ＝ スケジュール作成
			param.setExecutionContent(AlarmCategoryFn.CREATE_SCHEDULE);
			// 実行ログエラー詳細 ＝ 取得したエラーメッセージ情報（社員ID, エラーメッセージ ）（List）
			if (listError.isEmpty()) {
				if(!checkError1552) {
					this.scheCreExeErrorLogHandler.addError(scheduleErrorLogGeterCommand, "System", "Msg_1339");
					for (String managementId : listManagementId) {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
					}
				}
			} else {
				for (ScheduleErrorLog scheduleErrorLog : listError) {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(scheduleErrorLog.getErrorContent(),
							scheduleErrorLog.getEmployeeId()));
				}
			}
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);
		} else {
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// 実行内容 ＝ スケジュール作成
			param.setExecutionContent(AlarmCategoryFn.CREATE_SCHEDULE);
			// エラーの有無 ＝ エラーなし
			param.setExistenceError(0);
			// 実行ログエラー詳細 ＝ NULL
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);
		}
		dataSetter.updateData("taskId", context.asAsync().getTaskId());
		dataSetter.setData("createSchedule", "done");
		if (isInterruption) {
			return false;
		}

		// 再実行の場合にExceptionが発生したかどうかを確認する。
		// if(isException ==false && (procExec.getProcessExecType() ==
		// ProcessExecType.RE_CREATE) ) {
		// return false;
		// }

		return true;
	}

	private boolean checkStop(String execId) {
		Optional<ExeStateOfCalAndSumImportFn> exeStateOfCalAndSumImportFn = dailyMonthlyprocessAdapterFn
				.executionStatus(execId);
		if (exeStateOfCalAndSumImportFn.isPresent())
			if (exeStateOfCalAndSumImportFn.get() == ExeStateOfCalAndSumImportFn.START_INTERRUPTION) {
				return true;
			}
		return false;
	}

	private ScheduleCreatorExecutionCommand getScheduleCreatorExecutionAllEmp(String execId, ProcessExecution procExec,
			LoginUserContext loginContext, DatePeriod calculateSchedulePeriod, List<String> empIds) {
		ScheduleCreatorExecutionCommand scheduleCommand = new ScheduleCreatorExecutionCommand();
		scheduleCommand.setConfirm(false);
		scheduleCommand.setExecutionId(execId);
		scheduleCommand.setAutomatic(true);
		scheduleCommand.setEmployeeIds(empIds);
		// 2-対象開始日 ＝ 「期間の計算」で作成した開始日とする
		// companyId
		scheduleCommand.setCompanyId(loginContext.companyId());
		// 3-対象開始日 ＝ 「期間の計算」で作成した開始日とする
		// 4-対象終了日 ＝ 「期間の計算」で作成した終了日とする
		// calculateSchedulePeriod
		ScheduleExecutionLog scheduleExecutionLog = new ScheduleExecutionLog();
		scheduleExecutionLog.setPeriod(new DatePeriod(calculateSchedulePeriod.start(), calculateSchedulePeriod.end()));
		scheduleExecutionLog.setCompanyId(new CompanyId(loginContext.companyId()));
		scheduleExecutionLog.setExecutionId(execId);
		// 【ドメインモデル「作成対象詳細設定」．異動者を再作成する = "する" or ドメインモデル「作成対象詳細設定」．勤務種別変更者を再作成 = "する"
		// の場合】
		boolean recreateTransfer = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isRecreateTransfer();
		boolean recreateWorkType = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isRecreateWorkType();
		ScheduleCreateContent s = new ScheduleCreateContent();
		// 1-実行ID ＝ 取得した実行ID
		// execId
		s.setExecutionId(execId);
		ReCreateContent reCreateContent = new ReCreateContent();
		if (recreateTransfer || recreateWorkType) {
			// 6-実施区分 → 再作成 とする
			s.setImplementAtr(ImplementAtr.RECREATE);
			// 7-再作成区分 → 未確定データのみ とする
			reCreateContent.setReCreateAtr(ReCreateAtr.ONLY_UNCONFIRM);
			// 8-処理実行区分 → もう一度作り直す とする
			reCreateContent.setProcessExecutionAtr(ProcessExecutionAtr.REBUILD);
		} else {
			// #107055
			// ・実施区分 → 通常作成
			s.setImplementAtr(ImplementAtr.GENERALLY_CREATED);
			// ・再作成区分 → 全件 とする
			reCreateContent.setReCreateAtr(ReCreateAtr.ALL_CASE);
			// ・処理実行区分 → もう一度作り直す とする
			reCreateContent.setProcessExecutionAtr(ProcessExecutionAtr.REBUILD);
		}
		// ・9-マスタ情報再設定 → falseとする
		ResetAtr r = new ResetAtr();
		r.setResetMasterInfo(false);
		// 10-申し送り時間再設定 → falseとする
		r.setResetTimeAssignment(false);
		// ・11-作成時に確定済みにする → falseとする
		s.setConfirm(false);
		// ・12-作成方法区分 → 個人情報とする
		s.setCreateMethodAtr(CreateMethodAtr.PERSONAL_INFO);
		// 13-コピー開始日 → nullとする

		// 14-パターンコード → nullとする

		// 15-休日反映方法 → nullとする

		// 16-パターン開始日 → nullとする

		// 17-法内休日利用区分 → nullとする

		// 18-法内休日勤務種類 → nullとする

		// 19-法外休日利用区分 → nullとする

		// 20-法外休日勤務種類 → nullとする

		// 21-祝日利用区分 → nullとする

		// 22-祝日勤務種類 → nullとする

		// 23-実行区分 ＝ 自動
		scheduleExecutionLog.setExeAtr(ExecutionAtr.AUTOMATIC);
		RebuildTargetDetailsAtr rebuildTargetDetailsAtr = new RebuildTargetDetailsAtr();
		if (recreateTransfer) {
			// 24-異動者を再作成 → true
			rebuildTargetDetailsAtr.setRecreateConverter(true);

		} else {
			// 異動者を再作成 → false
			rebuildTargetDetailsAtr.setRecreateConverter(false);
		}
		if (recreateWorkType) {
			// 25-・勤務種別変更者を再作成 → true
			rebuildTargetDetailsAtr.setRecreateWorkTypeChange(true);
		} else {
			// ・勤務種別変更者を再作成 → false
			rebuildTargetDetailsAtr.setRecreateWorkTypeChange(false);
		}
		// 【ドメインモデル「作成対象詳細設定」．手修正を保護する = "する" 】
		boolean manualCorrection = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isManualCorrection();
		if (manualCorrection) {
			// 26-・手修正を保護 → true
			rebuildTargetDetailsAtr.setProtectHandCorrection(true);
		} else {
			// 手修正を保護 → false
			rebuildTargetDetailsAtr.setProtectHandCorrection(false);
		}

		// 27-再作成対象区分 → 対象者のみ
		reCreateContent.setRebuildTargetAtr(RebuildTargetAtr.TARGET_ONLY);
		// 28-休職休業者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateEmployeeOffWork(false);
		// 29-・直行直帰者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateDirectBouncer(false);
		// 30短時間勤務者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateShortTermEmployee(false);
		// 31勤務開始・終了時刻を再設定 → falseとする
		r.setResetWorkingHours(false);
		// 32休憩開始・終了時刻を再設定 → falseとする
		r.setResetStartEndTime(false);

		reCreateContent.setRebuildTargetDetailsAtr(rebuildTargetDetailsAtr);
		reCreateContent.setResetAtr(r);
		s.setReCreateContent(reCreateContent);
		scheduleCommand.setScheduleExecutionLog(scheduleExecutionLog);
		scheduleCommand.setContent(s);
		return scheduleCommand;
	}

	/***
	 * @param execId
	 * @param procExec
	 * @param loginContext
	 * @param calculateSchedulePeriod
	 * @param empIds
	 * @return
	 */
	private ScheduleCreatorExecutionCommand getScheduleCreatorExecutionOneEmp(String execId, ProcessExecution procExec,
			LoginUserContext loginContext, DatePeriod calculateSchedulePeriod, List<String> empIds) {
		ScheduleCreatorExecutionCommand scheduleCommand = new ScheduleCreatorExecutionCommand();
		scheduleCommand.setConfirm(false);
		scheduleCommand.setAutomatic(true);
		scheduleCommand.setEmployeeIds(empIds);
		// 1-実行ID ＝ 取得した実行ID
		// execId
		scheduleCommand.setExecutionId(execId);
		// 2-対象開始日 ＝ 「期間の計算」で作成した開始日とする
		// companyId
		scheduleCommand.setCompanyId(loginContext.companyId());
		// 3-対象開始日 ＝ 「期間の計算」で作成した開始日とする
		// 4-対象終了日 ＝ 「期間の計算」で作成した終了日とする
		// calculateSchedulePeriod
		ScheduleExecutionLog scheduleExecutionLog = new ScheduleExecutionLog();
		scheduleExecutionLog.setPeriod(new DatePeriod(calculateSchedulePeriod.start(), calculateSchedulePeriod.end()));
		scheduleExecutionLog.setCompanyId(new CompanyId(loginContext.companyId()));
		scheduleExecutionLog.setExecutionId(execId);
		// 【ドメインモデル「作成対象詳細設定」．異動者を再作成する = "する" or ドメインモデル「作成対象詳細設定」．勤務種別変更者を再作成 = "する"
		// の場合】
		boolean recreateTransfer = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isRecreateTransfer();
		boolean recreateWorkType = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isRecreateWorkType();
		ScheduleCreateContent s = new ScheduleCreateContent();
		s.setExecutionId(execId);
		ReCreateContent reCreateContent = new ReCreateContent();
		if (recreateTransfer || recreateWorkType) {
			// 6-実施区分 → 再作成 とする
			s.setImplementAtr(ImplementAtr.RECREATE);
			// 7-再作成区分 → 未確定データのみ とする

			reCreateContent.setReCreateAtr(ReCreateAtr.ONLY_UNCONFIRM);
			// 8-処理実行区分 → もう一度作り直す とする
			reCreateContent.setProcessExecutionAtr(ProcessExecutionAtr.REBUILD);
		} else {
			// ・実施区分 → 再作成 とする
			s.setImplementAtr(ImplementAtr.RECREATE);
			// ・再作成区分 → 全件 とする
			reCreateContent.setReCreateAtr(ReCreateAtr.ALL_CASE);
			// ・処理実行区分 → もう一度作り直す とする
			reCreateContent.setProcessExecutionAtr(ProcessExecutionAtr.REBUILD);
		}
		// ・9-マスタ情報再設定 → falseとする
		ResetAtr r = new ResetAtr();
		r.setResetMasterInfo(false);
		// 10-申し送り時間再設定 → falseとする
		r.setResetTimeAssignment(false);
		// ・11-作成時に確定済みにする → falseとする
		s.setConfirm(false);
		// ・12-作成方法区分 → 個人情報とする
		s.setCreateMethodAtr(CreateMethodAtr.PERSONAL_INFO);
		// 13-コピー開始日 → nullとする

		// 14-パターンコード → nullとする

		// 15-休日反映方法 → nullとする

		// 16-パターン開始日 → nullとする

		// 17-法内休日利用区分 → nullとする

		// 18-法内休日勤務種類 → nullとする

		// 19-法外休日利用区分 → nullとする

		// 20-法外休日勤務種類 → nullとする

		// 21-祝日利用区分 → nullとする

		// 22-祝日勤務種類 → nullとする

		// 23-実行区分 ＝ 自動
		scheduleExecutionLog.setExeAtr(ExecutionAtr.AUTOMATIC);

		RebuildTargetDetailsAtr rebuildTargetDetailsAtr = new RebuildTargetDetailsAtr();
		if (recreateTransfer) {
			// 24-異動者を再作成 → true
			rebuildTargetDetailsAtr.setRecreateConverter(true);

		} else {
			// 異動者を再作成 → false
			rebuildTargetDetailsAtr.setRecreateConverter(false);
		}
		if (recreateWorkType) {
			// 25-・勤務種別変更者を再作成 → true
			rebuildTargetDetailsAtr.setRecreateWorkTypeChange(true);
		} else {
			// ・勤務種別変更者を再作成 → false
			rebuildTargetDetailsAtr.setRecreateWorkTypeChange(false);
		}
		// 【ドメインモデル「作成対象詳細設定」．手修正を保護する = "する" 】
		boolean manualCorrection = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting()
				.isManualCorrection();
		if (manualCorrection) {
			// 26-・手修正を保護 → true
			rebuildTargetDetailsAtr.setProtectHandCorrection(true);
		} else {
			// 手修正を保護 → false
			rebuildTargetDetailsAtr.setProtectHandCorrection(false);
		}

		// 27-再作成対象区分 → 対象者のみ
		reCreateContent.setRebuildTargetAtr(RebuildTargetAtr.TARGET_ONLY);
		// 28-休職休業者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateEmployeeOffWork(false);
		// 29-・直行直帰者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateDirectBouncer(false);
		// 30短時間勤務者を再作成 → falseとする
		rebuildTargetDetailsAtr.setRecreateShortTermEmployee(false);
		// 31勤務開始・終了時刻を再設定 → falseとする
		r.setResetWorkingHours(false);
		// 32休憩開始・終了時刻を再設定 → falseとする
		r.setResetStartEndTime(false);

		reCreateContent.setRebuildTargetDetailsAtr(rebuildTargetDetailsAtr);
		reCreateContent.setResetAtr(r);
		;
		s.setReCreateContent(reCreateContent);
		scheduleCommand.setScheduleExecutionLog(scheduleExecutionLog);
		scheduleCommand.setContent(s);
		return scheduleCommand;
	}

	/**
	 * 実行
	 * 
	 * @param execId
	 */
	// private void executeScheduleCreation(String execId) {
	// ScheduleCreatorExecutionCommand scheduleCommand = new
	// ScheduleCreatorExecutionCommand();
	// scheduleCommand.setExecutionId(execId);
	// this.scheduleExecution.handle(scheduleCommand);
	// }

	/**
	 * 日別作成・計算
	 * 
	 * @param execId
	 *            実行ID
	 * @param procExec
	 *            更新処理自動実行
	 * @param execSetting
	 *            実行タスク設定
	 * @param procExecLog
	 *            更新処理自動実行ログ
	 */
	private boolean createDailyData(CommandHandlerContext<ExecuteProcessExecutionCommand> context,
			EmpCalAndSumExeLog empCalAndSumExeLog, String execId, ProcessExecution procExec,
			ProcessExecutionLog procExecLog) {
		context.asAsync().getDataSetter().updateData("taskId", context.asAsync().getTaskId());
		ExecuteProcessExecutionCommand command = context.getCommand();
		boolean checkError1552 = false;
		String companyId = command.getCompanyId();
		// ドメインモデル「更新処理自動実行ログ」を更新する
		this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, null);
		this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, null);
		this.procExecLogRepo.update(procExecLog);

		// 日別実績の作成・計算区分の判定
		if (!procExec.getExecSetting().getDailyPerf().isDailyPerfCls()) {
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
			this.procExecLogRepo.update(procExecLog);
			return true;
		}
		log.info("更新処理自動実行_日別実績の作成・計算_START_"+procExec.getExecItemCd()+"_"+GeneralDateTime.now());
		String execItemCd = context.getCommand().getExecItemCd();
		List<ExecutionTaskLog> taskLogList = this.execTaskLogRepo.getAllByCidExecCdExecId(companyId, execItemCd,
				execId);
		if (CollectionUtil.isEmpty(taskLogList)) {
			this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		} else {
			this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		}

		// ドメインモデル「就業締め日」を取得する
		List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
		DatePeriod period = this.findClosureMinMaxPeriod(companyId, closureList);

		// ドメインモデル「実行ログ」を新規登録する
		ExecutionLog dailyCreateLog = new ExecutionLog(execId, ExecutionContent.DAILY_CREATION, ErrorPresent.NO_ERROR,
				new ExecutionTime(GeneralDateTime.now(), GeneralDateTime.now()), ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(period.end(), period.end()));
		dailyCreateLog.setDailyCreationSetInfo(
				new SettingInforForDailyCreation(ExecutionContent.DAILY_CREATION, ExecutionType.NORMAL_EXECUTION,
						IdentifierUtil.randomUniqueId(), DailyRecreateClassification.REBUILD, Optional.empty()));
		ExecutionLog dailyCalLog = new ExecutionLog(execId, ExecutionContent.DAILY_CALCULATION, ErrorPresent.NO_ERROR,
				new ExecutionTime(GeneralDateTime.now(), GeneralDateTime.now()), ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(period.start(), period.end()));
		dailyCalLog.setDailyCalSetInfo(new CalExeSettingInfor(ExecutionContent.DAILY_CALCULATION,
				ExecutionType.NORMAL_EXECUTION, IdentifierUtil.randomUniqueId()));
		this.executionLogRepository.addExecutionLog(dailyCreateLog);
		this.executionLogRepository.addExecutionLog(dailyCalLog);

		// ドメインモデル「更新処理自動実行ログ」を更新する
		if (procExecLog.getEachProcPeriod() != null && procExecLog.getEachProcPeriod().isPresent()) {
			EachProcessPeriod eachProcessPeriod = procExecLog.getEachProcPeriod().get();
			DatePeriod scheduleCreationPeriod = (eachProcessPeriod.getScheduleCreationPeriod() != null
					&& eachProcessPeriod.getScheduleCreationPeriod().isPresent())
							? eachProcessPeriod.getScheduleCreationPeriod().get()
							: null;
			DatePeriod reflectApprovalResult = (eachProcessPeriod.getReflectApprovalResult() != null
					&& eachProcessPeriod.getReflectApprovalResult().isPresent())
							? eachProcessPeriod.getReflectApprovalResult().get()
							: null;
			procExecLog.setEachProcPeriod(
					new EachProcessPeriod(scheduleCreationPeriod, period, period, reflectApprovalResult));
		} else {
			procExecLog.setEachProcPeriod(new EachProcessPeriod(null, period, period, null));
		}

		boolean isHasCreateDailyException = false;
		boolean isHasDailyCalculateException = false;
		// 就業担当者の社員ID（List）を取得する : RQ526
		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());

		try {
			for (Closure closure : closureList) {

				// 雇用コードを取得する
				List<ClosureEmployment> employmentList = this.closureEmpRepo.findByClosureId(companyId,
						closure.getClosureId().value);
				List<String> lstEmploymentCode = new ArrayList<String>();
				employmentList.forEach(x -> {
					lstEmploymentCode.add(x.getEmploymentCD());
				});
				List<ProcessExecutionScopeItem> workplaceIdList = procExec.getExecScope().getWorkplaceIdList();
				List<String> workPlaceIds = new ArrayList<String>();
				workplaceIdList.forEach(x -> {
					workPlaceIds.add(x.getWkpId());
				});
				 
				if (procExec.getProcessExecType() == ProcessExecType.NORMAL_EXECUTION) {
					// 実行呼び出し処理
					// 期間の計算
					DailyCreatAndCalOutput calculateDailyPeriod = this.calculateDailyPeriod(procExec,
							closure.getClosureId().value, closure.getClosureMonth());
					if (calculateDailyPeriod == null)
						continue;
					// 更新処理自動実行の実行対象社員リストを取得する
					List<String> listEmp = listEmpAutoExec.getListEmpAutoExec(companyId,
							calculateDailyPeriod.getDailyCreationPeriod(), procExec.getExecScope().getExecScopeCls(),
							Optional.of(workPlaceIds), Optional.of(lstEmploymentCode));
					String typeExecution = "日別作成";
					// 日別実績の作成
					// boolean dailyPerformanceCreation = this.dailyPerformanceCreation(
					// companyId,context, procExec, empCalAndSumExeLog,
					// createProcessForChangePerOrWorktype.getNoLeaderEmpIdList(),
					// calculateDailyPeriod.getDailyCreationPeriod(), workPlaceIds,
					// typeExecution,dailyCreateLog);
					try {
						log.info("更新処理自動実行_日別実績の作成_START_"+procExec.getExecItemCd()+"_"+GeneralDateTime.now());
						boolean dailyPerformanceCreation = this.dailyPerformanceCreation(companyId, context, procExec,
								empCalAndSumExeLog, listEmp, calculateDailyPeriod.getDailyCreationPeriod(), workPlaceIds,
								typeExecution, dailyCreateLog);

						if (dailyPerformanceCreation) {
							return false;
						}
					} catch (CreateDailyException ex) {
						isHasCreateDailyException = true;
					}
					// boolean dailyPerformanceCreation = this.dailyPerformanceCreation(
					// companyId,context, procExec, empCalAndSumExeLog, empIds,
					// calculateDailyPeriod.getDailyCreationPeriod(), workPlaceIds,
					// typeExecution,dailyCreateLog);
					//
					// if(dailyPerformanceCreation){
					// return false;
					// }
					log.info("更新処理自動実行_日別実績の作成_END_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now()
							+ "closure : " + closure.getClosureId().value);
					log.info("更新処理自動実行_日別実績の計算_START_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now()
							+ "closure : " + closure.getClosureId().value);

					typeExecution = "日別計算";
					// 日別実績の計算
					try {
						boolean dailyPerformanceCreation2 = this.dailyPerformanceCreation(companyId, context, procExec,
								empCalAndSumExeLog, listEmp, calculateDailyPeriod.getDailyCalcPeriod(), workPlaceIds,
								typeExecution, dailyCalLog);
						log.info("更新処理自動実行_日別実績の計算_END_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now());
						if (dailyPerformanceCreation2) {
							return false;
						}
					} catch (DailyCalculateException ex) {
						isHasDailyCalculateException = true;
					}

				} else {
					GeneralDate calculateDate = this.calculatePeriod(closure.getClosureId().value, period, companyId);
					// 更新処理自動実行の実行対象社員リストを取得する
					List<String> listEmp = listEmpAutoExec.getListEmpAutoExec(companyId,
							new DatePeriod(calculateDate, GeneralDate.ymd(9999, 12, 31)),
							procExec.getExecScope().getExecScopeCls(), Optional.of(workPlaceIds),
							Optional.of(lstEmploymentCode));
					
					// 異動者・勤務種別変更者リスト作成処理
//					 ListLeaderOrNotEmpOutput createProcessForChangePerOrWorktype = this
//					 .createProcessForChangePerOrWorktype(companyId, empIds,
//					 calculateDate, procExec);
					ListLeaderOrNotEmp listLeaderOrNotEmp = changePersionList
							.createProcessForChangePerOrWorktype(companyId, listEmp, calculateDate, procExec);

					boolean isHasInterrupt = false;
					for (String empLeader : listLeaderOrNotEmp.getLeaderEmpIdList()) {
						// ドメインモデル「日別実績の勤務情報」を取得する
						List<WorkInfoOfDailyPerFnImport> listWorkInfo = recordWorkInfoFunAdapter
								.findByPeriodOrderByYmd(empLeader);
						
						if (listWorkInfo.isEmpty())
							continue;
						// 再作成処理
						// 日別実績処理の再実行
						// 「作成した開始日」～「取得した日別実績の勤務情報.年月日」を対象期間とする
						GeneralDate maxDate = listWorkInfo.stream().map(u -> u.getYmd()).max(GeneralDate::compareTo)
								.get();
						if (calculateDate.beforeOrEquals(maxDate)) {
							DatePeriod datePeriod = new DatePeriod(calculateDate, maxDate);
							List<DatePeriod> listDatePeriodWorkplace = new ArrayList<>();
							List<DatePeriod> listDatePeriodWorktype = new ArrayList<>();
							List<DatePeriod> listDatePeriodAll = new ArrayList<>();
							//INPUT．「異動時に再作成」をチェックする
							if(procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTransfer()) {
								//社員ID（List）と期間から個人情報を取得する - RQ401	
								EmployeeGeneralInfoImport employeeGeneralInfoImport = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(Arrays.asList(empLeader), datePeriod, false, false, false, true, false); //職場を取得するか　=　True
								if(!employeeGeneralInfoImport.getExWorkPlaceHistoryImports().isEmpty()) {
									nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkPlaceHistoryImport exWorkPlaceHistoryImportFn = employeeGeneralInfoImport.getExWorkPlaceHistoryImports().get(0);
									List<ExWorkplaceHistItemImport> workplaceItems = exWorkPlaceHistoryImportFn
											.getWorkplaceItems().stream()
											.map(c -> new ExWorkplaceHistItemImport(c.getHistoryId(), c.getPeriod(),
													c.getWorkplaceId()))
											.collect(Collectors.toList());
									//職場情報変更期間を求める
									listDatePeriodWorkplace =  wkplaceInfoChangePeriod.getWkplaceInfoChangePeriod(empLeader, datePeriod, workplaceItems, true);
								}else {
									listDatePeriodWorkplace.add(datePeriod);
								}
							}
							boolean check =  false;
							if(listDatePeriodWorkplace.size() == 1 && listDatePeriodWorkplace.get(0).equals(datePeriod)) {
								listDatePeriodAll.addAll(listDatePeriodWorkplace);
								check = true;
							}
							//INPUT．「勤務種別変更時に再作成」をチェックする
							if(procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson() && !check ) {
								//<<Public>> 社員ID(List)、期間で期間分の勤務種別情報を取得する
								List<BusinessTypeOfEmpDto> listBusinessTypeOfEmpDto = businessTypeOfEmpHisAdaptor.findByCidSidBaseDate(companyId, Arrays.asList(empLeader), datePeriod);
								//勤務種別情報変更期間を求める
								listDatePeriodWorktype = wkTypeInfoChangePeriod.getWkTypeInfoChangePeriod(empLeader, datePeriod, listBusinessTypeOfEmpDto, true);
							}
							listDatePeriodAll.addAll(createListAllPeriod(listDatePeriodWorkplace,listDatePeriodWorktype));
							for(DatePeriod p : listDatePeriodAll) {
								log.info("更新処理自動実行_日別実績の作成_START_"+procExec.getExecItemCd()+"_"+GeneralDateTime.now());
								isHasInterrupt = this.RedoDailyPerformanceProcessing(context, companyId, empLeader,
									p,
									empCalAndSumExeLog.getEmpCalAndSumExecLogID(), dailyCreateLog, procExec);
							}
							if (isHasInterrupt) {
								break;
							}
						}
					}

				}

				// throw new CreateDailyException();
			}
			// } catch (CreateDailyException ex) {
			// isHasCreateDailyException=true;
			// }catch (DailyCalculateException ex) {
			// isHasDailyCalculateException=true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isHasCreateDailyException = true;
			isHasDailyCalculateException = true;
			
			this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
					ExecutionContent.DAILY_CREATION, GeneralDate.today(),
					new ErrMessageContent(TextResource.localize("Msg_1552"))));
			
			this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
					ExecutionContent.DAILY_CALCULATION, GeneralDate.today(),
					new ErrMessageContent(TextResource.localize("Msg_1552"))));
			checkError1552 =true;
		}
		log.info("更新処理自動実行_日別実績の作成・計算_END_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now());
		// exceptionがあるか確認する（日別作成）
		// ドメインモデル「エラーメッセージ情報」を取得する
		List<ErrMessageInfo> listErrDailyCreation = errMessageInfoRepository.getAllErrMessageInfoByID(execId,
				ExecutionContent.DAILY_CREATION.value);
		if (!listErrDailyCreation.isEmpty()) {
			isHasCreateDailyException = true;
		}
		if (isHasCreateDailyException) {
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.ABNORMAL_END);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();

			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// エラーの有無 ＝ エラーあり
			param.setExistenceError(1);
			// 実行内容 ＝ 日別実績の作成
			param.setExecutionContent(AlarmCategoryFn.CREATE_DAILY_REPORT);
			if (listErrDailyCreation.isEmpty()) {
				if(!checkError1552) {
					// アルゴリズム「エラーログ書き込み」を実行する
					this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
							ExecutionContent.DAILY_CREATION, GeneralDate.today(),
							new ErrMessageContent(TextResource.localize("Msg_1339"))));
					for (String managementId : listManagementId) {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
					}
				}else {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1552"), "System"));
				}
			} else {
				for (ErrMessageInfo errMessageInfo : listErrDailyCreation) {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(errMessageInfo.getMessageError().v(),
							errMessageInfo.getEmployeeID()));
				}
			}
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
				executionLogAdapterFn.updateExecuteLog(param);
		} else {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.SUCCESS);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// 実行内容 ＝ 日別実績の作成
			param.setExecutionContent(AlarmCategoryFn.CREATE_DAILY_REPORT);
			// エラーの有無 ＝ エラーなし
			param.setExistenceError(0);
			// 実行ログエラー詳細 ＝ NULL
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);
		}
		// ドメインモデル「エラーメッセージ情報」を取得する
		List<ErrMessageInfo> listErrDailyCalculation = errMessageInfoRepository.getAllErrMessageInfoByID(execId,
				ExecutionContent.DAILY_CALCULATION.value);
		if (!listErrDailyCalculation.isEmpty()) {
			isHasDailyCalculateException = true;
		}
		if (isHasDailyCalculateException) {
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.ABNORMAL_END);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// エラーの有無 ＝ エラーあり
			param.setExistenceError(1);
			// 実行内容 ＝ スケジュール作成
			param.setExecutionContent(AlarmCategoryFn.CALCULATION_DAILY_REPORT);
			if (listErrDailyCalculation.isEmpty()) {
				if(!checkError1552) {
					this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
							ExecutionContent.DAILY_CALCULATION, GeneralDate.today(),
							new ErrMessageContent(TextResource.localize("Msg_1339"))));
					for (String managementId : listManagementId) {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
					}
				}else {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1552"), "System"));
				}
			} else {
				for (ErrMessageInfo errMessageInfo : listErrDailyCalculation) {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(errMessageInfo.getMessageError().v(),
							errMessageInfo.getEmployeeID()));
				}
			}
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);

		} else {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.SUCCESS);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// 実行内容 ＝ 日別実績の計算
			param.setExecutionContent(AlarmCategoryFn.CALCULATION_DAILY_REPORT);
			// エラーの有無 ＝ エラーなし
			param.setExistenceError(0);
			// 実行ログエラー詳細 ＝ NULL
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);

		}
		this.procExecLogRepo.update(procExecLog);

		return true;
	}
	
	private List<DatePeriod> createListAllPeriod(List<DatePeriod> list1,List<DatePeriod> list2){
		List<DatePeriod> listResult = new ArrayList<>();
		List<DatePeriod> listAll = new ArrayList<>();
		listAll.addAll(list1);
		listAll.addAll(list2);
		listAll.sort((x, y) -> x.start().compareTo(y.start()));
		
		for(int i = 0;i< listAll.size();i++) {
			DatePeriod merged = new DatePeriod(listAll.get(i).start(),listAll.get(i).end());
			for (int j = i + 1; j < listAll.size(); j++) {
				DatePeriod next = listAll.get(j);
				if (merged.contains(next.start()) && merged.contains(next.end())){
					i++;
				}else if(merged.contains(next.start())||merged.end().addDays(1).equals(next.start())) {
					merged = merged.cutOffWithNewEnd(next.end());
					i++;
				}else {
					break;
				}
			}
			listResult.add(merged);
		}
		return listResult;
	}

	// 異動者・勤務種別変更者リスト作成処理
//	private ListLeaderOrNotEmpOutput createProcessForChangePerOrWorktype(String companyId, List<String> empIds,
//			GeneralDate startDate, ProcessExecution procExec) {
//		// 期間を計算
//		// GeneralDate p = this.calculatePeriod(closureId, period, companyId);
//		List<String> newEmpIdList = new ArrayList<>();
//		// ・社員ID（異動者、勤務種別変更者のみ）（List）
//		Set<String> setEmpIds = new HashSet<String>();
//		// ・社員ID（異動者、勤務種別変更者のみ）（List）
//		List<String> noLeaderEmpIdList = empIds;
//		// check 異動者を再作成する
//		if (procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTransfer()) {
//			// 異動者の絞り込み todo request list 189
//			List<AffWorkplaceHistoryImport> list = workplaceWorkRecordAdapter.getWorkplaceBySidsAndBaseDate(empIds,
//					startDate);
//			list.forEach(emp -> {
//				emp.getHistoryItems().forEach(x -> {
//					if (x.start().afterOrEquals(startDate)) {
//						setEmpIds.add(emp.getSid());
//						return;
//					}
//				});
//			});
//		}
//		if (procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson()) {
//			// 勤務種別の絞り込み
//			newEmpIdList = this.refineWorkType(companyId, empIds, startDate);
//		}
//		setEmpIds.addAll(newEmpIdList);
//		noLeaderEmpIdList.removeAll(new ArrayList<>(newEmpIdList));
//		return new ListLeaderOrNotEmpOutput(new ArrayList<>(setEmpIds), noLeaderEmpIdList);
//	}

	private DatePeriod findClosureMinMaxPeriod(String companyId, List<Closure> closureList) {
		GeneralDate startYearMonth = null;
		GeneralDate endYearMonth = null;
		for (Closure closure : closureList) {
			DatePeriod datePeriod = this.closureService.getClosurePeriod(closure.getClosureId().value,
					closure.getClosureMonth().getProcessingYm());

			if (startYearMonth == null || datePeriod.start().before(startYearMonth)) {
				startYearMonth = datePeriod.start();
			}

			if (endYearMonth == null || datePeriod.end().after(endYearMonth)) {
				endYearMonth = datePeriod.end();
			}
		}

		return new DatePeriod(startYearMonth, endYearMonth);
	}

	private void updateEachTaskStatus(ProcessExecutionLog procExecLog, ProcessExecutionTask execTask,
			EndStatus status) {
		procExecLog.getTaskLogList().forEach(task -> {
			if (execTask.value == task.getProcExecTask().value) {
				task.setStatus(Optional.ofNullable(status));
			}
		});
	}

	private boolean isAbnormalTermEachTask(ProcessExecutionLog procExecLog) {
		for (ExecutionTaskLog task : procExecLog.getTaskLogList()) {
			if (task.getStatus() != null && task.getStatus().isPresent()
					&& EndStatus.ABNORMAL_END.value == task.getStatus().get().value) {
				return true;
			}
		}
		return false;
	}

	private void initAllTaskStatus(ProcessExecutionLog procExecLog, EndStatus status) {
		if (CollectionUtil.isEmpty(procExecLog.getTaskLogList())) {
			procExecLog.initTaskLogList();
		}
		procExecLog.getTaskLogList().forEach(task -> {
			task.setStatus(Optional.ofNullable(status));
		});
	}

	/**
	 * 個人スケジュール作成期間を計算する
	 * 
	 * @param procExec
	 * @return 期間
	 */
	private DatePeriod calculateSchedulePeriod(ProcessExecution procExec, ProcessExecutionLog procExecLog,
			boolean checkCreateEmployee) {

		GeneralDate today = GeneralDate.today();
		int targetMonth = procExec.getExecSetting().getPerSchedule().getPeriod().getTargetMonth().value;
		int targetDate = procExec.getExecSetting().getPerSchedule().getPeriod().getTargetDate().v().intValue();
		int startMonth = today.month();
		GeneralDate startDate;
		switch (targetMonth) {
		case 0:
			startMonth = today.month();
			startDate = GeneralDate.ymd(today.year(), startMonth, targetDate);
			break;
		case 1:
			startMonth = today.month();
			startDate = GeneralDate.ymd(today.year(), startMonth, targetDate).addMonths(1);
			break;
		case 2:
			startMonth = today.month();
			startDate = GeneralDate.ymd(today.year(), startMonth, targetDate).addMonths(2);
			break;
		// 開始月を指定する の場合
		case 3:
			PersonalScheduleCreationPeriod creationPeriod = procExec.getExecSetting().getPerSchedule().getPeriod();
			// 個人スケジュール作成期間の年を計算する
			int year = GeneralDate.today().year();
			if (creationPeriod.getDesignatedYear().get() == CreateScheduleYear.FOLLOWING_YEAR) {
				year = year + 1;
			}
			// 個人スケジュール作成期間の月日を計算する
			startDate = GeneralDate.ymd(year, creationPeriod.getStartMonthDay().get().getMonth(),
					creationPeriod.getStartMonthDay().get().getDay());
			break;
		default:
			startDate = GeneralDate.ymd(today.year(), startMonth, targetDate);
			break;
		}
		int createPeriod = procExec.getExecSetting().getPerSchedule().getPeriod().getCreationPeriod().v().intValue();
		GeneralDate endDate;
		if (targetMonth == TargetMonth.DESIGNATE_START_MONTH.value) {
			PersonalScheduleCreationPeriod creationPeriod = procExec.getExecSetting().getPerSchedule().getPeriod();
			// 個人スケジュール作成期間の年を計算する
			int year = GeneralDate.today().year();
			if (creationPeriod.getDesignatedYear().get() == CreateScheduleYear.FOLLOWING_YEAR) {
				year = year + 1;
			}
			// 個人スケジュール作成期間の月日を計算する
			endDate = GeneralDate.ymd(year, creationPeriod.getEndMonthDay().get().getMonth(),
					creationPeriod.getEndMonthDay().get().getDay());
			if (endDate.before(startDate)) {
				endDate = endDate.addYears(1);
			}
		} else {
			if (targetDate == 1) {
				GeneralDate date = GeneralDate.ymd(startDate.year(), startDate.month(), 1).addMonths(createPeriod - 1);
				int dateMax = date.lastDateInMonth();
				endDate = GeneralDate.ymd(date.year(), date.month(), dateMax);
			} else {
				GeneralDate dateTest = GeneralDate.ymd(startDate.year(), startDate.month(), 1).addMonths(createPeriod);
				int maxdate = dateTest.lastDateInMonth();
				if (maxdate < (targetDate - 1)) {
					targetDate = maxdate;
				}
				endDate = GeneralDate.ymd(startDate.year(), startDate.month(), targetDate).addMonths(createPeriod)
						.addDays(-1);
			}
		}
		// パラメータ「新入社員作成区分」を判断 : true
		if (checkCreateEmployee) {
			// 全締めから一番早い期間.開始日を取得する
			// 開始日を取得した開始日に置き換える
			startDate = getMinPeriodFromStartDate(AppContexts.user().companyId()).start();
		}

		if (procExecLog.getEachProcPeriod() == null || procExecLog.getEachProcPeriod().isPresent()) {
			procExecLog.setEachProcPeriod(new EachProcessPeriod(new DatePeriod(startDate, endDate), null, null, null));
		} else {
			procExecLog.getEachProcPeriod().get().setScheduleCreationPeriod(new DatePeriod(startDate, endDate));
		}
		// ドメインモデル「更新処理自動実行ログ」を更新する
		// this.procExecLogRepo.update(procExecLog);
		return new DatePeriod(startDate, endDate);
	}

	// 期間を求める
	private DailyCreatAndCalOutput calculateDailyPeriod(ProcessExecution procExec, int closureId,
			CurrentMonth currentMonth) {
		DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, currentMonth.getProcessingYm());

		// ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.作成・計算項目」を元に日別作成の期間を作成する
		GeneralDate crtStartDate = null;
		GeneralDate crtEndDate = null;
		// ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.作成・計算項目」を元に日別計算の期間を作成する
		GeneralDate calStartDate = null;
		GeneralDate calEndDate = null;

		GeneralDate lastExecDate = GeneralDate.today();
		GeneralDate today = GeneralDate.today();
		// ドメインモデル「更新処理前回実行日時」を取得する
		Optional<LastExecDateTime> lastDateTimeOpt = lastExecDateTimeRepo.get(procExec.getCompanyId(),
				procExec.getExecItemCd().v());
		if (lastDateTimeOpt.isPresent()) {
			GeneralDateTime lastExecDateTime = lastDateTimeOpt.get().getLastExecDateTime();
			if (lastExecDateTime != null) {
				lastExecDate = GeneralDate.ymd(lastExecDateTime.year(), lastExecDateTime.month(),
						lastExecDateTime.day());
			}
		}

		switch (procExec.getExecSetting().getDailyPerf().getDailyPerfItem()) {
		case FIRST_OPT:
			crtStartDate = lastExecDate;
			crtEndDate = today;
			calStartDate = lastExecDate;
			calEndDate = today;
			if (lastDateTimeOpt.isPresent()) {
				if (lastDateTimeOpt.get().getLastExecDateTime() == null) {
					crtStartDate = GeneralDate.ymd(currentMonth.getProcessingYm().year(),
							currentMonth.getProcessingYm().month(), 1);
					calStartDate = GeneralDate.ymd(currentMonth.getProcessingYm().year(),
							currentMonth.getProcessingYm().month(), 1);
				}
			}
			break;
		case SECOND_OPT:
			crtStartDate = lastExecDate;
			crtEndDate = today;
			calStartDate = closurePeriod.start();
			calEndDate = today;
			break;
		case THIRD_OPT:
			crtStartDate = closurePeriod.start();
			crtEndDate = today;
			calStartDate = closurePeriod.start();
			calEndDate = today;
			break;
		case FOURTH_OPT:
			crtStartDate = closurePeriod.start();
			crtEndDate = closurePeriod.end();
			calStartDate = closurePeriod.start();
			calEndDate = closurePeriod.end();
			break;
		case FIFTH_OPT:
			crtStartDate = closurePeriod.start();
			GeneralDate closurePeriodFifth = closurePeriod.end().addMonths(1);
			int lastDateFifth = closurePeriodFifth.yearMonth().lastDateInMonth();
			crtEndDate = GeneralDate.ymd(closurePeriodFifth.year(), closurePeriodFifth.month(), lastDateFifth);
			calStartDate = closurePeriod.start();
			calEndDate = GeneralDate.ymd(closurePeriodFifth.year(), closurePeriodFifth.month(), lastDateFifth);
			break;
		case SIXTH_OPT:
			GeneralDate closurePeriodSixth = closurePeriod.end().addMonths(1);
			int lastDateSixth = closurePeriodSixth.yearMonth().lastDateInMonth();
			crtStartDate = closurePeriod.start().addMonths(1);
			crtEndDate = GeneralDate.ymd(closurePeriodSixth.year(), closurePeriodSixth.month(), lastDateSixth);
			calStartDate = closurePeriod.start().addMonths(1);
			calEndDate = GeneralDate.ymd(closurePeriodSixth.year(), closurePeriodSixth.month(), lastDateSixth);
			break;
		case SEVENTH_OPT:
			GeneralDate todayNow = GeneralDate.today();
			GeneralDate startDate = GeneralDate.today();
			GeneralDate endDate = GeneralDate.today();
			// monthtly 12
			if (todayNow.month() == 12 && todayNow.day() > 28) {
				if (todayNow.day() == 30 || todayNow.day() == 31)
					return null;
				if (todayNow.day() == 29) {
					if (todayNow.addMonths(2).lastDateInMonth() == 29) {
						startDate = todayNow.addMonths(2);
						endDate = todayNow.addMonths(2);
					} else {
						return null;
					}
				}
				// monthly 2
			} else if (todayNow.month() == 2 && todayNow.day() == todayNow.lastDateInMonth()) {
				startDate = todayNow.addMonths(2);
				endDate = todayNow.addMonths(2);
				endDate = endDate.addDays(todayNow.lastDateInMonth() - todayNow.day());
				// end monthly = end monthly + 2 monthly
			} else if (todayNow.day() == todayNow.lastDateInMonth()
					&& todayNow.addMonths(2).day() == todayNow.addMonths(2).lastDateInMonth()) {
				startDate = todayNow.addMonths(2);
				endDate = todayNow.addMonths(2);
			} else {
				startDate = todayNow.addMonths(2);
				endDate = todayNow.addMonths(2).addDays(1);

			}
			crtStartDate = startDate;
			crtEndDate = endDate;
			calStartDate = startDate;
			calEndDate = endDate;
			break;
		}
		DailyCreatAndCalOutput dailyCreatAndCalOutput = new DailyCreatAndCalOutput();
		dailyCreatAndCalOutput.setDailyCreationPeriod(new DatePeriod(crtStartDate, crtEndDate));
		dailyCreatAndCalOutput.setDailyCalcPeriod(new DatePeriod(calStartDate, calEndDate));
		return dailyCreatAndCalOutput;
	}

	/**
	 * 対象社員を絞り込み -> Đổi tên (異動者・勤務種別変更者リスト作成処理（スケジュール用）)
	 */
	// private DatePeriod filterEmployeeList(ProcessExecution procExec, List<String>
	// employeeIdList,
	// List<String> reEmployeeList, List<String> newEmployeeList, List<String>
	// temporaryEmployeeList) {
	//
	// String companyId = AppContexts.user().companyId();
	// /** 作成対象の判定 */
	// if (procExec.getExecSetting().getPerSchedule().getTarget()
	// .getCreationTarget().value == TargetClassification.ALL.value) {
	// return null;
	// } else {
	// Set<String> listSetReEmployeeList = new HashSet<>();
	// // ドメインモデル「就業締め日」を取得する
	// List<Closure> closureList = this.closureRepo.findAllActive(companyId,
	// UseClassification.UseClass_Use);
	// DatePeriod closurePeriod = this.findClosureMinMaxPeriod(companyId,
	// closureList);
	//
	// DatePeriod newClosurePeriod = new DatePeriod(closurePeriod.start(),
	// GeneralDate.ymd(9999, 12, 31));
	//
	// TargetSetting setting =
	// procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting();
	// // 異動者を再作成するか判定
	// if (setting.isRecreateTransfer()) {
	// // Imported(勤務実績)「所属職場履歴」を取得する : 異動者の絞り込み
	//// List<WorkPlaceHistImport> wkpImportList =
	// this.workplaceAdapter.getWplByListSidAndPeriod(employeeIdList,
	//// newClosurePeriod);
	////
	// List<AffWorkplaceHistoryImport> list = workplaceWorkRecordAdapter
	// .getWorkplaceBySidsAndBaseDate(employeeIdList, closurePeriod.start());
	// list.forEach(emp -> {
	// emp.getHistoryItems().forEach(x -> {
	// if (x.start().afterOrEquals(closurePeriod.start())) {
	// listSetReEmployeeList.add(emp.getSid());
	// return;
	// }
	// });
	// });
	// }
	//
	// // 勤務種別変更者を再作成するか判定
	// if (setting.isRecreateWorkType()) {
	// employeeIdList.forEach(x -> {
	// // ドメインモデル「社員の勤務種別の履歴」を取得する
	// Optional<BusinessTypeOfEmployeeHistory> optional =
	// this.typeEmployeeOfHistoryRepos
	// .findByEmployeeDesc(AppContexts.user().companyId(), x);
	// if (optional.isPresent()) {
	// for (DateHistoryItem history : optional.get().getHistory()) {
	// // 「全締めの期間.開始日年月日」以降に「社員の勤務種別の履歴.履歴.期間.開始日」が存在する
	// if (history.start().afterOrEquals(closurePeriod.start())) {
	// // 取得したImported（勤務実績）「所属職場履歴」.社員IDを異動者とする
	// listSetReEmployeeList.add(optional.get().getEmployeeId());
	// break;
	// }
	// }
	// }
	// });
	// }
	// // 休職者・休業者を再作成するか判定
	// // TODO:chua lam
	// // 新入社員を作成するか判定
	// if (setting.isCreateEmployee()) {
	// // request list 211
	// // Imported「所属会社履歴（社員別）」を取得する
	// List<nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport>
	// employeeHistList = this.syCompanyRecordAdapter
	// .getAffCompanyHistByEmployee(employeeIdList, newClosurePeriod);
	// // 取得したドメインモデル「所属開始履歴（社員別）」.社員IDを新入社員とする
	// employeeHistList.forEach(x -> newEmployeeList.add(x.getEmployeeId()));
	//
	// // 社員ID（新入社員以外）（List）
	// for (String empID : employeeIdList) {
	// boolean checkNotExist = true;
	// for (String newEmpID : newEmployeeList) {
	// if (empID.equals(newEmpID)) {
	// checkNotExist = false;
	// break;
	// }
	// }
	// if (checkNotExist) {
	// temporaryEmployeeList.add(empID);
	// }
	// }
	//
	// }
	// // 社員ID（異動者、勤務種別変更者、休職者・休業者）（List）から重複している社員IDを1つになるよう削除する
	// List<String> temp = new ArrayList<String>(listSetReEmployeeList);
	// reEmployeeList.addAll(temp);
	// return closurePeriod;
	// }
	//
	// }

	// 承認結果反映
	private boolean reflectApprovalResult(String execId, ProcessExecution processExecution,
			ProcessExecutionLog ProcessExecutionLog, String companyId) {
		// ドメインモデル「更新処理自動実行ログ」を更新する
		List<ExecutionTaskLog> taskLogLists = ProcessExecutionLog.getTaskLogList();
		int size = taskLogLists.size();
		boolean existExecutionTaskLog = false;
		boolean checkError1552 = false;
		for (int i = 0; i < size; i++) {
			ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
			// 承認結果反映
			if (executionTaskLog.getProcExecTask().value == 3) {
				executionTaskLog.setStatus(null);
				existExecutionTaskLog = true;
			}
		}
		if (!existExecutionTaskLog) {
			taskLogLists.add(new ExecutionTaskLog(ProcessExecutionTask.RFL_APR_RESULT, null));
		}
		this.procExecLogRepo.update(ProcessExecutionLog);

		boolean reflectResultCls = processExecution.getExecSetting().isReflectResultCls();
		// 承認結果反映の判定
		if (!reflectResultCls) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 承認結果反映
				if (executionTaskLog.getProcExecTask().value == 3) {
					// 未実施
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.NOT_IMPLEMENT));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			return false; // chua confirm
		}
		log.info("更新処理自動実行_承認結果の反映_START_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
		// ドメインモデル「就業締め日」を取得する
		List<Closure> lstClosure = this.closureRepo.findAllUse(companyId);

		// ドメインモデル「実行ログ」を新規登録する
		DatePeriod period = this.findClosurePeriodMinDate(companyId, lstClosure);
		GeneralDateTime now = GeneralDateTime.now();
		ExecutionLog executionLog = new ExecutionLog(execId, ExecutionContent.REFLRCT_APPROVAL_RESULT,
				ErrorPresent.NO_ERROR, new ExecutionTime(now, now), ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(period.start(), period.end()));
		executionLog.setReflectApprovalSetInfo(new SetInforReflAprResult(ExecutionContent.REFLRCT_APPROVAL_RESULT,
				processExecution.getProcessExecType() == ProcessExecType.NORMAL_EXECUTION
						? ExecutionType.NORMAL_EXECUTION
						: ExecutionType.RERUN,
				IdentifierUtil.randomUniqueId(), false));
		this.executionLogRepository.addExecutionLog(executionLog);
		// ドメインモデル「更新処理自動実行ログ」を更新する
		if (ProcessExecutionLog.getEachProcPeriod() != null && ProcessExecutionLog.getEachProcPeriod().isPresent()) {
			EachProcessPeriod eachProcessPeriod = ProcessExecutionLog.getEachProcPeriod().get();
			DatePeriod scheduleCreationPeriod = (eachProcessPeriod.getScheduleCreationPeriod() != null
					&& eachProcessPeriod.getScheduleCreationPeriod().isPresent())
							? eachProcessPeriod.getScheduleCreationPeriod().get()
							: null;
			DatePeriod dailyCreationPeriod = (eachProcessPeriod.getDailyCreationPeriod() != null
					&& eachProcessPeriod.getDailyCreationPeriod().isPresent())
							? eachProcessPeriod.getDailyCreationPeriod().get()
							: null;
			DatePeriod dailyCalcPeriod = (eachProcessPeriod.getDailyCalcPeriod() != null
					&& eachProcessPeriod.getDailyCalcPeriod().isPresent())
							? eachProcessPeriod.getDailyCalcPeriod().get()
							: null;
			ProcessExecutionLog.setEachProcPeriod(
					new EachProcessPeriod(scheduleCreationPeriod, dailyCreationPeriod, dailyCalcPeriod, period));

		} else {
			ProcessExecutionLog.setEachProcPeriod(new EachProcessPeriod(null, null, null, period));
		}

		boolean isHasException = false;
		boolean endStatusIsInterrupt = false;
		// 就業担当者の社員ID（List）を取得する : RQ526
		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
		try {
			int sizeClosure = lstClosure.size();
			for (int i = 0; i < sizeClosure; i++) {
				Closure closure = lstClosure.get(i);
				// 雇用コードを取得する ~ 締めに紐付く雇用コード一覧を取得
				List<ClosureEmployment> employmentList = this.closureEmpRepo.findByClosureId(companyId,
						closure.getClosureId().value);
				// 雇用コードを取得する
				List<String> lstEmploymentCode = new ArrayList<String>();
				employmentList.forEach(x -> {
					lstEmploymentCode.add(x.getEmploymentCD());
				});

				// 指定した年月の期間を算出する
				DatePeriod datePeriodClosure = closureService.getClosurePeriod(closure.getClosureId().value,
						closure.getClosureMonth().getProcessingYm());
				// 取得した「締め期間」から「期間」を計算する
				DatePeriod newDatePeriod = new DatePeriod(datePeriodClosure.start(), GeneralDate.ymd(9999, 12, 31));

				List<ProcessExecutionScopeItem> workplaceIdList = processExecution.getExecScope()
						.getWorkplaceIdList();
				List<String> workplaceIds = new ArrayList<String>();
				workplaceIdList.forEach(x -> {
					workplaceIds.add(x.getWkpId());
				});
				// 更新処理自動実行の実行対象社員リストを取得する
				List<String> listEmp = listEmpAutoExec.getListEmpAutoExec(companyId, newDatePeriod,
							processExecution.getExecScope().getExecScopeCls(), Optional.of(workplaceIds),
							Optional.of(lstEmploymentCode));
				List<String> leaderEmpIdList = new ArrayList<>();
				if (processExecution.getProcessExecType() == ProcessExecType.RE_CREATE) {
					// 異動者・勤務種別変更者リスト作成処理
					ListLeaderOrNotEmp listLeaderOrNotEmp = changePersionList.createProcessForChangePerOrWorktype(
							companyId, new ArrayList<>(listEmp), newDatePeriod.start(), processExecution);
					leaderEmpIdList = listLeaderOrNotEmp.getLeaderEmpIdList();
				}
				List<String> lstRegulationInfoEmployeeNew = new ArrayList<>();
				if(leaderEmpIdList.isEmpty()){
					lstRegulationInfoEmployeeNew = listEmp;
				}else {
					for(String emp :listEmp) {
						for(String empId :leaderEmpIdList) {
							if(emp.equals(empId)) {
								lstRegulationInfoEmployeeNew.add(empId);
								break;
							}
						}
					}
				}
				int sizeEmployee = lstRegulationInfoEmployeeNew.size();
				for (int j = 0; j < sizeEmployee; j++) {
					try {
						String regulationInfoEmployeeAdapterDto = lstRegulationInfoEmployeeNew.get(j);
						ProcessStateReflectImport processStateReflectImport = appReflectManagerAdapter
								.reflectAppOfEmployeeTotal(execId, regulationInfoEmployeeAdapterDto,
										newDatePeriod);
						if (processStateReflectImport == ProcessStateReflectImport.INTERRUPTION) {
							endStatusIsInterrupt = true;
						}
						if (endStatusIsInterrupt) {
							break;
						}
					} catch (Exception e) {
						isHasException = true;
					}
				}
				if (endStatusIsInterrupt) {
					break;
				}
			}
		} catch (Exception e) {
			isHasException = true;
			this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
					ExecutionContent.REFLRCT_APPROVAL_RESULT, GeneralDate.today(),
					new ErrMessageContent(TextResource.localize("Msg_1552"))));
			checkError1552  = true;
		}
		log.info("更新処理自動実行_承認結果の反映_END_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
		if (endStatusIsInterrupt) {
			return true;
			// 終了状態 ＝ 中断
		}
		// ドメインモデル「エラーメッセージ情報」を取得する
		List<ErrMessageInfo> listErrReflrct = errMessageInfoRepository.getAllErrMessageInfoByID(execId,
				ExecutionContent.REFLRCT_APPROVAL_RESULT.value);
		if (!listErrReflrct.isEmpty()) {
			isHasException = true;
		}
		if (isHasException) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 承認結果反映
				if (executionTaskLog.getProcExecTask().value == 3) {
					// 異常終了
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.ABNORMAL_END));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();

			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// エラーの有無 ＝ エラーあり
			param.setExistenceError(1);
			// 実行内容 ＝ 承認結果の反映
			param.setExecutionContent(AlarmCategoryFn.REFLECT_APPROVAL_RESULT);
			if (listErrReflrct.isEmpty()) {
				if(!checkError1552) {
					this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
							ExecutionContent.REFLRCT_APPROVAL_RESULT, GeneralDate.today(),
							new ErrMessageContent(TextResource.localize("Msg_1339"))));
					for (String managementId : listManagementId) {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
					}
				}else {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1552"), "System"));
				}
			} else {
				for (ErrMessageInfo errMessageInfo : listErrReflrct) {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(errMessageInfo.getMessageError().v(),
							errMessageInfo.getEmployeeID()));
				}

			}
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);

		} else {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 承認結果反映
				if (executionTaskLog.getProcExecTask().value == 3) {
					// 正常終了
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.SUCCESS));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// 実行内容 ＝ 承認結果の反映
			param.setExecutionContent(AlarmCategoryFn.REFLECT_APPROVAL_RESULT);
			// エラーの有無 ＝ エラーなし
			param.setExistenceError(0);
			// 実行ログエラー詳細 ＝ NULL
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);

		}

		return false; // 終了状態 !＝ 中断
	}

	// 月別集計
	private boolean monthlyAggregation(String execId, ProcessExecution processExecution,
			ProcessExecutionLog ProcessExecutionLog, String companyId,
			CommandHandlerContext<ExecuteProcessExecutionCommand> context) {
		context.asAsync().getDataSetter().updateData("taskId", context.asAsync().getTaskId());
		// ドメインモデル「更新処理自動実行ログ」を更新する
		List<ExecutionTaskLog> taskLogLists = ProcessExecutionLog.getTaskLogList();
		int size = taskLogLists.size();
		boolean existExecutionTaskLog = false;
		boolean checkError1552 = false;
		for (int i = 0; i < size; i++) {
			ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
			// 月別集計
			if (executionTaskLog.getProcExecTask().value == 4) {
				executionTaskLog.setStatus(null);
				existExecutionTaskLog = true;
			}
		}
		if (!existExecutionTaskLog) {
			taskLogLists.add(new ExecutionTaskLog(ProcessExecutionTask.MONTHLY_AGGR, null));
		}
		String execItemCd = context.getCommand().getExecItemCd();
		List<ExecutionTaskLog> taskLogList = this.execTaskLogRepo.getAllByCidExecCdExecId(companyId, execItemCd,
				execId);
		if (CollectionUtil.isEmpty(taskLogList)) {
			this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, ProcessExecutionLog.getTaskLogList());
		} else {
			this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, ProcessExecutionLog.getTaskLogList());
		}

		this.procExecLogRepo.update(ProcessExecutionLog);
		// 月別集計の判定
		boolean reflectResultCls = processExecution.getExecSetting().isMonthlyAggCls();
		if (!reflectResultCls) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 月別集計
				if (executionTaskLog.getProcExecTask().value == 4) {
					// 未実施
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.NOT_IMPLEMENT));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			return false; // chua confirm
		}
		log.info("更新処理自動実行_月別実績の集計_START_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
		// ドメインモデル「就業締め日」を取得する
		List<Closure> lstClosure = this.closureRepo.findAllUse(companyId);

		// ドメインモデル「実行ログ」を新規登録する
		DatePeriod period = this.findClosureMinMaxPeriod(companyId, lstClosure);
		GeneralDateTime now = GeneralDateTime.now();
		ExecutionLog executionLog = new ExecutionLog(execId, ExecutionContent.MONTHLY_AGGREGATION,
				ErrorPresent.NO_ERROR, new ExecutionTime(now, now), ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(period.start(), period.end()));
		executionLog.setMonlyAggregationSetInfo(new CalExeSettingInfor(ExecutionContent.MONTHLY_AGGREGATION,
				ExecutionType.NORMAL_EXECUTION, IdentifierUtil.randomUniqueId()));
		this.executionLogRepository.addExecutionLog(executionLog);

		boolean isHasException = false;
		// boolean endStatusIsInterrupt = false;
		// 就業担当者の社員ID（List）を取得する : RQ526
		List<Boolean> listCheck = new ArrayList<>();
		List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
		try {
			int sizeClosure = lstClosure.size();
			for (int i = 0; i < sizeClosure; i++) {
				Closure closure = lstClosure.get(i);
				// 雇用コードを取得する ~ 締めに紐付く雇用コード一覧を取得
				List<ClosureEmployment> employmentList = this.closureEmpRepo.findByClosureId(companyId,
						closure.getClosureId().value);
				List<String> lstEmploymentCode = new ArrayList<String>();
				employmentList.forEach(x -> {
					lstEmploymentCode.add(x.getEmploymentCD());
				});
				
				// 指定した年月の期間を算出する
				DatePeriod datePeriodClosure = closureService.getClosurePeriod(closure.getClosureId().value,
						closure.getClosureMonth().getProcessingYm());
				// 取得した「締め期間」から「期間」を計算する
				DatePeriod newDatePeriod = new DatePeriod(datePeriodClosure.start(), GeneralDate.ymd(9999, 12, 31));

				// <<Public>> 就業条件で社員を検索して並び替える
				List<ProcessExecutionScopeItem> workplaceIdList = processExecution.getExecScope()
						.getWorkplaceIdList();
				List<String> workplaceIds = new ArrayList<String>();
				workplaceIdList.forEach(x -> {
					workplaceIds.add(x.getWkpId());
				});
				// 更新処理自動実行の実行対象社員リストを取得する
				List<String> listEmp = listEmpAutoExec.getListEmpAutoExec(companyId, newDatePeriod,
						processExecution.getExecScope().getExecScopeCls(), Optional.of(workplaceIds),
						Optional.of(lstEmploymentCode));

				List<String> leaderEmpIdList = new ArrayList<>();
				if (processExecution.getProcessExecType() == ProcessExecType.RE_CREATE) {
					// 異動者・勤務種別変更者リスト作成処理
					ListLeaderOrNotEmp listLeaderOrNotEmp = changePersionList.createProcessForChangePerOrWorktype(
							companyId, new ArrayList<>(listEmp), newDatePeriod.start(), processExecution);
					leaderEmpIdList = listLeaderOrNotEmp.getLeaderEmpIdList();
				}
				List<String> lstRegulationInfoEmployeeNew = new ArrayList<>();
				if(leaderEmpIdList.isEmpty()){
					lstRegulationInfoEmployeeNew = listEmp;
				}else {
					for(String emp :listEmp) {
						for(String empId :leaderEmpIdList) {
							if(emp.equals(empId)) {
								lstRegulationInfoEmployeeNew.add(empId);
								break;
							}
						}
					}
				}
				try {
					this.managedParallelWithContext.forEach(ControlOption.custom().millisRandomDelay(MAX_DELAY_PARALLEL),
						lstRegulationInfoEmployeeNew, item -> {
							AsyncCommandHandlerContext<ExecuteProcessExecutionCommand> asyContext = (AsyncCommandHandlerContext<ExecuteProcessExecutionCommand>) context;
							ProcessState aggregate = monthlyService.aggregate(asyContext, companyId,
									item,
									GeneralDate.legacyDate(now.date()), execId, ExecutionType.NORMAL_EXECUTION);
							// 中断
							if (aggregate.value == 0) {
								// endStatusIsInterrupt = true;
								listCheck.add(true);
								// break;
								return;
							}
					});
				} catch (Exception e) {
					isHasException = true;
				}
				if (!listCheck.isEmpty()) {
					if (listCheck.get(0)) {
						break;
					}
				}
			}
		} catch (Exception e) {
			isHasException = true;
			this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
					ExecutionContent.MONTHLY_AGGREGATION, GeneralDate.today(),
					new ErrMessageContent(TextResource.localize("Msg_1552"))));
			checkError1552 = true;
		}
		log.info("更新処理自動実行_月別実績の集計_END_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
		if (!listCheck.isEmpty()) {
			if (listCheck.get(0)) {
				return true; // 終了状態 ＝ 中断
			}
		}
		// ドメインモデル「エラーメッセージ情報」を取得する
		List<ErrMessageInfo> listErrMonthlyAggregation = errMessageInfoRepository.getAllErrMessageInfoByID(execId,
				ExecutionContent.MONTHLY_AGGREGATION.value);
		if (!listErrMonthlyAggregation.isEmpty()) {
			isHasException = true;
		}
		if (isHasException) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 月別集計
				if (executionTaskLog.getProcExecTask().value == 4) {
					// 異常終了
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.ABNORMAL_END));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();

			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// エラーの有無 ＝ エラーあり
			param.setExistenceError(1);
			// 実行内容 ＝ 月別実績の集計
			param.setExecutionContent(AlarmCategoryFn.AGGREGATE_RESULT_MONTH);
			if (listErrMonthlyAggregation.isEmpty()) {
				if(!checkError1552) {
					this.errMessageInfoRepository.add(new ErrMessageInfo("System", execId, new ErrMessageResource("18"),
							ExecutionContent.MONTHLY_AGGREGATION, GeneralDate.today(),
							new ErrMessageContent(TextResource.localize("Msg_1339"))));
					for (String managementId : listManagementId) {
						listErrorAndEmpId
								.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
					}
				}else {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1552"), "System"));
				}
			} else {
				for (ErrMessageInfo errMessageInfo : listErrMonthlyAggregation) {
					listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(errMessageInfo.getMessageError().v(),
							errMessageInfo.getEmployeeID()));
				}
			}
			
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);
		} else {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				// 月別集計
				if (executionTaskLog.getProcExecTask().value == 4) {
					// 正常終了
					executionTaskLog.setStatus(Optional.ofNullable(EndStatus.SUCCESS));
				}
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			ExecutionLogImportFn param = new ExecutionLogImportFn();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
			param.setCompanyId(companyId);
			// 管理社員ID ＝
			param.setManagerId(listManagementId);
			// 実行完了日時 ＝ システム日時
			param.setFinishDateTime(GeneralDateTime.now());
			// 実行内容 ＝ 月別実績の集計
			param.setExecutionContent(AlarmCategoryFn.AGGREGATE_RESULT_MONTH);
			// エラーの有無 ＝ エラーなし
			param.setExistenceError(0);
			// 実行ログエラー詳細 ＝ NULL
			param.setTargerEmployee(listErrorAndEmpId);
			// アルゴリズム「実行ログ登録」を実行する 2290 Done
			executionLogAdapterFn.updateExecuteLog(param);

		}

		return false; // 終了状態 !＝ 中断
	}

	@Inject
	private CreateExtraProcessService createExtraProcessService;
	@Inject
	private ExecAlarmListProcessingService execAlarmListProcessingService;

	// アラーム抽出
	private boolean alarmExtraction(String execId, ProcessExecution processExecution,
			ProcessExecutionLog ProcessExecutionLog, String companyId,
			CommandHandlerContext<ExecuteProcessExecutionCommand> context) {

			context.asAsync().getDataSetter().updateData("taskId", context.asAsync().getTaskId());
			// ドメインモデル「更新処理自動実行ログ」を更新する
			List<ExecutionTaskLog> taskLogLists = ProcessExecutionLog.getTaskLogList();
			int size = taskLogLists.size();
			boolean existExecutionTaskLog = false;
			String execItemCd = context.getCommand().getExecItemCd();
			boolean checkException = false;
			OutputExecAlarmListPro outputExecAlarmListPro = new OutputExecAlarmListPro();
			List<ExecutionLogErrorDetailFn> listErrorAndEmpId = new ArrayList<>();
			// 就業担当者の社員ID（List）を取得する : RQ526
			List<String> listManagementId = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
		try {
			for (int i = 0; i < size; i++) {
				ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
				if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
					executionTaskLog.setStatus(null);
					existExecutionTaskLog = true;
				}
			}
			if (!existExecutionTaskLog) {
				taskLogLists.add(new ExecutionTaskLog(ProcessExecutionTask.AL_EXTRACTION, null));
			}
			List<ExecutionTaskLog> taskLogList = this.execTaskLogRepo.getAllByCidExecCdExecId(companyId, execItemCd,
					execId);
			if (CollectionUtil.isEmpty(taskLogList)) {
				this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, ProcessExecutionLog.getTaskLogList());
			} else {
				this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, ProcessExecutionLog.getTaskLogList());
			}
			this.procExecLogRepo.update(ProcessExecutionLog);
			// アラーム抽出区分の判定
			boolean alarmAtr = processExecution.getExecSetting().getAlarmExtraction().isAlarmAtr();
			if (!alarmAtr) {
				// ドメインモデル「更新処理自動実行ログ」を更新する
				for (int i = 0; i < size; i++) {
					ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
					if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
						executionTaskLog.setStatus(Optional.ofNullable(EndStatus.NOT_IMPLEMENT));
					}
				}
				this.procExecLogRepo.update(ProcessExecutionLog);
				return false;
			}
			log.info("更新処理自動実行_アラーム抽出_START_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
			// アルゴリズム「抽出処理状況を作成する」を実行する
			String extraProcessStatusID = createExtraProcessService.createExtraProcess(companyId);
			// 実行 :
			// List<職場コード>
			List<String> workplaceIdList = new ArrayList<>();
			if (processExecution.getExecScope().getExecScopeCls() == ExecutionScopeClassification.COMPANY) {
				workplaceIdList = workplaceAdapter.findListWorkplaceIdByBaseDate(GeneralDate.today());
			} else {
				workplaceIdList = processExecution.getExecScope().getWorkplaceIdList().stream().map(c -> c.getWkpId())
						.collect(Collectors.toList());
			}
			// List<パターンコード>
			List<String> listPatternCode = new ArrayList<>();
			listPatternCode.add(processExecution.getExecSetting().getAlarmExtraction().getAlarmCode().get().v());
			boolean sendMailPerson = false;
			if (processExecution.getExecSetting().getAlarmExtraction().getMailPrincipal().isPresent()) {
				if (processExecution.getExecSetting().getAlarmExtraction().getMailPrincipal().get().booleanValue())
					sendMailPerson = true;
			}
			boolean sendMailAdmin = false;
			if (processExecution.getExecSetting().getAlarmExtraction().getMailAdministrator().isPresent()) {
				if (processExecution.getExecSetting().getAlarmExtraction().getMailAdministrator().get().booleanValue())
					sendMailAdmin = true;
			}
			try {
				// アラームリスト自動実行処理を実行する
				outputExecAlarmListPro = this.execAlarmListProcessingService
						.execAlarmListProcessing(extraProcessStatusID, companyId, workplaceIdList, listPatternCode,
								GeneralDateTime.now(), sendMailPerson, sendMailAdmin,
								!processExecution.getExecSetting().getAlarmExtraction().getAlarmCode().isPresent() ? ""
										: processExecution.getExecSetting().getAlarmExtraction().getAlarmCode().get().v(),
								execId);
				log.info("更新処理自動実行_アラーム抽出_END_" + processExecution.getExecItemCd() + "_" + GeneralDateTime.now());
				if (outputExecAlarmListPro.isCheckStop())
					return true;
			} catch (Exception e) {
				// 各処理の後のログ更新処理
				checkException = true;
			}
		}catch (Exception e) {
			checkException = true;
			for (String managementId : listManagementId) {
				listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1552"), managementId));
			}
		}

		// ドメインモデル「更新処理自動実行ログ」を取得しチェックする（中断されている場合は更新されているため、最新の情報を取得する）
		Optional<ProcessExecutionLog> processExecutionLog = procExecLogRepo.getLogByCIdAndExecCd(companyId, execItemCd,
				execId);
		// if optional
		if (!processExecutionLog.isPresent())
			return false;

		ExecutionLogImportFn param = new ExecutionLogImportFn();
		// 会社ID ＝ パラメータ.更新処理自動実行.会社ID
		param.setCompanyId(companyId);
		// 管理社員ID ＝
		param.setManagerId(listManagementId);
		// 実行完了日時 ＝ システム日時
		param.setFinishDateTime(GeneralDateTime.now());

		// 実行内容 ＝ スケジュール作成
		param.setExecutionContent(AlarmCategoryFn.ALARM_LIST_PERSONAL);
		if (!checkException) {
			// IF :TRUE
			if (outputExecAlarmListPro.isCheckExecAlarmListPro()) {
				// ドメインモデル「更新処理自動実行ログ」を更新する
				for (int i = 0; i < processExecutionLog.get().getTaskLogList().size(); i++) {
					ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
					if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
						executionTaskLog.setStatus(Optional.ofNullable(EndStatus.SUCCESS));
						this.procExecLogRepo.update(ProcessExecutionLog);
					}
				}
				param.setTargerEmployee(Collections.emptyList());
				param.setExistenceError(0);
				// アルゴリズム「実行ログ登録」を実行する 2290
				executionLogAdapterFn.updateExecuteLog(param);
				return false;
			}
		}
		// IF :FALSE
		// ドメインモデル「更新処理自動実行ログ」を更新する
		for (int i = 0; i < processExecutionLog.get().getTaskLogList().size(); i++) {
			ExecutionTaskLog executionTaskLog = taskLogLists.get(i);
			if (executionTaskLog.getProcExecTask().value == ProcessExecutionTask.AL_EXTRACTION.value) {
				executionTaskLog.setStatus(Optional.ofNullable(EndStatus.ABNORMAL_END));
				this.procExecLogRepo.update(ProcessExecutionLog);
			}
		}
		if(listErrorAndEmpId.isEmpty()) {
			for (String managementId : listManagementId) {
				listErrorAndEmpId.add(new ExecutionLogErrorDetailFn(TextResource.localize("Msg_1339"), managementId));
			}
		}
		param.setTargerEmployee(listErrorAndEmpId);
		param.setExistenceError(1);
		// アルゴリズム「実行ログ登録」を実行する 2290
		executionLogAdapterFn.updateExecuteLog(param);
		return false;
	}

	private DatePeriod findClosurePeriodMinDate(String companyId, List<Closure> closureList) {
		YearMonth startYearMonth = null;
		YearMonth endYearMonth = null;
		for (Closure closure : closureList) {
			Optional<ClosureHistory> firstHist = this.closureRepo.findByHistoryBegin(companyId,
					closure.getClosureId().value);
			if (firstHist.isPresent()) {
				if (startYearMonth == null || firstHist.get().getStartYearMonth().lessThan(startYearMonth)) {
					startYearMonth = firstHist.get().getStartYearMonth();
				}
			}
			Optional<ClosureHistory> lastHist = this.closureRepo.findByHistoryLast(companyId,
					closure.getClosureId().value);
			if (lastHist.isPresent()) {
				if (endYearMonth == null || lastHist.get().getEndYearMonth().greaterThan(endYearMonth)) {
					endYearMonth = lastHist.get().getEndYearMonth();
				}
			}
		}
		GeneralDate startClosingDate = GeneralDate.ymd(startYearMonth.year(), startYearMonth.month(), 1);
		GeneralDate endClosingDate = GeneralDate.ymd(9999, 12, 31);
		return new DatePeriod(startClosingDate, endClosingDate);
	}

	// 実行前登録処理
	private ProcessExecutionLog preExecutionRegistrationProcessing(String companyId, String execItemCd, String execId,
			ProcessExecutionLogManage processExecutionLogManage, int execType) {
		Optional<ProcessExecutionLog> procExecLogOpt = this.procExecLogRepo.getLog(companyId, execItemCd);
		ProcessExecutionLog procExecLog = null;
		if (procExecLogOpt.isPresent()) {
			procExecLog = procExecLogOpt.get();
			// アルゴリズム「更新処理自動実行ログ新規登録処理」を実行する
			// 現在の実行状態 ＝ 実行
			processExecutionLogManage.setCurrentStatus(CurrentExecutionStatus.RUNNING);
			// 全体の終了状態 ＝ NULL
			processExecutionLogManage.setOverallStatus(null);
			if (execType == 1) {
				processExecutionLogManage.setLastExecDateTime(GeneralDateTime.now());
			} else {
				processExecutionLogManage.setLastExecDateTime(GeneralDateTime.now());
				processExecutionLogManage.setLastExecDateTimeEx(GeneralDateTime.now());
			}
			this.processExecLogManaRepo.update(processExecutionLogManage);
			// ドメインモデル「更新処理自動実行ログ」を削除する
			this.procExecLogRepo.remove(companyId, execItemCd, procExecLogOpt.get().getExecId());

			// [更新処理：スケジュールの作成、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.FORCE_END);
			// [更新処理：日別作成、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
			// [更新処理：日別計算、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
			// [更新処理：承認結果反映、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.RFL_APR_RESULT, EndStatus.NOT_IMPLEMENT);
			// [更新処理：月別集計、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.MONTHLY_AGGR, EndStatus.NOT_IMPLEMENT);

			// [更新処理：アラーム抽出、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.AL_EXTRACTION, EndStatus.NOT_IMPLEMENT);
			// [更新処理：承認ルート更新（日次、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_DAI, EndStatus.NOT_IMPLEMENT);
			// [更新処理：承認ルート更新（月次）、終了状態 ＝ 未実施]
			this.updateEachTaskStatus(procExecLog, ProcessExecutionTask.APP_ROUTE_U_MON, EndStatus.NOT_IMPLEMENT);

			procExecLog.setExecId(execId);

			this.procExecLogRepo.insert(procExecLog);

		} else {
			// アルゴリズム「更新処理自動実行ログ新規登録処理」を実行する
			// 現在の実行状態 ＝ 実行
			processExecutionLogManage.setCurrentStatus(CurrentExecutionStatus.RUNNING);
			// 全体の終了状態 ＝ NULL
			processExecutionLogManage.setOverallStatus(null);
			if (execType == 1) {
				processExecutionLogManage.setLastExecDateTime(GeneralDateTime.now());
			} else {
				processExecutionLogManage.setLastExecDateTime(GeneralDateTime.now());
				processExecutionLogManage.setLastExecDateTimeEx(GeneralDateTime.now());
			}
			this.processExecLogManaRepo.update(processExecutionLogManage);
			List<ExecutionTaskLog> taskLogList = new ArrayList<ExecutionTaskLog>();
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.SCH_CREATION,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.DAILY_CREATION,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.DAILY_CALCULATION,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.RFL_APR_RESULT,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.MONTHLY_AGGR,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.AL_EXTRACTION,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.APP_ROUTE_U_DAI,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.APP_ROUTE_U_MON,
					Optional.ofNullable(EndStatus.NOT_IMPLEMENT)));
			procExecLog = new ProcessExecutionLog(new ExecutionCode(execItemCd), companyId, null, taskLogList, execId);
			this.procExecLogRepo.insert(procExecLog);
		}
		return procExecLog;

	}

	// true is interrupt
	// 日別実績の作成 ~ 日別実績の計算
	private boolean dailyPerformanceCreation(String companyId,
			CommandHandlerContext<ExecuteProcessExecutionCommand> context, ProcessExecution processExecution,
			EmpCalAndSumExeLog empCalAndSumExeLog, List<String> lstEmpId, DatePeriod period, List<String> workPlaceIds,
			String typeExecution, ExecutionLog dailyCreateLog) throws CreateDailyException, DailyCalculateException {
		boolean isInterrupt = false;
		List<Boolean> listIsInterrupt = Collections.synchronizedList(new ArrayList<>());
		// int size = lstEmpId.size();
		try {
			this.managedParallelWithContext.forEach(ControlOption.custom().millisRandomDelay(MAX_DELAY_PARALLEL), lstEmpId, empId -> {
				// アルゴリズム「開始日を入社日にする」を実行する
				DatePeriod employeeDatePeriod = this.makeStartDateForHiringDate(processExecution, empId,
						period);

				if (employeeDatePeriod == null && processExecution.getExecSetting().getDailyPerf()
						.getTargetGroupClassification().isMidJoinEmployee()) {

				} else {
					if (employeeDatePeriod != null) {
						boolean executionDaily = this.executionDaily(companyId, context, processExecution,
								empId, empCalAndSumExeLog, employeeDatePeriod, typeExecution, dailyCreateLog);
						if (executionDaily) {
							listIsInterrupt.add(true);
							return;
						}
					}
				}
			});
		} catch (Exception e) {
			val analyzer = new ThrowableAnalyzer(e);
			if(analyzer.findByClass(CreateDailyException.class).isPresent()){
				throw new CreateDailyException(e);
			} else if (analyzer.findByClass(DailyCalculateException.class).isPresent()) {
				throw new DailyCalculateException(e);
			}
		}

		if (!listIsInterrupt.isEmpty()) {
			isInterrupt = true;
		}
		List<ErrMessageInfo> errMessageInfos = this.errMessageInfoRepository
				.getAllErrMessageInfoByEmpID(empCalAndSumExeLog.getEmpCalAndSumExecLogID());
		List<String> errorMessage = errMessageInfos.stream().map(error -> {
			return error.getMessageError().v();
		}).collect(Collectors.toList());

		if ("日別作成".equals(typeExecution)) {
			if (!errorMessage.isEmpty()) {
				throw new CreateDailyException(null);
			}
		} else {
			if (isInterrupt) {
				throw new DailyCalculateException(null);
			}
		}
		if (isInterrupt) {
			return true;
		}
		return false;

	}

	// 開始日を入社日にする
	private DatePeriod makeStartDateForHiringDate(ProcessExecution processExecution, String employeeId,
			DatePeriod period) {
		List<String> lstEmployeeId = new ArrayList<String>();
		lstEmployeeId.add(employeeId);
		// ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.途中入社は入社日からにする」の判定
		if (processExecution.getExecSetting().getDailyPerf().getTargetGroupClassification().isMidJoinEmployee()) {
			// request list 211
			List<nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport> affCompanyHistByEmployee = this.syCompanyRecordAdapter
					.getAffCompanyHistByEmployee(lstEmployeeId, period);
			if (affCompanyHistByEmployee != null && !affCompanyHistByEmployee.isEmpty()) {
				List<AffComHistItemImport> lstAffComHistItem = affCompanyHistByEmployee.get(0).getLstAffComHistItem();
				if (lstAffComHistItem.isEmpty())
					return null;
				List<AffComHistItemImport> lstAffComHistItemSort = lstAffComHistItem.stream()
						.sorted((x, y) -> x.getDatePeriod().start().compareTo(y.getDatePeriod().start()))
						.collect(Collectors.toList());
				// int size = lstAffComHistItem.size();
				GeneralDate startDate = GeneralDate.ymd(9999, 12, 31);
				if (lstAffComHistItemSort.get(0).getDatePeriod().start().before(period.start())) {
					return period;
				}
				if (lstAffComHistItemSort.get(0).getDatePeriod().start().after(period.end())) {
					return null;
				}
				if (lstAffComHistItemSort.get(0).getDatePeriod().start().afterOrEquals(period.start())
						&& lstAffComHistItemSort.get(0).getDatePeriod().start().beforeOrEquals(period.end())) {
					startDate = lstAffComHistItemSort.get(0).getDatePeriod().start();
				}

				return new DatePeriod(startDate, period.end());
			}
			return null;
		}
		return period;
	}

	// true is interrupt
	private boolean executionDaily(String companyId, CommandHandlerContext<ExecuteProcessExecutionCommand> context,
			ProcessExecution processExecution, String employeeId, EmpCalAndSumExeLog empCalAndSumExeLog,
			DatePeriod period, String typeExecution, ExecutionLog dailyCreateLog) {
		AsyncCommandHandlerContext<ExecuteProcessExecutionCommand> asyContext = (AsyncCommandHandlerContext<ExecuteProcessExecutionCommand>) context;
		ProcessState processState;
		// 受け取った期間が「作成した期間（日別作成）」の場合
		if ("日別作成".equals(typeExecution)) {
			try {
				// ⑤社員の日別実績を作成する
				processState = this.createDailyService.createDailyResultEmployeeWithNoInfoImport(asyContext, employeeId,
						period, empCalAndSumExeLog.getCompanyID(), empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
						Optional.ofNullable(dailyCreateLog), processExecution.getExecSetting().getDailyPerf()
								.getTargetGroupClassification().isRecreateTypeChangePerson() ? true : false,
						false, false, null);
			} catch (Exception e) {
				throw new CreateDailyException(e);
			}
		} else {
			try {
				processState = this.dailyCalculationEmployeeService.calculateForOnePerson(employeeId, period,
						Optional.empty(), empCalAndSumExeLog.getEmpCalAndSumExecLogID());
			} catch (Exception e) {
				throw new DailyCalculateException(e);
			}

		}
		// fixed
		return processState.value == 0 ? true : false;
	}

	// private DatePeriod getMaxDatePeriod(DatePeriod dailyCreation, DatePeriod
	// dailyCalculation) {
	// GeneralDate start;
	// GeneralDate end;
	// if (dailyCreation.start().compareTo(dailyCalculation.start()) <= 0) {
	// start = dailyCreation.start();
	// } else {
	// start = dailyCalculation.start();
	// }
	// if (dailyCreation.end().compareTo(dailyCalculation.end()) >= 0) {
	// end = dailyCreation.end();
	// } else {
	// end = dailyCalculation.end();
	// }
	// return new DatePeriod(start, end);
	// }

	@Inject
	private RecordWorkInfoFunAdapter recordWorkInfoFunAdapter;

	// 再作成処理
	// private boolean
	// recreateProcess(CommandHandlerContext<ExecuteProcessExecutionCommand>
	// context, int closureId,
	// EmpCalAndSumExeLog empCalAndSumExeLog, DatePeriod period, List<String>
	// workPlaceIds, List<String> empIdList,
	// String companyId, ProcessExecutionLog procExecLog, ProcessExecution
	// processExecution,
	// ExecutionLog dailyCreateLog) throws CreateDailyException,
	// DailyCalculateException {
	// // 期間を計算
	// GeneralDate calculateDate = this.calculatePeriod(closureId, period,
	// companyId);
	//
	// //// 勤務種別の絞り込み
	// List<String> newEmpIdList = this.refineWorkType(companyId, empIdList,
	// calculateDate);
	//
	// boolean isHasInterrupt = false;
	// // 日別実績処理の再実行
	// int size = newEmpIdList.size();
	// for (int i = 0; i < size; i++) {
	// String empId = newEmpIdList.get(i);
	// // ドメインモデル「日別実績の勤務情報」を取得する
	// // 「作成した開始日」～「取得した日別実績の勤務情報.年月日」を対象期間とする
	// List<WorkInfoOfDailyPerFnImport> listWorkInfo =
	// recordWorkInfoFunAdapter.findByPeriodOrderByYmd(empId);
	// if (listWorkInfo.isEmpty())
	// continue;
	// GeneralDate maxDate = listWorkInfo.stream().map(u ->
	// u.getYmd()).max(GeneralDate::compareTo).get();
	// isHasInterrupt = this.RedoDailyPerformanceProcessing(context, companyId,
	// empId,
	// new DatePeriod(calculateDate, maxDate),
	// empCalAndSumExeLog.getEmpCalAndSumExecLogID(),
	// dailyCreateLog,processExecution);
	// if (isHasInterrupt) {
	// break;
	// }
	// }
	// return isHasInterrupt;
	// }	

	// 承認結果の反映の実行ログを作成
	// private void createExecLogReflecAppResult(String execId, String companyId,
	// ProcessExecutionLog procExecLog) {
	// List<Closure> findAll = this.closureRepo.findAll(companyId);
	// DatePeriod period = this.findClosurePeriodMinDate(companyId, findAll);
	// // ドメインモデル「実行ログ」を新規登録する
	// // ドメインモデル「実行ログ」を新規登録する
	// ExecutionLog exeLog = new ExecutionLog(execId,
	// ExecutionContent.REFLRCT_APPROVAL_RESULT, ErrorPresent.NO_ERROR,
	// new ExecutionTime(GeneralDateTime.now(), GeneralDateTime.now()),
	// ExecutionStatus.INCOMPLETE,
	// new ObjectPeriod(period.start(), period.end()));
	// exeLog.setReflectApprovalSetInfo(new
	// SetInforReflAprResult(ExecutionContent.REFLRCT_APPROVAL_RESULT,
	// ExecutionType.NORMAL_EXECUTION, IdentifierUtil.randomUniqueId(), true));
	// this.executionLogRepository.addExecutionLog(exeLog);
	// // ドメインモデル「更新処理自動実行ログ」を更新する
	// procExecLog.getEachProcPeriod().get().setReflectApprovalResult(period);
	// this.procExecLogRepo.update(procExecLog);
	// }

	// 期間を計算
	private GeneralDate calculatePeriod(int closureId, DatePeriod period, String companyId) {
		Optional<Closure> closureOpt = this.closureRepo.findById(companyId, closureId);
		if (closureOpt.isPresent()) {
			Closure closure = closureOpt.get();
			YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
			DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
			return closurePeriod.start();
		}
		return period.start();
	}

	// 勤務種別の絞り込み
	private List<String> refineWorkType(String companyId, List<String> empIdList, GeneralDate startDate) {
		List<String> newEmpIdList = new ArrayList<String>();
		for (String empId : empIdList) {
			// ドメインモデル「社員の勤務種別の履歴」を取得する
			Optional<BusinessTypeOfEmployeeHistory> businessTypeOpt = this.typeEmployeeOfHistoryRepos
					.findByEmployeeDesc(AppContexts.user().companyId(), empId);
			if (businessTypeOpt.isPresent()) {
				BusinessTypeOfEmployeeHistory businessTypeOfEmployeeHistory = businessTypeOpt.get();
				List<DateHistoryItem> lstDate = businessTypeOfEmployeeHistory.getHistory();
				int size = lstDate.size();
				for (int i = 0; i < size; i++) {
					DateHistoryItem dateHistoryItem = lstDate.get(i);
					if (dateHistoryItem.start().compareTo(startDate) >= 0) {
						newEmpIdList.add(empId);
						break;
					}
				}
			}
		}
		return newEmpIdList;
	}

	private boolean RedoDailyPerformanceProcessing(CommandHandlerContext<ExecuteProcessExecutionCommand> context,
			String companyId, String empId, DatePeriod period, String empCalAndSumExeLogId, ExecutionLog dailyCreateLog,
			ProcessExecution procExec) throws CreateDailyException, DailyCalculateException {
		AsyncCommandHandlerContext<ExecuteProcessExecutionCommand> asyncContext = (AsyncCommandHandlerContext<ExecuteProcessExecutionCommand>) context;
		ProcessState processState1;
		try {
			// 実行設定.日別実績の作成・計算.対象者区分.勤務種別者を再作成
			boolean reCreateWorkType = procExec.getExecSetting().getDailyPerf().getTargetGroupClassification()
					.isRecreateTypeChangePerson();
			// 実行設定.日別実績の作成・計算.対象者区分.異動者を再作成する
			boolean reCreateWorkPlace = procExec.getExecSetting().getDailyPerf().getTargetGroupClassification()
					.isRecreateTransfer();
			// 実行設定.日別実績の作成・計算.対象者区分.休職者・休業者を再作成

			boolean reCreateRestTime = false; // TODO : chua lam
			// ⑤社員の日別実績を作成する
			processState1 = this.createDailyService.createDailyResultEmployeeWithNoInfoImport(asyncContext, empId,
					period, companyId, empCalAndSumExeLogId, Optional.ofNullable(dailyCreateLog), reCreateWorkType,
					reCreateWorkPlace, reCreateRestTime, null);
		} catch (Exception e) {
			throw new CreateDailyException(e);
		}
		log.info("更新処理自動実行_日別実績の作成_END_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now());
		log.info("更新処理自動実行_日別実績の計算_START_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now());
		ProcessState ProcessState2;
		try {
			// 社員の日別実績を計算
			ProcessState2 = this.dailyCalculationEmployeeService.calculateForOnePerson(empId, period, Optional.empty(),
					empCalAndSumExeLogId);
		log.info("更新処理自動実行_日別実績の計算_END_" + procExec.getExecItemCd() + "_" + GeneralDateTime.now());
		} catch (Exception e) {
			throw new DailyCalculateException(e);
		}

		// 社員の申請を反映 cua chi du
		// AppReflectManager.reflectEmployeeOfApp
		// fixed endStatusIsInterrupt =true (終了状態 ＝ 中断)
		// boolean endStatusIsInterrupt = true;

		// 中断
		if (processState1.value == 0 || ProcessState2.value == 0) {
			return true;
		}
		return false;
	}

	// 全締めから一番早い期間.開始日を取得する
	private DatePeriod getMinPeriodFromStartDate(String companyId) {
		// ドメインモデル「就業締め日」を取得する
		List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
		// 全締めから一番早い期間.開始日を取得する
		return this.findClosureMinMaxPeriod(companyId, closureList);
	}

}

class CreateDailyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateDailyException(Throwable cause) {
		super(cause);
	}
}

class DailyCalculateException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DailyCalculateException(Throwable cause) {
		super(cause);
	}
}
