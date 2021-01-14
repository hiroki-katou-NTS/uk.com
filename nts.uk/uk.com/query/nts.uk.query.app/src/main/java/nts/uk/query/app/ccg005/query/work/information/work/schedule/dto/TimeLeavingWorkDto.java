package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeLeavingWorkDto {
	/** 勤務NO */
	private Integer workNo;
	/** 出勤 */
	private TimeActualStampDto attendanceStamp;
	/** 退勤 */
	private TimeActualStampDto leaveStamp;
	/** 遅刻を取り消した */
	private boolean canceledLate;
	/** 早退を取り消した */
	private boolean CanceledEarlyLeave;
	
	private TimeSpanForCalcDto timespan;
}
