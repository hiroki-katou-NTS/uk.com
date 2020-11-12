package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.AlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateDaily;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionName;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionScopeClassification;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecType;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreateScheduleYear;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationTarget;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetDate;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetMonth;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetSetting;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionScopeItemRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The class Save process execution command handler.
 */
@Stateless
public class SaveProcessExecutionCommandHandler extends CommandHandlerWithResult<SaveProcessExecutionCommand, String> {

	/** The Process execution repository */
	@Inject
	private ProcessExecutionRepository procExecRepo;

	/** The Last exec date time repository */
	@Inject
	private LastExecDateTimeRepository lastDateTimeRepo;
	
	/** The Execution scope item repository */
	@Inject
	private ExecutionScopeItemRepository scopeItemRepo;

	/** The Process execution log manage repository */
	@Inject
	private ProcessExecutionLogManageRepository processExecLogManRepo;

	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.登録ボタン押下時処理.登録ボタン押下時処理
	 * @param context the context
	 * @return the <code>String</code>
	 */
	@Override
	protected String handle(CommandHandlerContext<SaveProcessExecutionCommand> context) {
		// ログイン社員の社員ID
		String companyId = AppContexts.user().companyId();

		SaveProcessExecutionCommand command = context.getCommand();
		// 選択している項目の更新処理自動実行項目コード
		String execItemCd = command.getExecItemCd();
		
		List<ProcessExecutionScopeItem> workplaceIdList = command.getWorkplaceList().stream()
				.map(workplaceId -> ProcessExecutionScopeItem.createSimpleFromJavaType(
																		companyId,
																		command.getExecItemCd(),
																		workplaceId)
				).collect(Collectors.toList());
		
		ProcessExecutionScope execScope =
				new ProcessExecutionScope(EnumAdaptor.valueOf(command.getExecScopeCls(), ExecutionScopeClassification.class),
						command.getRefDate(),
						workplaceIdList);
		
		AlarmExtraction alarmExtraction = new AlarmExtraction(command.isAlarmAtr(), new AlarmPatternCode(command.getAlarmCode()),
				command.getMailPrincipal(),
				command.getMailAdministrator(),
				null, //TODO-MINHNB
				null//TODO-MINHNB
				);
		
		PersonalScheduleCreationPeriod period = new PersonalScheduleCreationPeriod(
										command.getCreationPeriod() == null ? null : new CreationPeriod(command.getCreationPeriod()),
										command.getTargetDate() == null ? null : new TargetDate(command.getTargetDate()),
										EnumAdaptor.valueOf(command.getTargetMonth(), TargetMonth.class),
										command.getDesignatedYear() == null ? null : EnumAdaptor.valueOf(command.getDesignatedYear(), CreateScheduleYear.class),
										command.getStartMonthDay() == null ? null : new MonthDay(command.getStartMonthDay()/100, command.getStartMonthDay()%100),
										command.getEndMonthDay() == null ? null : new MonthDay(command.getEndMonthDay()/100, command.getEndMonthDay()%100)
				);
		
		PersonalScheduleCreationTarget target = new PersonalScheduleCreationTarget(
				EnumAdaptor.valueOf(command.getCreationTarget(), TargetClassification.class),
				new TargetSetting(command.isRecreateWorkType(), command.isCreateEmployee(), command.isRecreateTransfer()));
		PersonalScheduleCreation perSchCreation = new PersonalScheduleCreation(period, command.isPerScheduleCls(), target);
		DailyPerformanceCreation dailyPerfCreation =
								new DailyPerformanceCreation(command.isDailyPerfCls(),
										EnumAdaptor.valueOf(command.getDailyPerfItem(), DailyPerformanceItem.class)
										,new TargetGroupClassification( command.isRecreateWorkType(), command.isMidJoinEmployee(),command.isRecreateTransfer()));

		ProcessExecutionSetting execSetting = ProcessExecutionSetting.builder()
				.alarmExtraction(alarmExtraction)
				.perSchedule(perSchCreation)
				.dailyPerf(dailyPerfCreation)
				.reflectResultCls(command.isReflectResultCls())
				.monthlyAggCls(command.isMonthlyAggCls())
				.appRouteUpdateDaily(new AppRouteUpdateDaily(
								EnumAdaptor.valueOf(command.isAppRouteUpdateAtr()?1:0, NotUseAtr.class),
								command.getCreateNewEmp()==null?null:EnumAdaptor.valueOf((command.getCreateNewEmp().booleanValue()?1:0), NotUseAtr.class)
								))
				.appRouteUpdateMonthly(EnumAdaptor.valueOf(command.isAppRouteUpdateMonthly()?1:0, NotUseAtr.class))
//				.externalOutput(externalOutput) //TODO-MINHNB
//				.externalAcceptance(externalAcceptance) //TODO-MINHNB
//				.saveData(saveData) //TODO-MINHNB
//				.deleteData(deleteData) //TODO-MINHNB
//				.aggregationOfArbitraryPeriod(aggregationOfArbitraryPeriod) //TODO-MINHNB
//				.indexReconstruction(indexReconstruction)
				.build();
		ProcessExecution procExec = ProcessExecution.builder()
				.companyId(companyId)
				.execItemCd(new ExecutionCode(execItemCd))
				.execItemName(new ExecutionName(command.getExecItemName()))
				.execScope(execScope)
				.execSetting(execSetting)
				.processExecType(EnumAdaptor.valueOf(command.getProcessExecType(), ProcessExecType.class))
				.cloudCreationFlag(command.getCloudCreationFlag())
				.build();
		procExec.validateVer2();
		if (command.isNewMode()) {
			// 新規モード(new mode)
			//新規登録処理
			Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, execItemCd);
			if (procExecOpt.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			// ドメインモデル「更新処理自動実行」に新規登録する
			this.procExecRepo.insert(procExec);
			
			//ドメインモデル「更新処理自動実行管理」に新規登録する
			ProcessExecutionLogManage processExecutionLogManage = new ProcessExecutionLogManage(new ExecutionCode(command.getExecItemCd()),companyId,EndStatus.NOT_IMPLEMENT,CurrentExecutionStatus.WAITING);
			this.processExecLogManRepo.insert(processExecutionLogManage);
			this.lastDateTimeRepo.insert(new LastExecDateTime(companyId,
																new ExecutionCode(command.getExecItemCd()),
																null));
		} else {
			// 更新モード(update mode)
			// ドメインモデル「更新処理自動実行管理」を取得し、現在の実行状態を判断する
			ProcessExecutionLogManage processExecutionLogManage = this.processExecLogManRepo
																	  .getLogByCIdAndExecCd(companyId, execItemCd)
																	  .orElseThrow(() -> new BusinessException("Msg_3"));
			// 更新処理自動実行管理.現在の実行状態　＝　実行中
			if (processExecutionLogManage.getCurrentStatus().isPresent()
					&& processExecutionLogManage.getCurrentStatus().get().equals(CurrentExecutionStatus.RUNNING)) {
				throw new BusinessException("Msg_1318");
			}
			// 更新処理自動実行管理.現在の実行状態　≠　実行中
			this.scopeItemRepo.removeAllByCidAndExecCd(procExec.getCompanyId(), procExec.getExecItemCd().v());
			// ドメインモデル「更新処理自動実行」に更新登録する
			this.procExecRepo.update(procExec);
			// Todo ドメインモデル「実行タスク設定」を更新する

			this.scopeItemRepo.insert(procExec.getCompanyId(),
										procExec.getExecItemCd().v(),
										procExec.getExecScope().getWorkplaceIdList());
		}
		return procExec.getExecItemCd().v();
//		return null;
	}
}
