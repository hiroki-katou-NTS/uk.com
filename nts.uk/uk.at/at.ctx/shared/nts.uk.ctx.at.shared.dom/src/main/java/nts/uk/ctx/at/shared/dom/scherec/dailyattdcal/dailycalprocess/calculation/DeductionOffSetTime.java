package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 控除相殺時間
 * @author ken_takasu
 *
 */
@Getter
@NoArgsConstructor
public class DeductionOffSetTime  implements Cloneable {
	//年休
	private AttendanceTime annualLeave;
	//超過有休
	private AttendanceTime sixtyHourHoliday;
	//特別休暇
	private AttendanceTime SpecialHoliday;
	//代休
	private AttendanceTime CompensatoryLeave;

	/**
	 * Constructor 
	 */
	public DeductionOffSetTime(AttendanceTime annualLeave, AttendanceTime sixtyHourHoliday,
			AttendanceTime specialHoliday, AttendanceTime compensatoryLeave) {
		super();
		this.annualLeave = annualLeave;
		this.sixtyHourHoliday = sixtyHourHoliday;
		SpecialHoliday = specialHoliday;
		CompensatoryLeave = compensatoryLeave;
	}
	
	/**
	 * 全て0のインスタンスを生成
	 * @return DeductionOffSetTime （全て０）
	 */
	public static DeductionOffSetTime createAllZero() {
		return new DeductionOffSetTime(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO);
	}
	
	/**
	 * 作る
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @param useTime 日別実績の時間休暇使用時間
	 * @param remainingTime	 時間休暇使用残時間
	 */
	public static DeductionOffSetTime create(CompanyHolidayPriorityOrder priorityOrder, TimevacationUseTimeOfDaily useTime, AttendanceTime remainingTime) {
		int annualHoliday = 0;
		int subHoliday = 0;
		int sixtyhourHoliday = 0;
		int specialHoliday = 0;
		
		// 時間休暇の優先順に処理
		for (HolidayPriorityOrder holiday : priorityOrder.getHolidayPriorityOrders()) {
			if(remainingTime.lessThanOrEqualTo(AttendanceTime.ZERO)) {
				break;
			}
			switch (holiday) {
			case ANNUAL_HOLIDAY:
				annualHoliday = Math.min(useTime.getTimeAnnualLeaveUseTime().valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(annualHoliday);
				break;
			case SUB_HOLIDAY:
				subHoliday = Math.min(useTime.getTimeCompensatoryLeaveUseTime().valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(subHoliday);
				break;
			case SIXTYHOUR_HOLIDAY:
				sixtyhourHoliday = Math.min(useTime.getSixtyHourExcessHolidayUseTime().valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(sixtyhourHoliday);
				break;
			case SPECIAL_HOLIDAY:
				specialHoliday = Math.min(useTime.getTimeSpecialHolidayUseTime().valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(specialHoliday);
				break;
			}
		}
		return new DeductionOffSetTime(
				new AttendanceTime(annualHoliday),
				new AttendanceTime(subHoliday),
				new AttendanceTime(sixtyhourHoliday),
				new AttendanceTime(specialHoliday));
	}
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.sixtyHourHoliday.valueAsMinutes()+this.SpecialHoliday.valueAsMinutes()+this.CompensatoryLeave.valueAsMinutes();
		return totalTime;
	}
	
	public DeductionOffSetTime clone() {
		DeductionOffSetTime clone = new DeductionOffSetTime();
		try {
			clone.annualLeave = new AttendanceTime(this.annualLeave.valueAsMinutes());
			clone.sixtyHourHoliday = new AttendanceTime(this.sixtyHourHoliday.valueAsMinutes());
			clone.SpecialHoliday = new AttendanceTime(this.SpecialHoliday.valueAsMinutes());
			clone.CompensatoryLeave = new AttendanceTime(this.CompensatoryLeave.valueAsMinutes());
		}
		catch (Exception e) {
			throw new RuntimeException("DeductionOffSetTime clone error.");
		}
		return clone;
	}
}
