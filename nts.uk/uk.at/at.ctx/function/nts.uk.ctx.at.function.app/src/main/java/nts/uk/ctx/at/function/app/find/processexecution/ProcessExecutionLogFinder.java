package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionLogDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ProcessExecutionLogFinder {

	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;
	
	@Inject
	private ExecutionTaskSettingRepository execSettingRepo;
	
	@Inject
	private ProcessExecutionRepository procExecRepo;

	public List<ProcessExecutionLogDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.procExecLogRepo.getProcessExecutionLogByCompanyId(companyId)
				.stream().map(a -> {
					ProcessExecutionLogDto dto = ProcessExecutionLogDto.fromDomain(a);
					// ドメインモデル「実行タスク設定」を取得する
					Optional<ExecutionTaskSetting> taskSettingOpt = this.execSettingRepo.getByCidAndExecCd(companyId, a.getExecItemCd().v());
					if (taskSettingOpt.isPresent()) {
						ExecutionTaskSetting setting = Optional.of(taskSettingOpt.get()).get();
						dto.setNextExecDateTime(
								setting.getNextExecDateTime() == null ? 
										"" : setting.getNextExecDateTime().toString("yyyy/MM/dd HH:mm:ss"));
					}
					Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, a.getExecItemCd().v());
					if (procExecOpt.isPresent()) {
						dto.setExecItemName(procExecOpt.get().getExecItemName().v());
					}
					return dto;
				}).collect(Collectors.toList());
	}
}