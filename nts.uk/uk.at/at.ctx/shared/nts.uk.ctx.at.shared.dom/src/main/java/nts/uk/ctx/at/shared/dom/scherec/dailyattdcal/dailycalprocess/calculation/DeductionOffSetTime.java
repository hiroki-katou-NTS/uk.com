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
		AttendanceTime annualHoliday = AttendanceTime.ZERO;
		AttendanceTime subHoliday = AttendanceTime.ZERO;
		AttendanceTime sixtyhourHoliday = AttendanceTime.ZERO;
		AttendanceTime specialHoliday = AttendanceTime.ZERO;
		
		// 時間休暇の優先順に処理
		for (HolidayPriorityOrder holiday : priorityOrder.getHolidayPriorityOrders()) {
			switch (holiday) {
			case ANNUAL_HOLIDAY:
				annualHoliday = calcOffsetTime(useTime.getTimeAnnualLeaveUseTime(), remainingTime);
				remainingTime = remainingTime.minusMinutes(annualHoliday.valueAsMinutes());
				break;
			case SUB_HOLIDAY:
				subHoliday = calcOffsetTime(useTime.getTimeCompensatoryLeaveUseTime(), remainingTime);
				remainingTime = remainingTime.minusMinutes(subHoliday.valueAsMinutes());
				break;
			case SIXTYHOUR_HOLIDAY:
				sixtyhourHoliday = calcOffsetTime(useTime.getSixtyHourExcessHolidayUseTime(), remainingTime);
				remainingTime = remainingTime.minusMinutes(sixtyhourHoliday.valueAsMinutes());
				break;
			case SPECIAL_HOLIDAY:
				specialHoliday = calcOffsetTime(useTime.getTimeSpecialHolidayUseTime(), remainingTime);
				remainingTime = remainingTime.minusMinutes(specialHoliday.valueAsMinutes());
				break;
			}
		}
		return new DeductionOffSetTime(annualHoliday, subHoliday, sixtyhourHoliday, specialHoliday);
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
	
	/**
	 * 相殺する時間を計算（比較）する
	 * @param timeVacationUseTime 時間休暇使用時間
	 * @param remainingTime  遅刻 or 早退 or 外出 の残時間
	 * @return 相殺する時間
	 */
	private static AttendanceTime calcOffsetTime(AttendanceTime timeVacationUseTime, AttendanceTime remainingTime) {
		int offSetTime;
		if (timeVacationUseTime.lessThanOrEqualTo(remainingTime)) {
			offSetTime = timeVacationUseTime.valueAsMinutes();
		} else {
			offSetTime = remainingTime.valueAsMinutes();
		}
		return new AttendanceTime(offSetTime);
	}
}
