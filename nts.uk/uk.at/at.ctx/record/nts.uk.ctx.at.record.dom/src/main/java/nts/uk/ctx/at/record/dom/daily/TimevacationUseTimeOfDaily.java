package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の時間休暇使用時間
 * @author ken_takasu
 *
 */
@Getter
public class TimevacationUseTimeOfDaily {
	
	private AttendanceTime TimeAnnualLeaveUseTime;
	private AttendanceTime TimeCompensatoryLeaveUseTime;
	private AttendanceTime sixtyHourExcessHolidayUseTime;
	private AttendanceTime TimeSpecialHolidayUseTime;
	
	/**
	 * Constructor 
	 */
	public TimevacationUseTimeOfDaily(AttendanceTime timeAnnualLeaveUseTime,
			AttendanceTime timeCompensatoryLeaveUseTime, AttendanceTime sixtyHourExcessHolidayUseTime,
			AttendanceTime timeSpecialHolidayUseTime) {
		super();
		TimeAnnualLeaveUseTime = timeAnnualLeaveUseTime;
		TimeCompensatoryLeaveUseTime = timeCompensatoryLeaveUseTime;
		this.sixtyHourExcessHolidayUseTime = sixtyHourExcessHolidayUseTime;
		TimeSpecialHolidayUseTime = timeSpecialHolidayUseTime;
	}
	
	
	/**
	 * 各使用時間から相殺時間を控除する
	 * 
	 * @param deductionOffSetTime
	 */
	public void subtractionDeductionOffSetTime(DeductionOffSetTime deductionOffSetTime) {
		this.TimeAnnualLeaveUseTime = new AttendanceTime(this.TimeAnnualLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getAnnualLeave().valueAsMinutes());
		this.TimeCompensatoryLeaveUseTime = new AttendanceTime(this.TimeCompensatoryLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getCompensatoryLeave().valueAsMinutes());
		this.sixtyHourExcessHolidayUseTime = new AttendanceTime(this.sixtyHourExcessHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getRetentionYearly().valueAsMinutes());
		this.TimeSpecialHolidayUseTime = new AttendanceTime(this.TimeSpecialHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getSpecialHoliday().valueAsMinutes());
	}





}
