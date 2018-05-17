package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.LastExecDateTimeRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.task.schedule.UkJobScheduler;

@Stateless
public class RemoveProcessExecutionCommandHandler extends CommandHandler<RemoveProcessExecutionCommand> {

	@Inject
	private ProcessExecutionRepository procExecRepo;
	
	@Inject
	private ExecutionTaskSettingRepository execSetRepo;
	
	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;
	
	@Inject
	private LastExecDateTimeRepository lastExecRepo;
	
	@Inject
	private ProcessExecutionLogManageRepository processExecLogManRepo;
	
	@Inject
	private ProcessExecutionLogHistRepository processExecLogHistRepo;
	
	@Inject
	private UkJobScheduler scheduler;
	@Override
	protected void handle(CommandHandlerContext<RemoveProcessExecutionCommand> context) {
		String companyId = AppContexts.user().companyId();
		RemoveProcessExecutionCommand command = context.getCommand();
		//ドメインモデル「実行タスク設定」を取得する
		Optional<ExecutionTaskSetting> executionTaskSettingOpt = execSetRepo.getByCidAndExecCd(companyId, command.getExecItemCd());
		if(executionTaskSettingOpt.isPresent()){
			ExecutionTaskSetting execTaskSetting = executionTaskSettingOpt.get();
			String scheduleId = execTaskSetting.getScheduleId();
			this.scheduler.unscheduleOnCurrentCompany(SortingProcessScheduleJob.class,scheduleId);
			Optional<String> endScheduleId = execTaskSetting.getEndScheduleId();
			if(endScheduleId.isPresent()){
				this.scheduler.unscheduleOnCurrentCompany(SortingProcessEndScheduleJob.class,endScheduleId.get());
			}
		}
		//ドメインモデル「実行タスク設定」を削除する
		this.execSetRepo.remove(companyId, command.getExecItemCd());
		//ドメインモデル「更新処理自動実行ログ」を削除する
		this.procExecLogRepo.removeList(companyId, command.getExecItemCd());
		//ドメインモデル「更新処理自動実行」を削除する
		this.procExecRepo.remove(companyId, command.getExecItemCd());
		//ドメインモデル「更新処理前回実行日時」を削除する
		this.lastExecRepo.remove(companyId, command.getExecItemCd());
		//ドメインモデル「更新処理自動実行管理」を削除する
		this.processExecLogManRepo.remove(companyId, command.getExecItemCd());
		//ドメインモデル「更新処理自動実行ログ履歴」を削除する
		this.processExecLogHistRepo.remove(companyId, command.getExecItemCd());
	}
}
