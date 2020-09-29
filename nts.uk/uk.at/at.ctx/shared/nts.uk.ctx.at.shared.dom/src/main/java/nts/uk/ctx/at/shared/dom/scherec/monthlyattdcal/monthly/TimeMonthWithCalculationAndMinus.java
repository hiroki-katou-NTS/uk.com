package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

/**
 * 計算付き月間時間（マイナス有り）
 * @author shuichi_ishida
 */
@Getter
@Setter
public class TimeMonthWithCalculationAndMinus {

	/** 時間 */
	private AttendanceTimeMonthWithMinus time;
	/** 計算期間 */
	private AttendanceTimeMonthWithMinus calcTime;

	/**
	 * コンストラクタ
	 */
	public TimeMonthWithCalculationAndMinus(){
		this.time = new AttendanceTimeMonthWithMinus(0);
		this.calcTime = new AttendanceTimeMonthWithMinus(0);
	}
	
	/**
	 * コンストラクタ
	 * @param time 時間
	 * @param calcTime 計算時間
	 */
	public TimeMonthWithCalculationAndMinus(AttendanceTimeMonthWithMinus time, AttendanceTimeMonthWithMinus calcTime) {
		super();
		this.time = time;
		this.calcTime = calcTime;
	}

	/**
	 * 時間と計算時間を同じ時間で作成
	 * @param minutes 時間（分）
	 * @return 計算付き月間時間
	 */
	public static TimeMonthWithCalculationAndMinus ofSameTime(int minutes){
		return new TimeMonthWithCalculationAndMinus(
				new AttendanceTimeMonthWithMinus(minutes),
				new AttendanceTimeMonthWithMinus(minutes)
			);
	}

	/**
	 * 時間と計算時間に同じ時間を加算する
	 * @param minutes 加算する時間（分）
	 * @return 計算付き月間時間
	 */
	public TimeMonthWithCalculationAndMinus addSameTime(int minutes){
		return new TimeMonthWithCalculationAndMinus(
				this.time.addMinutes(minutes),
				this.calcTime.addMinutes(minutes)
			);
	}
	
	/**
	 * 分を加算する
	 * @param minutes 時間（分）
	 * @param calcMinutes 計算時間（分）
	 * @return 計算付き月間時間
	 */
	public TimeMonthWithCalculationAndMinus addMinutes(int minutes, int calcMinutes){
		return new TimeMonthWithCalculationAndMinus(
				this.time.addMinutes(minutes),
				this.calcTime.addMinutes(calcMinutes)
			);
	}
}
