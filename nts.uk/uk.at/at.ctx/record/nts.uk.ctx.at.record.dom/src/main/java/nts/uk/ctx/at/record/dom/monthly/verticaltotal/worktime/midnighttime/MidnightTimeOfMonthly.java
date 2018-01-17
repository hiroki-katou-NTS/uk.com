package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;

/**
 * 月別実績の深夜時間
 * @author shuichu_ishida
 */
@Getter
public class MidnightTimeOfMonthly {

	/** 残業深夜時間 */
	private TimeMonthWithCalculation overWorkMidnightTime;
	/** 祝日休出深夜時間 */
	private TimeMonthWithCalculation specialHolidayWorkMidnightTime;
	/** 法定内深夜時間 */
	private TimeMonthWithCalculation legalMidnightTime;
	/** 法定内休出深夜時間 */
	private TimeMonthWithCalculation legalHolidayWorkMidnightTime;
	/** 法定外深夜時間 */
	private IllegalMidnightTime illegalMidnightTime;
	/** 法定外休出深夜時間 */
	private TimeMonthWithCalculation illegalHolidayWorkMidnightTime;
	
	/**
	 * コンストラクタ
	 */
	public MidnightTimeOfMonthly(){
		
		this.overWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.specialHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalMidnightTime = new IllegalMidnightTime();
		this.illegalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param overWorkMidnightTime 残業深夜時間
	 * @param specialHolidayWorkMidnightTime 祝日休出深夜時間
	 * @param legalMidnightTime 法定内深夜時間
	 * @param legalHolidayWorkMidnightTime 法定内休出深夜時間
	 * @param illegalMidnightTime 法定外深夜時間
	 * @param illegalHolidayWorkMidnightTime 法定外休出深夜時間
	 * @return 月別実績の深夜時間
	 */
	public static MidnightTimeOfMonthly of(
			TimeMonthWithCalculation overWorkMidnightTime,
			TimeMonthWithCalculation specialHolidayWorkMidnightTime,
			TimeMonthWithCalculation legalMidnightTime,
			TimeMonthWithCalculation legalHolidayWorkMidnightTime,
			IllegalMidnightTime illegalMidnightTime,
			TimeMonthWithCalculation illegalHolidayWorkMidnightTime){
		
		val domain = new MidnightTimeOfMonthly();
		domain.overWorkMidnightTime = overWorkMidnightTime;
		domain.specialHolidayWorkMidnightTime = specialHolidayWorkMidnightTime;
		domain.legalMidnightTime = legalMidnightTime;
		domain.legalHolidayWorkMidnightTime = legalHolidayWorkMidnightTime;
		domain.illegalMidnightTime = illegalMidnightTime;
		domain.illegalHolidayWorkMidnightTime = illegalHolidayWorkMidnightTime;
		return domain;
	}
}
