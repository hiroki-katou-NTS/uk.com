package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.processexecution.dto.*;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionService.NextExecutionDateTimeDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionService;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 起動時処理 - KBT002
 * 
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ProcessExecutionLogFinder {

	@Inject
	private ProcessExecutionLogRepository procExecLogRepo;

	@Inject
	private ExecutionTaskSettingRepository execSettingRepo;

	@Inject
	private ProcessExecutionRepository procExecRepo;

	@Inject
	private ProcessExecutionLogManageRepository processExecLogManRepo;

	@Inject
	private ProcessExecutionService processExecutionService;

	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.F:実行選択.アルゴリズム.起動時処理.起動時処理
	 * 
	 * @return
	 */
	public List<ExecutionItemInfomationDto> findAll() {
		String companyId = AppContexts.user().companyId();

		// ドメインモデル「更新処理自動実行」を取得する
		List<UpdateProcessAutoExecution> listProcessExecution = this.procExecRepo
				.getProcessExecutionByCompanyId(companyId);
		return this.findData(listProcessExecution, companyId);
	}
	
	public ExecutionItemInfomationDto findOne(String execItemCd) {
		String companyId = AppContexts.user().companyId();
		return this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, execItemCd)
				.map(item -> this.findData(Arrays.asList(item), companyId).get(0))
				.orElse(null);
	}

	private List<ExecutionItemInfomationDto> findData(List<UpdateProcessAutoExecution> listProcessExecution,
													  String companyId) {
		List<String> listExecItemCd = listProcessExecution.stream().map(item -> item.getExecItemCode().v())
				.collect(Collectors.toList());
		// データが0件(0 dữ liệu)
		if (listExecItemCd.isEmpty()) {
			// エラーメッセージ（#Msg_851）を表示(hiển thị error message （#Msg_851）)
			throw new BusinessException("Msg_851");
		}

		Map<String, UpdateProcessAutoExecution> mapProcessExecution = listProcessExecution.stream()
				.collect(Collectors.toMap(item -> item.getExecItemCode().v(), Function.identity(), (a, b) -> a, TreeMap::new));

		// ドメインモデル「更新処理自動実行ログ」を取得する
		Map<String, ProcessExecutionLog> mapProcessExecutionLog = this.procExecLogRepo
				.getProcessExecutionLogByCompanyIdAndExecItemCd(companyId, listExecItemCd).stream().collect(Collectors
						.toMap(item -> item.getExecItemCd().v(), Function.identity(), (a, b) -> a, TreeMap::new));

		// ドメインモデル「更新処理自動実行管理」を取得する
		Map<String, ProcessExecutionLogManage> mapProcessExecutionLogManage = this.processExecLogManRepo
				.getProcessExecutionLogByCompanyIdAndExecItemCd(companyId, listExecItemCd).stream().collect(Collectors
						.toMap(item -> item.getExecItemCd().v(), Function.identity(), (a, b) -> a, TreeMap::new));

		// ドメインモデル「実行タスク設定」を取得する
		Map<String, ExecutionTaskSetting> mapExecutionTaskSetting = this.execSettingRepo
				.getByCidAndExecItemCd(companyId, listExecItemCd).stream().collect(Collectors
						.toMap(item -> item.getExecItemCd().v(), Function.identity(), (a, b) -> a, TreeMap::new));

		List<ExecutionItemInfomationDto> listResult = listExecItemCd.stream().map(execItemCd -> {
			// OUTPUT「実行項目情報」を作成する
			UpdateProcessAutoExecution processExecution = mapProcessExecution.get(execItemCd);
			ProcessExecutionLog processExecutionLog = mapProcessExecutionLog.get(execItemCd);
			ProcessExecutionLogManage processExecutionLogManage = mapProcessExecutionLogManage.get(execItemCd);
			ExecutionTaskSetting executionTaskSetting = mapExecutionTaskSetting.get(execItemCd);
			ExecutionItemInfomationDto dto = ExecutionItemInfomationDto.builder().execItemCd(execItemCd)
					// 実行項目情報．更新処理自動実行 = 取得した「更新処理自動実行」
					.updateProcessAutoExec(UpdateProcessAutoExecutionDto.createFromDomain(processExecution))
					// 実行項目情報．更新処理自動実行ログ = 取得した「更新処理自動実行ログ」
					.updateProcessAutoExecLog(
							processExecutionLog != null ? ProcessExecutionLogDto.fromDomain(processExecutionLog) : null)
					// 実行項目情報．更新処理自動実行管理 = 取得した「更新処理自動実行管理」
					.updateProcessAutoExecManage(processExecutionLogManage != null
							? ProcessExecutionLogManageDto.fromDomain(processExecutionLogManage)
							: null)
					// 実行項目情報．実行タスク設定 = 取得した「実行タスク設定」
					.executionTaskSetting(
							executionTaskSetting != null ? ExecutionTaskSettingDto.fromDomain(executionTaskSetting)
									: null)
					// 実行項目情報．次回実行日時 = empty
					.nextExecDate(null)
					// 次回実行日時を過ぎているか = false
					.isOverAverageExecTime(false)
					// 実行平均時間を超えているか = false
					.isPastNextExecDate(false).build();

			// 作成した「実行項目情報．実行タスク設定．更新処理
			ExecutionTaskSettingDto execTaskSetDto = dto.getExecutionTaskSetting();
			NextExecutionDateTimeDto nextExecutionDateTimeDto = null;
			// 作成した「実行項目情報．実行タスク設定．更新処理有効設定」を確認する
			if (execTaskSetDto != null && execTaskSetDto.isEnabledSetting()) {
				// 次回実行日時を過ぎているか
				nextExecutionDateTimeDto = this.processExecutionService
						.isPassNextExecutionDateTime(companyId, execItemCd);
				GeneralDateTime nextExecDateTime = nextExecutionDateTimeDto.getNextExecDateTime().orElse(null);

				// 作成した「実行項目情報」を更新する
				execTaskSetDto.setNextExecDateTime(nextExecDateTime);
				dto.setExecutionTaskSetting(execTaskSetDto);
				dto.setNextExecDate(nextExecDateTime);
				dto.setIsPastNextExecDate(nextExecutionDateTimeDto.isPassNextExecDateTime());
			}

			// 「実行項目情報．更新処理自動実行管理」を確認する
			// 「実行項目情報．更新処理自動実行管理」 <> empty AND 「実行項目情報．更新処理自動実行管理．前回実行日時」 <> empty
			if (dto.getUpdateProcessAutoExecManage() != null
					&& dto.getUpdateProcessAutoExecManage().getLastExecDateTime() != null) {
				// 過去の実行平均時間を超過しているか
				boolean isOverAverageExecTime = this.processExecutionService.isPassAverageExecTimeExceeded(companyId,
						processExecution.getExecItemCode(), processExecutionLogManage.getLastExecDateTime().get()); //#115526

				// 「実行項目情報」を更新する
				dto.setIsOverAverageExecTime(isOverAverageExecTime);
			}

			return dto;
		}).collect(Collectors.toList());

		// OUTPUT「実行項目情報」<List>を返す
		return listResult;
	}
}