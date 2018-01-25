package nts.uk.ctx.at.function.app.command.processexecution;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetSetting;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ProcessFlowOfDailyCreationDomainService;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ObjectPeriod;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutedMenu;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommandHandler;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
//import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
//import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ProcessFlowOfDailyCreationDomainService;
//import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
//import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
//import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommandHandler;
//import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
//import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
//import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
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
	
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	
	@Inject
	private ScheduleCreatorExecutionCommandHandler scheduleExecution;
	
	@Inject
	private ProcessFlowOfDailyCreationDomainService processFlowOfDailyCreationDomainService;
	
	@Inject
	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	/**
	 * 更新処理を開始する
	 * 
	 * @param execType 実行タイプ
	 * @param execId 実行ID
	 * @param execItemCd 更新処理自動実行項目コード
	 * @param companyId 会社ID
	 */
	@Override
	protected void handle(CommandHandlerContext<ExecuteProcessExecutionCommand> context) {
		ExecuteProcessExecutionCommand command = context.getCommand();
		String execItemCd = command.getExecItemCd();
		String companyId = command.getCompanyId();
		String execId = command.getExecId();
		int execType = command.getExecType();
		// ドメインモデル「更新処理自動実行」を取得する
		ProcessExecution procExec = null;
		Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, execItemCd);
		if (procExecOpt.isPresent()) {
			procExec = procExecOpt.get();
		}
		
		// ドメインモデル「実行タスク設定」を取得する
		ExecutionTaskSetting execSetting = null;
		Optional<ExecutionTaskSetting> execSettingOpt = this.execSettingRepo.getByCidAndExecCd(companyId, execItemCd);
		if (execSettingOpt.isPresent()) {
			execSetting = execSettingOpt.get();
		}
		
		// ドメインモデル「更新処理自動実行ログ」を取得する
		ProcessExecutionLog procExecLog = null;
		Optional<ProcessExecutionLog> procExecLogOpt = this.procExecLogRepo.getLogByCIdAndExecCd(companyId, execItemCd, execId);
		if (procExecLogOpt.isPresent()) {
			procExecLog = procExecLogOpt.get();
		}

		// ドメインモデル「更新処理前回実行日時」を取得する
		LastExecDateTime lastExecDateTime = null;
		Optional<LastExecDateTime> lastDateTimeOpt =
									lastExecDateTimeRepo.get(procExec.getCompanyId(), procExec.getExecItemCd().v());
		if (lastDateTimeOpt.isPresent()) {
			lastExecDateTime = lastDateTimeOpt.get();
		}
		
		/*
		 *  ドメインモデル「更新処理自動実行ログ」を更新する
		 *  現在の実行状態　＝　実行
		 *  全体の終了状態　＝　NULL
		 */
		procExecLog.setCurrentStatus(CurrentExecutionStatus.RUNNING);
		procExecLog.setOverallStatus(null);
		this.procExecLogRepo.update(procExecLog);
		
		/*
		 *  各処理を実行する
		 *  【パラメータ】
		 *  実行ID　＝　取得した実行ID
		 *  取得したドメインモデル「更新処理自動実行」、「実行タスク設定」、「更新処理自動実行ログ」の情報
		 */
		this.doProcesses(execId, procExec, execSetting, procExecLog, context);
		
		// アルゴリズム「自動実行登録処理」を実行する
		this.updateDomains(execItemCd, execType, companyId, execId, execSetting, procExecLog);
	}

	private void updateDomains(String execItemCd, int execType, String companyId, String execId,
			ExecutionTaskSetting execSetting, ProcessExecutionLog procExecLog) {
		/*
		 * ドメインモデル「更新処理自動実行ログ」を更新する
		 * 
		 * 【更新処理自動実行ログ.各処理の終了状態に「異常終了」がない＆全体の終了状態がNULLの場合】
		 * 実行ID　＝　取得した実行ID
		 * 現在の実行状態　＝　待機
		 * 全体の終了状態　＝　正常終了
		 * 
		 * 【更新処理自動実行ログ.各処理の終了状態に「異常終了」がある場合】
		 * 実行ID　＝　取得した実行ID
		 * 現在の実行状態　＝　待機
		 * 全体の終了状態　＝　異常終了
		 */
		if (!this.isAbnormalTermEachTask(procExecLog) && procExecLog.getOverallStatus() == null) {
			procExecLog.setExecId(execId);
			procExecLog.setCurrentStatus(CurrentExecutionStatus.WAITING);
			procExecLog.setOverallStatus(EndStatus.SUCCESS);
		} else if (this.isAbnormalTermEachTask(procExecLog)) {
			procExecLog.setExecId(execId);
			procExecLog.setCurrentStatus(CurrentExecutionStatus.WAITING);
			procExecLog.setOverallStatus(EndStatus.ABNORMAL_END);
		}
		boolean firstImpl = procExecLog.getOverallStatus().value == EndStatus.NOT_IMPLEMENT.value;
		if (firstImpl) {
			this.execTaskLogRepo.insertAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		} else {
			this.execTaskLogRepo.updateAll(companyId, execItemCd, execId, procExecLog.getTaskLogList());
		}
		this.procExecLogRepo.update(procExecLog);
		
		/*
		 * ドメインモデル「更新処理自動実行ログ履歴」を新規登録する
		 * 
		 * 会社ID　＝　更新処理自動実行ログ.会社ID
		 * 実行ID　＝　取得した実行ID
		 * コード　＝　更新処理自動実行ログ.コード
		 * 前回実行日時　＝　更新処理自動実行ログ.前回実行日時
		 * 各処理の終了状態(List)　＝　更新処理自動実行ログ.各処理の終了状態(List)
		 * 全体の終了状態　＝　更新処理自動実行ログ.全体の終了状態
		 * 全体のエラー詳細　＝　更新処理自動実行ログ.全体のエラー詳細
		 * 各処理の期間　＝　更新処理自動実行ログ.各処理の期間
		 */
		this.procExecLogHistRepo.insert(
					new ProcessExecutionLogHistory(new ExecutionCode(execItemCd),
													companyId,
													procExecLog.getOverallError(),
													procExecLog.getOverallStatus(),
													procExecLog.getLastExecDateTime(),
													procExecLog.getEachProcPeriod(),
													procExecLog.getTaskLogList(),
													execId));
		
		/*
		 * ドメインモデル「更新処理自動実行ログ」を更新する
		 * 
		 * 【実行タイプ　＝　1（即時実行）の場合】
		 * 前回実行日時　＝　システム日時
		 * 前回実行日時（即時実行を含めない）　＝　システム日時
		 * 
		 * 【実行タイプ　＝　0（開始時刻）の場合】
		 * 前回実行日時　＝　システム日時
		 */
		if (execType == 1) {
			procExecLog.setLastExecDateTime(GeneralDateTime.now());
			procExecLog.setLastExecDateTimeEx(GeneralDateTime.now());
		} else if (execType == 0) {
			procExecLog.setLastExecDateTime(GeneralDateTime.now());
		}
		this.procExecLogRepo.update(procExecLog);
		
		/*
		 *  ドメインモデル「実行タスク設定」を更新する
		 *  
		 *  次回実行日時　＝　次回実行日時を作成する。　※補足資料⑤参照
		 */
		execSetting.setNextExecDateTime();
		this.execSettingRepo.update(execSetting);
	}

	/**
	 * 各処理を実行する
	 * @param execId 実行ID
	 * @param procExec 更新処理自動実行
	 * @param execSetting 実行タスク設定
	 * @param procExecLog 更新処理自動実行ログ
	 */
	private void doProcesses(String execId, ProcessExecution procExec, ExecutionTaskSetting execSetting, ProcessExecutionLog procExecLog, CommandHandlerContext<ExecuteProcessExecutionCommand> context) {
		// Initialize status [未実施] for each task 
		initAllTaskStatus(procExecLog, EndStatus.NOT_IMPLEMENT);
		
		/*
		 *  スケジュールの作成
		 *  【パラメータ】
		 *  実行ID
		 *  取得したドメインモデル「更新処理自動実行」、「実行タスク設定」、「更新処理自動実行ログ」の情報
		 */
		this.createSchedule(execId, procExec, execSetting, procExecLog);
//		this.createDailyData(execId, procExec, execSetting, procExecLog, context);
//		if (!this.createSchedule(execId, procExec, execSetting, procExecLog)) {
//			// ドメインモデル「更新処理自動実行ログ」を更新する
////			updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.FORCE_END);
//			return;
//		} else if (!this.createDailyData(execId, procExec, execSetting, procExecLog, context)) {
//			// ドメインモデル「更新処理自動実行ログ」を更新する
////			updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.FORCE_END);
////			updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.FORCE_END);
//			return;
//		}
	}
	
	/**
	 * スケジュールの作成
	 * @param execId 実行ID
	 * @param procExec 更新処理自動実行
	 * @param execSetting 実行タスク設定
	 * @param procExecLog 更新処理自動実行ログ
	 */
	private boolean createSchedule(String execId, ProcessExecution procExec, ExecutionTaskSetting execSetting, ProcessExecutionLog procExecLog) {
		// ドメインモデル「更新処理自動実行ログ」を更新する
		updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, null);
		try {
			// 個人スケジュール作成区分の判定
			if (!procExec.getExecSetting().getPerSchedule().isPerSchedule()) {
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.NOT_IMPLEMENT);
				return true;
			}
			
			// 期間の計算
			DatePeriod period = this.calculateSchedulePeriod(procExec);
			
			/*
			 * 対象社員を取得 TODO
			 */
			
			
			/*
			 *  作成対象の判定
			 */
			// Login user context
			LoginUserContext loginContext = AppContexts.user();
			// 全員の場合
			if (procExec.getExecSetting().getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.ALL.value) {
				// 対象社員を取得 - TODO
				
				ScheduleCreatorExecutionCommand scheduleCommand = new ScheduleCreatorExecutionCommand();
				scheduleCommand.setEmployeeId(loginContext.employeeId());
				scheduleCommand.setExecutionId(execId);
				this.scheduleExecution.handle(scheduleCommand);
				
//				Optional<ScheduleExecutionLog> logOpt =  this.scheduleExecutionLogRepository.findById(loginContext.companyId(), execId);
//				if (logOpt.isPresent() && logOpt.get().getCompletionStatus().value == CompletionStatus.COMPLETION_ERROR.value) {
//					return false;
//				}
				
				// ドメインモデル「更新処理自動実行ログ」を更新する
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.SUCCESS);
			}
			// 異動者・新入社員のみ作成の場合
			else {
				// 対象社員を絞り込み
				this.filterEmployeeList(procExec, loginContext.employeeId(), period);
			}
			
			
		} catch (Exception e) {
			// ドメインモデル「更新処理自動実行ログ」を更新する
			updateEachTaskStatus(procExecLog, ProcessExecutionTask.SCH_CREATION, EndStatus.ABNORMAL_END);
		}
		
//		/*
//		 *  作成対象の判定
//		 */
//		// 全員の場合
//		if (procExec.getExecSetting().getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.ALL.value) {
//			
//		}
//		// 異動者・新入社員のみ作成の場合
//		else {
//			// 対象社員を絞り込み
//			// 対象社員を絞り込む
//			
//		}
		return true;
	}

	/**
	 * 日別作成
	 * @param execId 実行ID
	 * @param procExec 更新処理自動実行
	 * @param execSetting 実行タスク設定
	 * @param procExecLog 更新処理自動実行ログ
	 */
	private boolean createDailyData(String execId, ProcessExecution procExec, ExecutionTaskSetting execSetting, ProcessExecutionLog procExecLog, CommandHandlerContext<ExecuteProcessExecutionCommand> context) {
			ExecuteProcessExecutionCommand command = context.getCommand();
			String companyId = command.getCompanyId();
			// ドメインモデル「更新処理自動実行ログ」を更新する
			updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, null);
			updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, null);
			
			// 日別実績の作成・計算区分の判定
			if (!procExec.getExecSetting().getDailyPerf().isDailyPerfCls()) {
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.NOT_IMPLEMENT);
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.NOT_IMPLEMENT);
				return true;
			}
			
			// ドメインモデル「就業計算と集計実行ログ」を新規登録する
			registerEmpCalAndSumExeLog(execId, procExecLog, command);
			
			// ドメインモデル「就業締め日」を取得する
			List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
			try {
				for (Closure closure : closureList) {
					// 期間の計算
					DatePeriod periodTime = this.calculateDailyPeriod(procExec,
																	closure.getCompanyId().v(),
																	closure.getClosureId().value,
																	closure.getClosureMonth());
					
					// 雇用コードを取得する
					List<ClosureEmployment> employmentList =
										this.closureEmpRepo.findByClosureId(companyId, closure.getClosureId().value);
					
					
					val asyncContext = context.asAsync();
//					val dataSetter = asyncContext.getDataSetter();
//					val command = context.getCommand();
		
//					DatePeriod periodTime = new DatePeriod(
//							GeneralDate.fromString(command.getDailyCreateStart(), "yyyy/MM/dd"),
//							GeneralDate.fromString(command.getDailyCreateEnd(), "yyyy/MM/dd"));
//					processFlowOfDailyCreationDomainService.processFlowOfDailyCreation(asyncContext, ExecutionAttr.MANUAL, periodTime, command.getExecId());
					processFlowOfDailyCreationDomainService.processFlowOfDailyCreation(asyncContext, ExecutionAttr.MANUAL, periodTime, execId);
					
//					dataSetter.setData("complete", true);
					
//					if () {
//						
//					}
					
				}
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.SUCCESS);
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.SUCCESS);
			} catch (Exception e) {
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CREATION, EndStatus.ABNORMAL_END);
				updateEachTaskStatus(procExecLog, ProcessExecutionTask.DAILY_CALCULATION, EndStatus.ABNORMAL_END);
			}
		
//		
//		// ドメインモデル「就業締め日」を取得する
//		List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
//		
//		if (!CollectionUtil.isEmpty(closureList)) {
//			closureList.forEach(closure -> {
//				// 雇用コードを取得する
//				List<ClosureEmployment> employmentList =
//									this.closureEmpRepo.findByClosureId(companyId, closure.getClosureId().value);
//				// 対象社員を取得
////				employmentList.forEach(employment -> {
////					
////				});
//				
//				// 取得した社員IDを1つずつ順番に回す（取得した社員ID　＝　回数）
//			});
//		}
//		
////		// ドメインモデル「更新処理自動実行」を更新する
////		procExec.getExecSetting().getDailyPerf().setLastProcDate(GeneralDate.today());
		
		
		return true;
	}

	private void registerEmpCalAndSumExeLog(String execId, ProcessExecutionLog procExecLog,
			ExecuteProcessExecutionCommand command) {
		// ドメインモデル「就業計算と集計実行ログ」を新規登録する
		EmpCalAndSumExeLog empCalAndSumExeLog = new EmpCalAndSumExeLog(
													execId,
													command.getCompanyId(),
													null,
													ExecutedMenu.SELECT_AND_RUN,
													GeneralDate.today(),
													ExeStateOfCalAndSum.PROCESSING,
													null,
													0,
													null,
													new ArrayList<>());
		// ドメインモデル「実行ログ」を新規登録する
		empCalAndSumExeLog.addExecutionLog(new ExecutionLog(
				execId,
				ExecutionContent.DAILY_CREATION,
				ErrorPresent.NO_ERROR,
				null,
				ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(
						procExecLog.getEachProcPeriod().getDailyCreationPeriod().start(),
						procExecLog.getEachProcPeriod().getDailyCreationPeriod().end()
						)
				));
		empCalAndSumExeLog.addExecutionLog(new ExecutionLog(
				execId,
				ExecutionContent.DAILY_CALCULATION,
				ErrorPresent.NO_ERROR,
				null,
				ExecutionStatus.INCOMPLETE,
				new ObjectPeriod(
						procExecLog.getEachProcPeriod().getDailyCreationPeriod().start(),
						procExecLog.getEachProcPeriod().getDailyCreationPeriod().end()
						)
				));
		this.empCalSumRepo.add(empCalAndSumExeLog);
	}
	
	private void updateEachTaskStatus(ProcessExecutionLog procExecLog, ProcessExecutionTask execTask, EndStatus status) {
		procExecLog.getTaskLogList().forEach(task -> {
			if(execTask.value == task.getProcExecTask().value){
				task.setStatus(status);
			}
		});
	}
	
	private boolean isAbnormalTermEachTask(ProcessExecutionLog procExecLog) {
		for (ExecutionTaskLog task : procExecLog.getTaskLogList()) {
			if (EndStatus.ABNORMAL_END.value == task.getProcExecTask().value) {
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
			task.setStatus(status);
		});
	}
	
	/**
	 * 個人スケジュール作成期間を計算する
	 * @param procExec
	 * @return 期間
	 */
	private DatePeriod calculateSchedulePeriod(ProcessExecution procExec) {
		GeneralDate today = GeneralDate.today();
		// Add month
		GeneralDate startDate = today.addMonths(procExec.getExecSetting().getPerSchedule().getPeriod().getTargetMonth().value);
		
		// Add day
		int targetDate = procExec.getExecSetting().getPerSchedule().getPeriod().getTargetDate().v();
		// ※2　システム日付の日　＞　対象日　であった場合、システム日付の日とする。
		if (today.day() > targetDate) {
			targetDate = today.day();
		}
		try {
			startDate = GeneralDate.ymd(startDate.year(),
										startDate.month(),
										targetDate);
		} catch (DateTimeException dte) {
			// ※1　対象月の末日を超える対象日だった場合、対象月の末日とする。
			startDate = GeneralDate.ymd(startDate.year(),
										startDate.month(),
										startDate.localDate().lengthOfMonth());
		}
		
		// Add month
		GeneralDate endDate = startDate.addMonths(procExec.getExecSetting().getPerSchedule().getPeriod().getCreationPeriod().v());
		try {
			endDate = GeneralDate.ymd(endDate.year(),
									  endDate.month(),
									  procExec.getExecSetting().getPerSchedule().getPeriod().getTargetDate().v() - 1);
		} catch (DateTimeException dte) {
			endDate = GeneralDate.ymd(endDate.year(),
									  endDate.month(),
									  endDate.localDate().lengthOfMonth());
		}
		return new DatePeriod(startDate, endDate);
	}
	
	
	/**
	 * 日別作成.計算の期間を作成する
	 * @param procExec
	 * @return 期間
	 */
	private DatePeriod calculateDailyPeriod(ProcessExecution procExec, String companyId, int closureId, CurrentMonth currentMonth) {
		// ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.作成・計算項目」を元に日別作成の期間を作成する
		// ドメインモデル「更新処理自動実行.実行設定.日別実績の作成・計算.作成・計算項目」を元に日別計算の期間を作成する
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		GeneralDate lastExecDate = GeneralDate.today();
		Optional<LastExecDateTime> lastDateTimeOpt =
				lastExecDateTimeRepo.get(procExec.getCompanyId(), procExec.getExecItemCd().v());
		if (lastDateTimeOpt.isPresent()) {
			GeneralDateTime lastExecDateTime = lastDateTimeOpt.get().getLastExecDateTime();
			lastExecDate = GeneralDate.ymd(lastExecDateTime.year(),
											lastExecDateTime.month(),
											lastExecDateTime.day());
		}
		
		switch (procExec.getExecSetting().getDailyPerf().getDailyPerfItem()) {
        case FIRST_OPT:
        	startDate = lastExecDate;
        	endDate = GeneralDate.today();
            break;
        case SECOND_OPT:
        	startDate = lastExecDate;
            break;
        case THIRD_OPT:
            break;
        case FOURTH_OPT:
            break;
        case FIFTH_OPT:
            break;
        case SIXTH_OPT:
            break;
		}
		return new DatePeriod(startDate, endDate);
	}
	
	/**
	 * 対象社員を絞り込み
	 * @param procExec
	 * @param employeeIdList
	 * @param period
	 */
	private void filterEmployeeList(ProcessExecution procExec, String employeeIdList, DatePeriod period) {
		if (procExec.getExecSetting().getPerSchedule().getTarget().getCreationTarget().value == TargetClassification.ALL.value) {
			return;
		} else {
			String companyId = AppContexts.user().companyId();
			
			// ドメインモデル「就業締め日」を取得する
			List<Closure> closureList = this.closureRepo.findAllActive(companyId, UseClassification.UseClass_Use);
			TargetSetting setting = procExec.getExecSetting().getPerSchedule().getTarget().getTargetSetting();
			// 異動者を再作成するか判定
			if (setting.isRecreateTransfer()) {
				
			}
			// 勤務種別変更者を再作成するか判定
			if (setting.isRecreateWorkType()) {
				
			}
			// 新入社員を作成するか判定
			if (setting.isCreateEmployee()) {
				
			}
		}
	}
}
