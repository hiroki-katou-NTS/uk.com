package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;

/**
 * 集計外出
 * @author shuichu_ishida
 */
@Getter
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
	public AggregateGoOut(){
		
		this.goOutReason = GoingOutReason.PRIVATE;
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
		
		val domain = new AggregateGoOut();
		domain.goOutReason = goOutReason;
		domain.times = times;
		domain.legalTime = legalTime;
		domain.illegalTime = illegalTime;
		domain.totalTime = totalTime;
		return domain;
	}
}
