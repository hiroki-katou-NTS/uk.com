package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;

/**
 * Dto 更新処理自動実行ログ
 * @author TungVD
 *
 */
@Data
public class ProcessExecutionLogDto {
	
	/* コード */
	private String execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 各処理の期間.スケジュール作成の期間 */
	public GeneralDate scheduleCreationPeriodStart;
	
	/* 各処理の期間.スケジュール作成の期間 */
	public GeneralDate scheduleCreationPeriodEnd;
	
	/* 各処理の期間.日別作成の期間 */
	public GeneralDate dailyCreationPeriodStart;
	
	/* 各処理の期間.日別作成の期間 */
	public GeneralDate dailyCreationPeriodEnd;
	
	/* 各処理の期間.日別計算の期間 */
	public GeneralDate dailyCalcPeriodStart;
	
	/* 各処理の期間.日別計算の期間 */
	public GeneralDate dailyCalcPeriodEnd;
	
	/* 各処理の終了状態 */
    private List<ProcessExecutionTaskLogDto> taskLogList;
    
	/* 実行ID */
	public String execId;
	
	public ProcessExecutionLogDto() {
		super();
	}

	private ProcessExecutionLogDto(
			String execItemCd, 
			String companyId, 
			GeneralDate scheduleCreationPeriodStart,
			GeneralDate scheduleCreationPeriodEnd,
			GeneralDate dailyCreationPeriodStart,
			GeneralDate dailyCreationPeriodEnd,
			GeneralDate dailyCalcPeriodStart,
			GeneralDate dailyCalcPeriodEnd,
			String execId,
			List<ProcessExecutionTaskLogDto> taskLogList) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.scheduleCreationPeriodStart = scheduleCreationPeriodStart;
		this.scheduleCreationPeriodEnd = scheduleCreationPeriodEnd;
		this.dailyCreationPeriodStart = dailyCreationPeriodStart;
		this.dailyCreationPeriodEnd = dailyCreationPeriodEnd;
		this.dailyCalcPeriodStart = dailyCalcPeriodStart;
		this.dailyCalcPeriodEnd = dailyCalcPeriodEnd;
		this.execId = execId;
		this.taskLogList = taskLogList;
	}
	
	public static ProcessExecutionLogDto fromDomain(ProcessExecutionLog procExecLog) {
		List<ProcessExecutionTaskLogDto> taskLogList = procExecLog.getTaskLogList().stream()
				.map(ProcessExecutionTaskLogDto::fromDomain)
				.sorted((ProcessExecutionTaskLogDto dto1, ProcessExecutionTaskLogDto dto2) -> dto1.getTaskId() - dto2.getTaskId())
				.collect(Collectors.toList());
		GeneralDate schCreateStart = null;
		GeneralDate schCreateEnd = null;
		if (procExecLog.getEachProcPeriod() != null 
				&& procExecLog.getEachProcPeriod().isPresent()
				&& procExecLog.getEachProcPeriod().get().getScheduleCreationPeriod() != null
				&& procExecLog.getEachProcPeriod().get().getScheduleCreationPeriod().isPresent()) {
			schCreateStart = procExecLog.getEachProcPeriod().get().getScheduleCreationPeriod().get().start();
			schCreateEnd = procExecLog.getEachProcPeriod().get().getScheduleCreationPeriod().get().end();
		}
		GeneralDate dailyCreateStart = null;
		GeneralDate dailyCreateEnd = null;
		if (procExecLog.getEachProcPeriod() != null && procExecLog.getEachProcPeriod().isPresent()
				&& procExecLog.getEachProcPeriod().get().getDailyCreationPeriod() != null
				&& procExecLog.getEachProcPeriod().get().getDailyCreationPeriod().isPresent()) {
			dailyCreateStart = procExecLog.getEachProcPeriod().get().getDailyCreationPeriod().get().start();
			dailyCreateEnd = procExecLog.getEachProcPeriod().get().getDailyCreationPeriod().get().end();
		}
		GeneralDate dailyCalcStart = null;
		GeneralDate dailyCalcEnd = null;
		if (procExecLog.getEachProcPeriod() != null && procExecLog.getEachProcPeriod().isPresent()
				&& procExecLog.getEachProcPeriod().get().getDailyCalcPeriod() != null
				&& procExecLog.getEachProcPeriod().get().getDailyCalcPeriod().isPresent()) {
			dailyCalcStart = procExecLog.getEachProcPeriod().get().getDailyCalcPeriod().get().start();
			dailyCalcEnd = procExecLog.getEachProcPeriod().get().getDailyCalcPeriod().get().end();
		}
		return new ProcessExecutionLogDto(
				procExecLog.getExecItemCd().v(),
				procExecLog.getCompanyId(),
				schCreateStart,
				schCreateEnd,
				dailyCreateStart,
				dailyCreateEnd,
				dailyCalcStart,
				dailyCalcEnd,
				procExecLog.getExecId(), 
				taskLogList);
	}
}
