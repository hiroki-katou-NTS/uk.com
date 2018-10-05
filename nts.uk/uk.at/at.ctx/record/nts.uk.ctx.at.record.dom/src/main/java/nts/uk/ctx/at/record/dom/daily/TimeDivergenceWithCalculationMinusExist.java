package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * 計算乖離付き時間(マイナス付き)
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeDivergenceWithCalculationMinusExist {
	//時間
	@Setter
	private AttendanceTimeOfExistMinus time;
	//計算時間
	private AttendanceTimeOfExistMinus calcTime;
	//乖離時間
	private AttendanceTimeOfExistMinus divergenceTime;
	
	private TimeDivergenceWithCalculationMinusExist(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		this.time = time==null?new AttendanceTimeOfExistMinus(0):time;
		this.calcTime = calcTime==null?new AttendanceTimeOfExistMinus(0):calcTime;
		this.divergenceTime = this.time.minusMinutes(this.calcTime.valueAsMinutes());
		if(this.divergenceTime.valueAsMinutes()<0) {
			this.divergenceTime = new AttendanceTimeOfExistMinus(0);
		}
	}
	
	/**
	 * 時間、計算時間が同じ計算付き時間帯を作成する
	 * @return
	 */
	public static TimeDivergenceWithCalculationMinusExist sameTime(AttendanceTimeOfExistMinus time) {
		return new TimeDivergenceWithCalculationMinusExist(time,time);
	}
	
	
	/**
	 * 指定された時間で計算付き時間を作成する
	 * @return
	 */
	public static TimeDivergenceWithCalculationMinusExist createTimeWithCalculation(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		return new TimeDivergenceWithCalculationMinusExist(time,calcTime);
		
	}
	
	/**
	 * 自身の乖離時間を計算する
	 * @return
	 */
	public TimeDivergenceWithCalculationMinusExist calcDiverGenceTime() {
		return new TimeDivergenceWithCalculationMinusExist(this.time,this.calcTime);
	}
	
	public void replaceTime(AttendanceTimeOfExistMinus time) {
		this.time = time;
	}
	
	public void replaceCalcTime(AttendanceTimeOfExistMinus calcTime) {
		this.calcTime = calcTime;
	}
	
	/**
	 * 計算時間のみを入れ替える(乖離計算有)
	 * @param calcTime
	 * @return
	 */
	public void replaceTimeAndCalcDiv(AttendanceTimeOfExistMinus calcTime) {
		this.calcTime = calcTime;
		this.divergenceTime = this.time.minusMinutes(calcTime.valueAsMinutes());
	}
	
}
