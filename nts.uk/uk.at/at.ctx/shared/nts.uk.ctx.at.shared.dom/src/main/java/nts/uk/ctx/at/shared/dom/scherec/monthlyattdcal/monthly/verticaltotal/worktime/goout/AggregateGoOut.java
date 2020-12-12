package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * 集計外出
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
public class AggregateGoOut implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

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
	/** コアタイム外時間 */
	private TimeMonthWithCalculation coreOutTime;

	/**
	 * コンストラクタ
	 */
	public AggregateGoOut(GoingOutReason goOutReason){
		
		this.goOutReason = goOutReason;
		this.times = new AttendanceTimesMonth(0);
		this.legalTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalTime = TimeMonthWithCalculation.ofSameTime(0);
		this.totalTime = TimeMonthWithCalculation.ofSameTime(0);
		this.coreOutTime = TimeMonthWithCalculation.ofSameTime(0);
	}

	/**
	 * ファクトリー
	 * @param goOutReason 外出理由
	 * @param times 回数
	 * @param legalTime 法定内時間
	 * @param illegalTime 法定外時間
	 * @param totalTime 合計時間
	 * @param coreOutTime コアタイム外時間
	 * @return 集計外出
	 */
	public static AggregateGoOut of(
			GoingOutReason goOutReason,
			AttendanceTimesMonth times,
			TimeMonthWithCalculation legalTime,
			TimeMonthWithCalculation illegalTime,
			TimeMonthWithCalculation totalTime,
			TimeMonthWithCalculation coreOutTime){
		
		val domain = new AggregateGoOut(goOutReason);
		domain.times = times;
		domain.legalTime = legalTime;
		domain.illegalTime = illegalTime;
		domain.totalTime = totalTime;
		domain.coreOutTime = coreOutTime;
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
	/**
	 * コアタイム外時間に分を加算する
	 * @param minutes 分
	 * @param calcMinutes 分(計算用)
	 */
	public void addMinutesToCoreOutTime(int minutes, int calcMinutes){
		this.coreOutTime = this.coreOutTime.addMinutes(minutes, calcMinutes);
	}
}
