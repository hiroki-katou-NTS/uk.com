package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * 計算付き時間(マイナス有)
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeWithCalculationMinusExist {
	//時間
	@Setter
	private AttendanceTimeOfExistMinus time;
	//計算時間
	private AttendanceTimeOfExistMinus calcTime;
	
	private TimeWithCalculationMinusExist(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		this.time = time;
		this.calcTime = calcTime;
	}
	
	/**
	 * 時間、計算時間が同じ計算付き時間帯を作成する
	 * @return
	 */
	public static TimeWithCalculationMinusExist sameTime(AttendanceTimeOfExistMinus time) {
		return new TimeWithCalculationMinusExist(time,time);
	}
	
	
	/**
	 * 指定された時間で計算付き時間を作成する
	 * @return
	 */
	public static TimeWithCalculationMinusExist createTimeWithCalculation(AttendanceTimeOfExistMinus time,AttendanceTimeOfExistMinus calcTime) {
		return new TimeWithCalculationMinusExist(time,calcTime);
		
	}
	
}
