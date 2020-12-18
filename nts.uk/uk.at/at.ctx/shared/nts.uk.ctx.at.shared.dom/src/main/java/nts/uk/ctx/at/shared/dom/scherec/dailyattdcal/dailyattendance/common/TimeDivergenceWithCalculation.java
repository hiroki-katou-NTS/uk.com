package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 計算乖離付き時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeDivergenceWithCalculation {
	//時間
	@Setter
	private AttendanceTime time;
	//計算時間
	private AttendanceTime calcTime;
	//乖離時間
	private AttendanceTime divergenceTime;
	
	
	private TimeDivergenceWithCalculation(AttendanceTime time,AttendanceTime calcTime) {
		this.time = time==null?new AttendanceTime(0):time;
		this.calcTime = calcTime==null?new AttendanceTime(0):calcTime;
		this.divergenceTime = this.time.minusMinutes(this.calcTime.valueAsMinutes());
		// 大塚モード時の仕様のため、業務処理側で個別に判断する(2020/11/9 shuichi_ishida)
//		if(this.divergenceTime.valueAsMinutes()<0) {
//			this.divergenceTime = new AttendanceTime(0);
//		}
	}
	
	/**
	 * 時間、計算時間が同じ計算乖離付き時間帯を作成する
	 * @return 計算付き時間
	 */
	public static TimeDivergenceWithCalculation sameTime(AttendanceTime time) {
		return new TimeDivergenceWithCalculation(time,time);
	}
	
	/**
	 * 指定された時間で計算乖離付き時間を作成する
	 * @return 計算付き時間
	 */
	public static TimeDivergenceWithCalculation createTimeWithCalculation(AttendanceTime time,AttendanceTime calcTime) {
		return new TimeDivergenceWithCalculation(time,calcTime);
		
	}
	
	public static TimeDivergenceWithCalculation defaultValue() {
		return new TimeDivergenceWithCalculation(AttendanceTime.ZERO, AttendanceTime.ZERO);
		
	}
	
	/**
	 * 時間のみを入れ替える(乖離計算無し)
	 * @param time
	 * @return
	 */
	public void replaceTime(AttendanceTime time) {
		this.time = time;
	}
	
	/**
	 * 計算時間のみを入れ替える(乖離計算無し)
	 * @param calcTime
	 * @return
	 */
	public void replaceCalcTime(AttendanceTime calcTime) {
		this.calcTime = calcTime;
	}
	
	/**
	 * 時間のみを入れ替える(乖離計算あり)
	 * @param time 入れ替える時間
	 */
	public void replaceTimeWithCalc(AttendanceTime time) {
		this.time = time;
		this.divergenceTime = this.time.minusMinutes(calcTime.valueAsMinutes());
	}
	
	/**
	 * 計算時間のみを入れ替える(乖離計算有)
	 * @param calcTime
	 * @return
	 */
	public void replaceTimeAndCalcDiv(AttendanceTime calcTime) {
		this.calcTime = calcTime;
		this.divergenceTime = this.time.minusMinutes(calcTime.valueAsMinutes());
	}
	
	public static TimeDivergenceWithCalculation emptyTime() {
		return TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0));
		
	}
	
	/**
	 * 時間を加算する（返り値なし）
	 * @param time 時間
	 * @param calcTime 計算時間
	 */
	public void addMinutesNotReturn(AttendanceTime time,AttendanceTime calcTime) {
		this.time = this.time.addMinutes(time.valueAsMinutes());
		this.calcTime = this.calcTime.addMinutes(calcTime.valueAsMinutes());
		this.divergenceTime = calcTime.minusMinutes(time.valueAsMinutes());
	}
	
	/**
	 * 受け取った時間を今持っている時間に加算する
	 * @param time 時間
	 * @param calcTime 計算時間
	 * @return　加算後の計算付き時間
	 */
	public TimeDivergenceWithCalculation addMinutes(AttendanceTime time,AttendanceTime calcTime) {
		return new TimeDivergenceWithCalculation(this.time.addMinutes(time.valueAsMinutes()),this.calcTime.addMinutes(calcTime.valueAsMinutes()));
	}
	
	
	/**
	 * 自身の乖離時間を計算する
	 * @return
	 */
	public TimeDivergenceWithCalculation calcDiverGenceTime() {
		return new TimeDivergenceWithCalculation(this.time,this.calcTime);
	}

	public TimeDivergenceWithCalculation(AttendanceTime time, AttendanceTime calcTime, AttendanceTime divergenceTime) {
		super();
		this.time = time;
		this.calcTime = calcTime;
		this.divergenceTime = divergenceTime;
	}
	
	/**
	 * マイナスの乖離時間を0にする
	 */
	public void divergenceMinusValueToZero(){
		if (this.divergenceTime.valueAsMinutes() < 0){
			this.divergenceTime = new AttendanceTime(0);
		}
	}
}
