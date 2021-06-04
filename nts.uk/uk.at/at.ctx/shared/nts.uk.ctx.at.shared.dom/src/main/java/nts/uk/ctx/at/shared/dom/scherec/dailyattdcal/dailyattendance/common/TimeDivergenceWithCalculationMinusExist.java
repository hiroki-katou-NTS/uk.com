package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * 計算乖離付き時間(マイナス付き)
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class TimeDivergenceWithCalculationMinusExist implements Cloneable{
	//時間
	private AttendanceTimeOfExistMinus time;
	//計算時間
	private AttendanceTimeOfExistMinus calcTime;
	//乖離時間
	private AttendanceTimeOfExistMinus divergenceTime;
	
	private TimeDivergenceWithCalculationMinusExist(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		this.time = time==null?new AttendanceTimeOfExistMinus(0):time;
		this.calcTime = calcTime==null?new AttendanceTimeOfExistMinus(0):calcTime;
		this.calcDiv();
	}
	
	/**
	 * 乖離計算
	 */
	private void calcDiv(){
		this.divergenceTime = this.calcTime.minusMinutes(this.time.valueAsMinutes());
	}
	
	/**
	 * 時間、計算時間が同じ計算付き時間帯を作成する
	 * @return 計算乖離付き時間
	 */
	public static TimeDivergenceWithCalculationMinusExist sameTime(AttendanceTimeOfExistMinus time) {
		return new TimeDivergenceWithCalculationMinusExist(time,time);
	}
	
	/**
	 * 指定された時間で計算付き時間を作成する
	 * @return 計算乖離付き時間
	 */
	public static TimeDivergenceWithCalculationMinusExist createTimeWithCalculation(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		return new TimeDivergenceWithCalculationMinusExist(time,calcTime);
	}
	
	/**
	 * 自身の乖離時間を計算する
	 * @return 計算後の計算乖離付き時間
	 */
	public TimeDivergenceWithCalculationMinusExist calcDiverGenceTime() {
		return new TimeDivergenceWithCalculationMinusExist(this.time,this.calcTime);
	}
	
	/**
	 * 時間を入れ替える(乖離計算有)
	 * @param time 時間
	 */
	public void replaceTimeAndCalcDiv(AttendanceTimeOfExistMinus time) {
		this.time = time;
		this.calcDiv();
	}
	
	/**
	 * 計算時間を入れ替える(乖離計算有)
	 * @param calcTime 計算時間
	 */
	public void replaceCalcTimeAndCalcDiv(AttendanceTimeOfExistMinus calcTime) {
		this.calcTime = calcTime;
		this.calcDiv();
	}
	
	@Override
	public TimeDivergenceWithCalculationMinusExist clone() {
		return new TimeDivergenceWithCalculationMinusExist(new AttendanceTimeOfExistMinus(time.v()),
				new AttendanceTimeOfExistMinus(calcTime.v()), new AttendanceTimeOfExistMinus(divergenceTime.v()));
	}
}
