package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;


/**
 * 控除相殺時間
 * @author ken_takasu
 *
 */
@Value
@AllArgsConstructor
public class DeductionOffSetTime {
	private AttendanceTime annualLeave;
	private AttendanceTime retentionYearly;
	private AttendanceTime SpecialHoliday;
	private AttendanceTime CompensatoryLeave;
	
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.retentionYearly.valueAsMinutes()+this.SpecialHoliday.valueAsMinutes()+this.CompensatoryLeave.valueAsMinutes();
		return totalTime;
	}
	
	
	/**
	 * 時間休暇相殺を利用して相殺した各時間を求める  （一時的に作成）
	 * @return
	 */
	public DeductionOffSetTime createDeductionOffSetTime(int lateRemainingTime,TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime) {
		
		AttendanceTime timeAnnualLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeAnnualLeaveUseTime());
		lateRemainingTime -= timeAnnualLeaveUseTime.valueAsMinutes();

		AttendanceTime timeCompensatoryLeaveUseTime = new AttendanceTime(0);
		AttendanceTime sixtyHourExcessHolidayUseTime = new AttendanceTime(0);
		AttendanceTime timeSpecialHolidayUseTime = new AttendanceTime(0);
		
		if(lateRemainingTime > 0) {
			timeCompensatoryLeaveUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeCompensatoryLeaveUseTime());
			lateRemainingTime -= timeCompensatoryLeaveUseTime.valueAsMinutes();
		}
		
		if(lateRemainingTime > 0) {
			sixtyHourExcessHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getSixtyHourExcessHolidayUseTime());
			lateRemainingTime -= sixtyHourExcessHolidayUseTime.valueAsMinutes();
		}
		
		if(lateRemainingTime > 0) {
			timeSpecialHolidayUseTime = calcOffSetTime(lateRemainingTime,TimeVacationAdditionRemainingTime.getTimeSpecialHolidayUseTime());
			lateRemainingTime -= timeSpecialHolidayUseTime.valueAsMinutes();
		}
				
		return new DeductionOffSetTime(
				timeAnnualLeaveUseTime,
				timeCompensatoryLeaveUseTime,
				sixtyHourExcessHolidayUseTime,
				timeSpecialHolidayUseTime);
	}
	
	/**
	 * 
	 * @param lateRemainingTime 遅刻残数
	 * @param timeVacationUseTime　時間休暇使用時間
	 * @return
	 */
	public AttendanceTime calcOffSetTime(int lateRemainingTime,AttendanceTime timeVacationUseTime) {
		int offSetTime;
		//相殺する時間を計算（比較）する
		if(timeVacationUseTime.lessThanOrEqualTo(lateRemainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		}else {
			offSetTime = lateRemainingTime;
		}
		return new AttendanceTime(offSetTime);
	}
		
	
}
