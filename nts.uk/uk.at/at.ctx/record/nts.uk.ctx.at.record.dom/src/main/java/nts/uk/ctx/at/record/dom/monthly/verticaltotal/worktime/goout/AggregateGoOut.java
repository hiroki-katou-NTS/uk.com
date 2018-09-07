package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計外出
 * @author shuichu_ishida
 */
@Getter
@NoArgsConstructor
public class AggregateGoOut {

	/** 外出理由 */
	private GoingOutReason goOutReason;
	/** 回数 */
	private AttendanceTimesMonth times;
	/** 法定内時間 */
	private TimeMonthWithCalculation legalTime;
	/** 法定外時間 */
	private TimeMonthWithCalculation illegalTime;
	/** 合計時間 */
	private TimeMonthWithCalculation totalTime;

	/**
	 * コンストラクタ
	 */
	public AggregateGoOut(GoingOutReason goOutReason){
		
		this.goOutReason = goOutReason;
		this.times = new AttendanceTimesMonth(0);
		this.legalTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalTime = TimeMonthWithCalculation.ofSameTime(0);
		this.totalTime = TimeMonthWithCalculation.ofSameTime(0);
	}

	/**
	 * ファクトリー
	 * @param goOutReason 外出理由
	 * @param times 回数
	 * @param legalTime 法定内時間
	 * @param illegalTime 法定外時間
	 * @param totalTime 合計時間
	 * @return 集計外出
	 */
	public static AggregateGoOut of(
			GoingOutReason goOutReason,
			AttendanceTimesMonth times,
			TimeMonthWithCalculation legalTime,
			TimeMonthWithCalculation illegalTime,
			TimeMonthWithCalculation totalTime){
		
		val domain = new AggregateGoOut(goOutReason);
		domain.times = times;
		domain.legalTime = legalTime;
		domain.illegalTime = illegalTime;
		domain.totalTime = totalTime;
		return domain;
	}
	/**
	 * for use merger table KrcdtMonMerge
	 * @author lanlt
	 * @param times
	 * @param legalTime
	 * @param illegalTime
	 * @param totalTime
	 * @return
	 */
	public static AggregateGoOut of(
			AttendanceTimesMonth times,
			TimeMonthWithCalculation legalTime,
			TimeMonthWithCalculation illegalTime,
			TimeMonthWithCalculation totalTime){
		
		val domain = new AggregateGoOut();
		domain.times = times;
		domain.legalTime = legalTime;
		domain.illegalTime = illegalTime;
		domain.totalTime = totalTime;
		return domain;
	}
	
	/**
	 * 回数に加算する
	 * @param times 回数
	 */
	public void addTimes(int times){
		this.times = this.times.addTimes(times);
	}
	
	/**
	 * 法定内時間に分を加算する
	 * @param minutes 分
	 * @param calcMinutes 分(計算用)
	 */
	public void addMinutesToLegalTime(int minutes, int calcMinutes){
		this.legalTime = this.legalTime.addMinutes(minutes, calcMinutes);
	}
	
	/**
	 * 法定外時間に分を加算する
	 * @param minutes 分
	 * @param calcMinutes 分(計算用)
	 */
	public void addMinutesToIllegalTime(int minutes, int calcMinutes){
		this.illegalTime = this.illegalTime.addMinutes(minutes, calcMinutes);
	}
	
	/**
	 * 合計時間に分を加算する
	 * @param minutes 分
	 * @param calcMinutes 分(計算用)
	 */
	public void addMinutesToTotalTime(int minutes, int calcMinutes){
		this.totalTime = this.totalTime.addMinutes(minutes, calcMinutes);
	}

	public static AggregateGoOut of(AttendanceTimeMonth attendanceTimeMonth, TimeMonthWithCalculation legalTime2,
			TimeMonthWithCalculation illegalTime2, TimeMonthWithCalculation totalTime2) {
		// TODO Auto-generated method stub
		return null;
	}
}
