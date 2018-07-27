package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;

/*
 * 戻り値：月別実績の勤怠時間を集計
 */
@Getter
public class AggregateAttendanceTimeValue {

	/** 月別実績の勤怠時間 */
	@Setter
	private AttendanceTimeOfMonthly attendanceTime;
	/** 週別実績の勤怠時間 */
	private List<AttendanceTimeOfWeekly> attendanceTimeWeeks;
	
	public AggregateAttendanceTimeValue(){
		
		this.attendanceTime = null;
		this.attendanceTimeWeeks = new ArrayList<>();
	}
}
