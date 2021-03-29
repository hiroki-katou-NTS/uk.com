package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workingstyle.flex.FlexCalcMethodOfHalfWork;

/**
 * 控除時間
 * @author keisuke_hoshina
 *
 */
@Value
public class DeductionTime {
	//休日
	private AttendanceTime forHolidayTime;
	//代休
	private AttendanceTime forCompensatoryHolidayTime;
	
	/**
	 * 控除時間用の実働時間を計算する
	 * @param flexCalcMethod 半日勤務のフレックス計算方法
	 * @return 控除時間
	 */
	public AttendanceTime forLackCalcPredetermineDeduction(FlexCalcMethodOfHalfWork flexCalcMethod) {
		return new AttendanceTime(decisionFlexCalcMethod(flexCalcMethod.getHalfHoliday().getCalcLack(), forHolidayTime).valueAsMinutes() + decisionFlexCalcMethod(flexCalcMethod.getHalfCompensatoryHoliday().getCalcLack(), forCompensatoryHolidayTime).valueAsMinutes());
	}
	
	/**
	 * 控除時間用の割増時間を計算する
	 * @param flexCalcMethod 半日勤務のフレックス計算方法
	 * @return　控除時間
	 */
	public AttendanceTime forPremiumCalcPredetermineDeduction(FlexCalcMethodOfHalfWork flexCalcMethod) {
		return new AttendanceTime(decisionFlexCalcMethod(flexCalcMethod.getHalfHoliday().getCalcPremium(), forHolidayTime).valueAsMinutes() + decisionFlexCalcMethod(flexCalcMethod.getHalfCompensatoryHoliday().getCalcPremium(), forCompensatoryHolidayTime).valueAsMinutes());
	}
	
	/**
	 * 控除時間に加算する時間を判定する
	 * @param flexCalcMethod　フレックス計算方法
	 * @param time　控除時間
	 * @return　加算する控除時間
	 */
	private AttendanceTime decisionFlexCalcMethod(FlexCalcMethod flexCalcMethod,AttendanceTime time) {
		return (flexCalcMethod.isHalf())?time : new AttendanceTime(0);
	}
}
