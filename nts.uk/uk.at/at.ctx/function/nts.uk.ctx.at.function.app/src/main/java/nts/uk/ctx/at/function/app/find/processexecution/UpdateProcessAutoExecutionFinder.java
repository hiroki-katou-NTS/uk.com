package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionTaskSettingDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.SelectedProcessExecutionDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.UpdateProcessAutoExecutionDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceConfigInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateProcessAutoExecutionFinder {

	@Inject
	private ProcessExecutionRepository processExecRepo;

	@Inject
	private WorkplaceConfigInfoAdapter workplaceConfigInfoAdapter;

	@Inject
	private ExecutionTaskSettingRepository executionTaskSettingRepository;

	public List<UpdateProcessAutoExecutionDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.processExecRepo.getProcessExecutionByCompanyId(companyId).stream()
				.map(UpdateProcessAutoExecutionDto::createFromDomain).collect(Collectors.toList());
	}

	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.更新処理自動実行を選択する.更新処理自動実行を選択する
	 */
	public SelectedProcessExecutionDto findByCode(String execItemCd) {
		SelectedProcessExecutionDto dto = new SelectedProcessExecutionDto();
		// ドメインモデル「実行タスク設定」を取得する
		Optional<ExecutionTaskSetting> optTaskSetting = this.executionTaskSettingRepository
				.getByCidAndExecCd(AppContexts.user().companyId(), execItemCd);
		Optional<UpdateProcessAutoExecution> optAutoExec = processExecRepo
				.getProcessExecutionByCidAndExecCd(AppContexts.user().companyId(), execItemCd);
		optTaskSetting.ifPresent(taskSetting -> {
			dto.setTaskSetting(ExecutionTaskSettingDto.fromDomain(taskSetting));
		});
		optAutoExec.ifPresent(autoExec -> {
			// [No.560]職場IDから職場の情報をすべて取得する
			List<WorkplaceInfor> workplaceInfos = workplaceConfigInfoAdapter.getWorkplaceInforByWkpIds(
					AppContexts.user().companyId(),
					autoExec.getExecScope().getWorkplaceIdList(),
					autoExec.getExecScope().getRefDate().orElse(GeneralDate.today()));
			dto.setProcessExecution(UpdateProcessAutoExecutionDto.createFromDomain(autoExec));
			dto.setWorkplaceInfos(workplaceInfos);
		});
		// 選択している「更新処理自動実行」項目の情報を画面に表示する
		return dto;
	}
}
