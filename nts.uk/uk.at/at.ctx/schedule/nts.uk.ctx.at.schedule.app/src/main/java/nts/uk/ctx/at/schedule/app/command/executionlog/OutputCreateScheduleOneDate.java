package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ProcessingStatus;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

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

	public OutputCreateScheduleOneDate(WorkSchedule workSchedule, ScheduleErrorLog scheduleErrorLog,
			ProcessingStatus processingStatus) {
		super();
		this.workSchedule = workSchedule;
		this.scheduleErrorLog = scheduleErrorLog;
		this.processingStatus = processingStatus;
	}
}
