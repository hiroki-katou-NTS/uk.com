package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 計算付き時間
 * @author keisuke_hoshina
 *
 */
@Value
public class TimeWithCalculation {
	private AttendanceTime time;
	private AttendanceTime calcTime;
	
	private TimeWithCalculation(AttendanceTime time,AttendanceTime calcTime) {
		this.time = time;
		this.calcTime = calcTime;
	}
	
	/**
	 * 時間、計算時間が同じ計算付き時間帯を作成する
	 * @return
	 */
	public static TimeWithCalculation sameTime(AttendanceTime time) {
		return new TimeWithCalculation(time,time);
	}
	
	/**
	 * 時間、計算時間が異なる計算付き時間帯を作成する
	 * @param time 時間
	 * @param calcTime　計算時間
	 * @return
	 */
	public static TimeWithCalculation of(AttendanceTime time, AttendanceTime calcTime) {
		return new TimeWithCalculation(time,calcTime);
	}
}
