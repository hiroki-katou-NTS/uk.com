package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * フレックス勤務の非勤務日の場合の計算方法
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
public enum CalcMethodOfNoWorkingDayForCalc {
	isCalculateFlexTime(1),    //フレックス時間を計算する
	isNotCalculateFlexTime(0); //フレックス時間を計算しない
	
	public final int value;
	
	/**
	 * フレックス時間を計算するか判定する
	 * @return　フレックス時間をする
	 */
	public boolean isCalclateFlexTime() {
		return isCalculateFlexTime.equals(this);
	}
	
	public static CalcMethodOfNoWorkingDayForCalc of(CalcMethodOfNoWorkingDay calcMethodOfNoWorkingDay) {
		return EnumAdaptor.valueOf(calcMethodOfNoWorkingDay.value, CalcMethodOfNoWorkingDayForCalc.class);
	}
}
