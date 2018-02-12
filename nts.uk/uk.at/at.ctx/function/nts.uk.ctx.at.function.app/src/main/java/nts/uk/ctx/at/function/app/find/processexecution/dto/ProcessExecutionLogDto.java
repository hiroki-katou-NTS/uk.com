package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;

@Data
public class ProcessExecutionLogDto {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	/* コード */
	private String execItemCd;
	
	private String execItemName;
	
	/* 会社ID */
	private String companyId;
	
	/* 現在の実行状態 */
	public Integer currentStatusCd;
	
	/* 現在の実行状態 */
	public String currentStatus;
	
	/* 全体の終了状態 */
	public Integer overallStatusCd;
	
	/* 全体の終了状態 */
	public String overallStatus;
	
	/* 全体のエラー詳細 */
	public String overallError;
	
	/* 前回実行日時 */
	public String lastExecDateTime;
	
	/* スケジュール作成の期間 */
	public GeneralDate schCreateStart;
	
	/* スケジュール作成の期間 */
	public GeneralDate schCreateEnd;
	
	/* 日別作成の期間 */
	public GeneralDate dailyCreateStart;
	
	/* 日別作成の期間 */
	public GeneralDate dailyCreateEnd;
	
	/* 日別計算の期間 */
	public GeneralDate dailyCalcStart;
	
	/* 日別計算の期間 */
	public GeneralDate dailyCalcEnd;
	
	/* 実行ID */
	public String execId;
	
	/* 次回実行日時 */
	private String nextExecDateTime;
	
    private List<ProcessExecutionTaskLogDto> taskLogList;
	
	public ProcessExecutionLogDto() {
		super();
	}

	public ProcessExecutionLogDto(String execItemCd, String companyId, Integer currentStatusCd,
			String currentStatus, Integer overallStatusCd, String overallStatus, String overallError,
			String lastExecDateTime, GeneralDate schCreateStart, GeneralDate schCreateEnd, GeneralDate dailyCreateStart,
			GeneralDate dailyCreateEnd, GeneralDate dailyCalcStart, GeneralDate dailyCalcEnd, String execId,
			List<ProcessExecutionTaskLogDto> taskLogList) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.currentStatusCd = currentStatusCd;
		this.currentStatus = currentStatus;
		this.overallStatusCd = overallStatusCd;
		this.overallStatus = overallStatus;
		this.overallError = overallError;
		this.lastExecDateTime = lastExecDateTime;
		this.schCreateStart = schCreateStart;
		this.schCreateEnd = schCreateEnd;
		this.dailyCreateStart = dailyCreateStart;
		this.dailyCreateEnd = dailyCreateEnd;
		this.dailyCalcStart = dailyCalcStart;
		this.dailyCalcEnd = dailyCalcEnd;
		this.execId = execId;
		this.taskLogList = taskLogList;
	}
	
	public static ProcessExecutionLogDto fromDomain(ProcessExecutionLog procExecLog) {
		List<ProcessExecutionTaskLogDto> taskLogList = procExecLog.getTaskLogList().stream().map(x -> ProcessExecutionTaskLogDto.fromDomain(x)).collect(Collectors.toList());
		Collections.sort(taskLogList, new Comparator<ProcessExecutionTaskLogDto>() {
			@Override
			public int compare(ProcessExecutionTaskLogDto dto1, ProcessExecutionTaskLogDto dto2) {
				return dto1.getTaskId() - dto2.getTaskId();
			}
		});
		return new ProcessExecutionLogDto(
				procExecLog.getExecItemCd().v(),
				procExecLog.getCompanyId(),
				procExecLog.getCurrentStatus() == null ? null : procExecLog.getCurrentStatus().value,
				procExecLog.getCurrentStatus() == null ? "" : EnumAdaptor.valueOf(procExecLog.getCurrentStatus().value, CurrentExecutionStatus.class).name,
				procExecLog.getOverallStatus() == null ? null : procExecLog.getOverallStatus().value,
				procExecLog.getOverallStatus() == null ? "" : EnumAdaptor.valueOf(procExecLog.getOverallStatus().value, EndStatus.class).name,
				procExecLog.getOverallError() == null ? "" : EnumAdaptor.valueOf(procExecLog.getOverallError().value, OverallErrorDetail.class).name,
				procExecLog.getLastExecDateTime() == null ? "" : procExecLog.getLastExecDateTime().toString(DATE_FORMAT),
				procExecLog.getEachProcPeriod().getScheduleCreationPeriod().start(),
				procExecLog.getEachProcPeriod().getScheduleCreationPeriod().end(),
				procExecLog.getEachProcPeriod().getDailyCreationPeriod().start(),
				procExecLog.getEachProcPeriod().getDailyCreationPeriod().end(),
				procExecLog.getEachProcPeriod().getDailyCalcPeriod().start(),
				procExecLog.getEachProcPeriod().getDailyCalcPeriod().end(),
				procExecLog.getExecId(), 
				taskLogList);
	}
}
