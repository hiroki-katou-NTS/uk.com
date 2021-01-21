package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ProcessingStatus;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreateScheduleOneDate {
	
	/** 勤務予定 */
	private WorkSchedule workSchedule;
	
	/** エラー */
	private ScheduleErrorLog scheduleErrorLog ;
	
	/** 処理状態 */
	private ProcessingStatus processingStatus;
	
	private List<ErrorMessageInfo>  errorMessageInfo;

	public OutputCreateScheduleOneDate(WorkSchedule workSchedule, ScheduleErrorLog scheduleErrorLog,
			ProcessingStatus processingStatus) {
		super();
		this.workSchedule = workSchedule;
		this.scheduleErrorLog = scheduleErrorLog;
		this.processingStatus = processingStatus;
	}

	public OutputCreateScheduleOneDate(WorkSchedule workSchedule, ProcessingStatus processingStatus,
			List<ErrorMessageInfo> errorMessageInfo) {
		super();
		this.workSchedule = workSchedule;
		this.processingStatus = processingStatus;
		this.errorMessageInfo = errorMessageInfo;
	}
}
