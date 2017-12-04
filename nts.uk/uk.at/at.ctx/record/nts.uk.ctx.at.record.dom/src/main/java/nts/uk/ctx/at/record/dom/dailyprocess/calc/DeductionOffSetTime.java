package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

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
	private AttendanceTime specialHoliday;
	private AttendanceTime compensatoryLeave;
	
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @author ken_takasu
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.retentionYearly.valueAsMinutes()+this.specialHoliday.valueAsMinutes()+this.compensatoryLeave.valueAsMinutes();
		return totalTime;
	}
	
	/**
	 * 時間休暇相殺を利用して相殺した各時間を求める(一時的に作成,本来は優先順位に応じて処理する順番を変更する）
	 * @author ken_takasu
	 * @param remainingTime
	 * @param TimeVacationAdditionRemainingTime
	 * @return
	 */
	public static DeductionOffSetTime createDeductionOffSetTime(
			int remainingTime,
			TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime
			) {
		AttendanceTime timeAnnualLeaveUseTime = new AttendanceTime(0);
		AttendanceTime timeCompensatoryLeaveUseTime = new AttendanceTime(0);
		AttendanceTime sixtyHourExcessHolidayUseTime = new AttendanceTime(0);
		AttendanceTime timeSpecialHolidayUseTime = new AttendanceTime(0);
		if(remainingTime > 0) {
			timeAnnualLeaveUseTime = calcOffSetTime(remainingTime,TimeVacationAdditionRemainingTime.getTimeAnnualLeaveUseTime());
			remainingTime -= timeAnnualLeaveUseTime.valueAsMinutes();
		}	
		if(remainingTime > 0) {
			timeCompensatoryLeaveUseTime = calcOffSetTime(remainingTime,TimeVacationAdditionRemainingTime.getTimeCompensatoryLeaveUseTime());
			remainingTime -= timeCompensatoryLeaveUseTime.valueAsMinutes();
		}
		
		if(remainingTime > 0) {
			sixtyHourExcessHolidayUseTime = calcOffSetTime(remainingTime,TimeVacationAdditionRemainingTime.getSixtyHourExcessHolidayUseTime());
			remainingTime -= sixtyHourExcessHolidayUseTime.valueAsMinutes();
		}
		
		if(remainingTime > 0) {
			timeSpecialHolidayUseTime = calcOffSetTime(remainingTime,TimeVacationAdditionRemainingTime.getTimeSpecialHolidayUseTime());
			remainingTime -= timeSpecialHolidayUseTime.valueAsMinutes();
		}
				
		return new DeductionOffSetTime(
				timeAnnualLeaveUseTime,
				timeCompensatoryLeaveUseTime,
				sixtyHourExcessHolidayUseTime,
				timeSpecialHolidayUseTime);
	}
	
	/**
	 * 相殺時間を求める
	 * @author ken_takasu
	 * @param remainingTime 残数
	 * @param timeVacationUseTime　時間休暇使用時間
	 * @return
	 */
	public static AttendanceTime calcOffSetTime(
			int remainingTime,
			AttendanceTime timeVacationUseTime
			) {
		int offSetTime;
		//相殺する時間を計算（比較）する
		if(timeVacationUseTime.lessThanOrEqualTo(remainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		}else {
			offSetTime = remainingTime;
		}
		return new AttendanceTime(offSetTime);
	}
	
	
	/**
	 * 複数の控除相殺時間を合算する
	 * @author ken_takasu
	 * @param deductionOffSetTimeList 合算したい控除相殺時間のList
	 * @return
	 */
	public DeductionOffSetTime sumDeductionOffSetTime(
			List<DeductionOffSetTime> deductionOffSetTimeList
			) {		
		DeductionOffSetTime totalDeductionOffSetTime = new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
		AttendanceTime totalAnnualLeave = new AttendanceTime(0);
		AttendanceTime totalRetentionYearly = new AttendanceTime(0);
		AttendanceTime totalSpecialHoliday = new AttendanceTime(0);
		AttendanceTime totalCompensatoryLeave = new AttendanceTime(0);
		for(DeductionOffSetTime deductionOffSetTime : deductionOffSetTimeList) {
			totalAnnualLeave = new AttendanceTime(
					totalDeductionOffSetTime.getAnnualLeave().valueAsMinutes() + deductionOffSetTime.getAnnualLeave().valueAsMinutes());
			totalRetentionYearly = new AttendanceTime(
					totalDeductionOffSetTime.getRetentionYearly().valueAsMinutes() + deductionOffSetTime.getRetentionYearly().valueAsMinutes());
			totalSpecialHoliday = new AttendanceTime(
					totalDeductionOffSetTime.getSpecialHoliday().valueAsMinutes() + deductionOffSetTime.getSpecialHoliday().valueAsMinutes());
			totalCompensatoryLeave = new AttendanceTime(
					totalDeductionOffSetTime.getCompensatoryLeave().valueAsMinutes() + deductionOffSetTime.getCompensatoryLeave().valueAsMinutes());	
		}
		totalDeductionOffSetTime = new DeductionOffSetTime(totalAnnualLeave, totalRetentionYearly, totalSpecialHoliday, totalCompensatoryLeave);
		return totalDeductionOffSetTime;
	}
	
}
