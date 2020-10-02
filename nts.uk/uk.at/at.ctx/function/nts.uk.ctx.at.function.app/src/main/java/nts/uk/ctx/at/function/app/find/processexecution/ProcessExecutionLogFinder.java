package nts.uk.ctx.at.function.app.find.processexecution;

//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ExecutionItemInfomationDto;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionDto;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
//import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.ctx.at.function.dom.processexecution.repository.ExecutionTaskSettingRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogManageRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.ExecutionTaskSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.task.schedule.UkJobScheduler;
/**
 * 起動時処理 - KBT002	
 * @author tutk
 *
 */
@Stateless
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
	private UkJobScheduler scheduler;
	
	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.F:実行選択.アルゴリズム.起動時処理.起動時処理
	 * @return
	 */
	public List<ExecutionItemInfomationDto> findAll() {
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「更新処理自動実行」を取得する
		List<ProcessExecution> listProcessExecution = this.procExecRepo.getProcessExecutionByCompanyId(companyId);
		List<String> listExecItemCd = listProcessExecution.stream()
				.map(item -> item.getExecItemCd().v())
				.collect(Collectors.toList());
		
		// データが0件(0 dữ liệu)
		if (listExecItemCd.isEmpty()) {
			// エラーメッセージ（#Msg_851）を表示(hiển thị error message （#Msg_851）)
			throw new BusinessException("Msg_851");
		}
		
		Map<String, ProcessExecutionDto> mapProcessExecution = listProcessExecution.stream()
				.map(domain -> ProcessExecutionDto.fromDomain(domain))
				.collect(Collectors.toMap(ProcessExecutionDto::getExecItemCd, Function.identity()));
		
		// ドメインモデル「更新処理自動実行ログ」を取得する
		Map<String, ProcessExecutionLog> mapProcessExecutionLog = this.procExecLogRepo.getProcessExecutionLogByCompanyIdAndExecItemCd(companyId, listExecItemCd).stream()
				.collect(Collectors.toMap(item -> item.getExecItemCd().v(), Function.identity()));
		
		// ドメインモデル「更新処理自動実行管理」を取得する
		Map<String, ProcessExecutionLogManage> mapProcessExecutionLogManage = this.processExecLogManRepo.getProcessExecutionLogByCompanyIdAndExecItemCd(companyId, listExecItemCd).stream()
				.collect(Collectors.toMap(item -> item.getExecItemCd().v(), Function.identity()));
		
		// ドメインモデル「実行タスク設定」を取得する
		Map<String, ExecutionTaskSetting> mapExecutionTaskSetting = this.execSettingRepo.getByCidAndExecItemCd(companyId, listExecItemCd).stream()
				.collect(Collectors.toMap(item -> item.getExecItemCd().v(), Function.identity()));
		
		List<ExecutionItemInfomationDto> listResult = listExecItemCd.stream()
				.map(execItemCd -> {
					// OUTPUT「実行項目情報」を作成する
					ExecutionItemInfomationDto dto = ExecutionItemInfomationDto.builder()
							// 実行項目情報．更新処理自動実行 = 取得した「更新処理自動実行」
							.updateProcessAutoExec(mapProcessExecution.get(execItemCd))
							// 実行項目情報．更新処理自動実行ログ = 取得した「更新処理自動実行ログ」
							.updateProcessAutoExecLog(mapProcessExecutionLog.get(execItemCd))
							// 実行項目情報．更新処理自動実行管理 = 取得した「更新処理自動実行管理」
							.updateProcessAutoExecManage(mapProcessExecutionLogManage.get(execItemCd))
							// 実行項目情報．実行タスク設定 = 取得した「実行タスク設定」
							.executionTaskSetting(mapExecutionTaskSetting.get(execItemCd))
							// 実行項目情報．次回実行日時 = empty
							.nextExecDate(null)
							// 次回実行日時を過ぎているか = false
							.isOverAverageExecTime(false)
							// 実行平均時間を超えているか = false
							.isPastNextExecDate(false)
							.build();
					
					// 作成した「実行項目情報．実行タスク設定．更新処理
					if (dto.getExecutionTaskSetting().isEnabledSetting()) {
						// 次回実行日時作成処理
						// TODO
						
						// 作成した「実行項目情報」を更新する
						// TODO
						
						// 次回実行日時 < システム日時
						// TODO
						if (true) {
							// 「実行項目情報」を更新する
							// TODO
						}
					} else {
						
					}
					
					// 「実行項目情報．更新処理自動実行管理」を確認する
					// TODO
					if (true) {
						// 過去の実行平均時間を超過しているか
						// TODO
						
						// 「実行項目情報」を更新する
						// TODO
					}
					
					return dto;
				})
				.collect(Collectors.toList());

		// OUTPUT「実行項目情報」<List>を返す
		return listResult;
		
		
//		String KBT002_165 = TextResource.localize("KBT002_165");
//			return this.processExecLogManRepo.getProcessExecutionLogByCompanyId(companyId).stream().map(a -> {
//				ProcessExecutionLogDto dto = null;
//				//ドメインモデル「更新処理自動実行ログ」を取得する
//				Optional<ProcessExecutionLog> processExecutionLogOpt = this.procExecLogRepo.getLog(companyId, a.getExecItemCd().v());
//				if(processExecutionLogOpt!=null && processExecutionLogOpt.isPresent()){
//					dto = ProcessExecutionLogDto.fromDomain(processExecutionLogOpt.get(),a);
//				}else{
//					dto = ProcessExecutionLogDto.fromDomain(new ProcessExecutionLog(new ExecutionCode(a.getExecItemCd().v()),companyId,IdentifierUtil.randomUniqueId()),a);
//				}
//				GeneralDateTime nextFireTime = null;
//				// ドメインモデル「実行タスク設定」を取得する	
//				Optional<ExecutionTaskSetting> taskSettingOpt = this.execSettingRepo.getByCidAndExecCd(companyId, a.getExecItemCd().v());
//				//次回実行日時作成処理
//				if(taskSettingOpt.isPresent()) {
//					try {
//						if(taskSettingOpt.get().isEnabledSetting()) {
//							nextFireTime = taskSettingOpt
//									.map(e -> e.getScheduleId())
//									.flatMap(id -> this.scheduler.getNextFireTime(id))
//									.orElse(null);
//						}
//					} catch (Exception e2) {
//						nextFireTime = null;
//					}
//					
//				}
//				if (taskSettingOpt.isPresent()) {
//					ExecutionTaskSetting setting = Optional.of(taskSettingOpt.get()).get();
//					dto.setNextExecDateTime(
//							(nextFireTime == null || !setting.getNextExecDateTime().isPresent()) ? 
//									KBT002_165 : nextFireTime.toString("yyyy/MM/dd HH:mm:ss"));
//				}else{
//					dto.setNextExecDateTime(KBT002_165);
//				}
//				Optional<ProcessExecution> procExecOpt = this.procExecRepo.getProcessExecutionByCidAndExecCd(companyId, a.getExecItemCd().v());
//				if (procExecOpt.isPresent()) {
//					dto.setExecItemName(procExecOpt.get().getExecItemName().v());
//				}
//				dto.setRangeDateTime(CalTimeRangeDateTimeToString.calTimeExec(a.getLastExecDateTime(), a.getLastEndExecDateTime()));
//				return dto;
//			}).collect(Collectors.toList());
	}
	
}