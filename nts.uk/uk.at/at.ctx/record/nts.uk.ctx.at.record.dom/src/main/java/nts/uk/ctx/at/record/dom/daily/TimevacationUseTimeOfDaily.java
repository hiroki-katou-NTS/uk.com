package nts.uk.ctx.at.record.dom.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の時間休暇使用時間
 * 
 * @author ken_takasu
 *
 */
@Getter
@AllArgsConstructor
public class TimevacationUseTimeOfDaily {

	private AttendanceTime TimeAnnualLeaveUseTime;// 時間年休使用時間
	private AttendanceTime TimeCompensatoryLeaveUseTime;// 時間代休使用時間
	private AttendanceTime sixtyHourExcessHolidayUseTime;// 超過有休使用時間
	private AttendanceTime TimeSpecialHolidayUseTime;// 特別休暇使用時間

	/**
	 * 各使用時間を合計した時間を返す
	 * 
	 * @author ken_takasu
	 * @return
	 */
	public int getTotalTimeVacationUseTime() {
		int totalTime = this.TimeAnnualLeaveUseTime.valueAsMinutes()
				+ this.TimeCompensatoryLeaveUseTime.valueAsMinutes()
				+ this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
				+ this.TimeSpecialHolidayUseTime.valueAsMinutes();
		return totalTime;
	}

	/**
	 * 中身が全て0：00の日別実績の時間休暇使用時間を返す
	 * 
	 * @author ken_takasu
	 * @return
	 */
	public static TimevacationUseTimeOfDaily noTimevacationUseTimeOfDaily() {
		return new TimevacationUseTimeOfDaily(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0));
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
	
	
	
	/**
	 * 各使用時間から相殺時間を控除した日別実績の時間休暇使用時間を返す
	 * 
	 * @author ken_takasu
	 * @param deductionOffSetTime
	 * @return
	 */
	public TimevacationUseTimeOfDaily calcTimevacationUseTimeOfDaily(DeductionOffSetTime deductionOffSetTime) {
		TimevacationUseTimeOfDaily timevacationUseTimeOfDaily = new TimevacationUseTimeOfDaily(
				new AttendanceTime(this.TimeAnnualLeaveUseTime.valueAsMinutes()
						- deductionOffSetTime.getAnnualLeave().valueAsMinutes()),
				new AttendanceTime(this.TimeCompensatoryLeaveUseTime.valueAsMinutes()
						- deductionOffSetTime.getCompensatoryLeave().valueAsMinutes()),
				new AttendanceTime(this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
						- deductionOffSetTime.getRetentionYearly().valueAsMinutes()),
				new AttendanceTime(this.TimeSpecialHolidayUseTime.valueAsMinutes()
						- deductionOffSetTime.getSpecialHoliday().valueAsMinutes()));
		return timevacationUseTimeOfDaily;
	}

}
