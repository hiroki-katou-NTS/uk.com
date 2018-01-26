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
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionName;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionScopeClassification;
import nts.uk.ctx.at.function.dom.processexecution.LastExecDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.alarmextraction.IndividualAlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.alarmextraction.WorkplaceAlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
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
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveProcessExecutionCommandHandler extends CommandHandlerWithResult<SaveProcessExecutionCommand, String> {

	@Inject
	private ProcessExecutionRepository procExecRepo;

	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;
	
	@Inject
	private LastExecDateTimeRepository lastDateTimeRepo;
	
	@Inject
	private ExecutionScopeItemRepository scopeItemRepo;


	@Override
	protected String handle(CommandHandlerContext<SaveProcessExecutionCommand> context) {
		String companyId = AppContexts.user().companyId();

		SaveProcessExecutionCommand command = context.getCommand();
		
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
		IndividualAlarmExtraction indvAlarm = new IndividualAlarmExtraction(command.isIndvAlarmCls(),
				command.isIndvMailPrin(),
				command.isIndvMailMng());
		WorkplaceAlarmExtraction wkpAlarm = new WorkplaceAlarmExtraction(command.isWkpAlarmCls(), command.isWkpMailMng());
		
		PersonalScheduleCreationPeriod period = new PersonalScheduleCreationPeriod(
										/*command.getCreationPeriod() == null ? null : */new CreationPeriod(command.getCreationPeriod()),
										/*command.getTargetDate() == null ? null : */new TargetDate(command.getTargetDate()),
										EnumAdaptor.valueOf(command.getTargetMonth(), TargetMonth.class));
		
		PersonalScheduleCreationTarget target = new PersonalScheduleCreationTarget(
				EnumAdaptor.valueOf(command.getCreationTarget(), TargetClassification.class),
				new TargetSetting(command.isRecreateWorkType(), command.isManualCorrection(), command.isCreateEmployee(), command.isRecreateTransfer()));
		PersonalScheduleCreation perSchCreation = new PersonalScheduleCreation(period, command.isPerScheduleCls(), target);
		DailyPerformanceCreation dailyPerfCreation =
								new DailyPerformanceCreation(command.isDailyPerfCls(),
										EnumAdaptor.valueOf(command.getDailyPerfItem(), DailyPerformanceItem.class),
										command.isMidJoinEmployee());

		ProcessExecutionSetting execSetting = 
				new ProcessExecutionSetting(indvAlarm, wkpAlarm, perSchCreation, dailyPerfCreation, command.isReflectResultCls(), command.isMonthlyAggCls());
		
		ProcessExecution procExec =
				new ProcessExecution(companyId,
						new ExecutionCode(command.getExecItemCd()),
						new ExecutionName(command.getExecItemName()), execScope, execSetting);
		
		procExec.validate();
		if (command.isNewMode()) {
			Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, command.getExecItemCd());
			if (procExecOpt.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			// ドメインモデル「更新処理自動実行」に新規登録する
			procExecRepo.insert(procExec);
			
//			// ドメインモデル「実行タスク設定」に新規登録する
//			ExecutionTaskSetting taskSetting = new ExecutionTaskSetting(new ExecutionCode(command.getExecItemCd()), companyId, false, false);
//			execSettingRepo.insert(taskSetting);
			
			// ドメインモデル「更新処理自動実行ログ」に新規登録する
			String execId = IdentifierUtil.randomUniqueId();
			ProcessExecutionLog procExecLog =
								new ProcessExecutionLog(new ExecutionCode(command.getExecItemCd()),
														companyId,
														EndStatus.NOT_IMPLEMENT,
														execId,
														CurrentExecutionStatus.INVALID);
			// TODO - Confirming
//			procExecLog.setTaskLogList(new ArrayList<>());
//			procExecLog.setEachProcPeriod(
//					new EachProcessPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today()), new DatePeriod(GeneralDate.today(), GeneralDate.today()), new DatePeriod(GeneralDate.today(), GeneralDate.today())));
			this.procExecLogRepo.insert(procExecLog);
			this.lastDateTimeRepo.insert(new LastExecDateTime(companyId,
																new ExecutionCode(command.getExecItemCd()),
																null));
		} else {
			this.scopeItemRepo.removeAllByCidAndExecCd(procExec.getCompanyId(), procExec.getExecItemCd().v());
			procExecRepo.update(procExec);
			this.scopeItemRepo.insert(procExec.getCompanyId(),
										procExec.getExecItemCd().v(),
										procExec.getExecScope().getWorkplaceIdList());
		}
		return procExec.getExecItemCd().v();
//		return null;
	}
}
