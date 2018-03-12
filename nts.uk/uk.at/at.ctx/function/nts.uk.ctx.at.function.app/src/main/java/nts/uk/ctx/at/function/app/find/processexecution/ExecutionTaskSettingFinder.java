package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExecutionTaskSettingFinder {

	@Inject
	private ExecutionTaskSettingRepository execTaskRepo;
	
	public ExecutionTaskSettingDto find(String execItemCd) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「実行タスク設定」を取得する
		Optional<ExecutionTaskSetting> taskSettingOpt = this.execTaskRepo.getByCidAndExecCd(companyId, execItemCd);
		if (taskSettingOpt.isPresent()) {
			return Optional.of(ExecutionTaskSettingDto.fromDomain(taskSettingOpt.get())).get();
		}
		return null;
	}
}
