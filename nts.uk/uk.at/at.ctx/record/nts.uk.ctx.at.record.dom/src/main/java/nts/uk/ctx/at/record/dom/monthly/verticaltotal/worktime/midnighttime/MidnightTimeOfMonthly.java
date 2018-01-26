package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;

/**
 * 月別実績の深夜時間
 * @author shuichu_ishida
 */
@Getter
public class MidnightTimeOfMonthly {

	/** 残業深夜時間 */
	private TimeMonthWithCalculation overWorkMidnightTime;
	/** 法定内深夜時間 */
	private TimeMonthWithCalculation legalMidnightTime;
	/** 法定外深夜時間 */
	private IllegalMidnightTime illegalMidnightTime;
	/** 法定内休出深夜時間 */
	private TimeMonthWithCalculation legalHolidayWorkMidnightTime;
	/** 法定外休出深夜時間 */
	private TimeMonthWithCalculation illegalHolidayWorkMidnightTime;
	/** 祝日休出深夜時間 */
	private TimeMonthWithCalculation specialHolidayWorkMidnightTime;
	
	/**
	 * コンストラクタ
	 */
	public MidnightTimeOfMonthly(){
		
		this.overWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalMidnightTime = new IllegalMidnightTime();
		this.legalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.specialHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param overWorkMidnightTime 残業深夜時間
	 * @param legalMidnightTime 法定内深夜時間
	 * @param illegalMidnightTime 法定外深夜時間
	 * @param legalHolidayWorkMidnightTime 法定内休出深夜時間
	 * @param illegalHolidayWorkMidnightTime 法定外休出深夜時間
	 * @param specialHolidayWorkMidnightTime 祝日休出深夜時間
	 * @return 月別実績の深夜時間
	 */
	public static MidnightTimeOfMonthly of(
			TimeMonthWithCalculation overWorkMidnightTime,
			TimeMonthWithCalculation legalMidnightTime,
			IllegalMidnightTime illegalMidnightTime,
			TimeMonthWithCalculation legalHolidayWorkMidnightTime,
			TimeMonthWithCalculation illegalHolidayWorkMidnightTime,
			TimeMonthWithCalculation specialHolidayWorkMidnightTime){
		
		val domain = new MidnightTimeOfMonthly();
		domain.overWorkMidnightTime = overWorkMidnightTime;
		domain.legalMidnightTime = legalMidnightTime;
		domain.illegalMidnightTime = illegalMidnightTime;
		domain.legalHolidayWorkMidnightTime = legalHolidayWorkMidnightTime;
		domain.illegalHolidayWorkMidnightTime = illegalHolidayWorkMidnightTime;
		domain.specialHolidayWorkMidnightTime = specialHolidayWorkMidnightTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.overWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.legalMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalMidnightTime = new IllegalMidnightTime();
		this.legalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.illegalHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);
		this.specialHolidayWorkMidnightTime = TimeMonthWithCalculation.ofSameTime(0);

		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
			val legalTime = totalWorkingTime.getWithinStatutoryTimeOfDaily();
			val illegalTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
			//*****（未）　日別実績の休出時間の実装待ち。→　祝日休出深夜時間に集計。
			
			// 日別実績の「深夜時間」を集計する
			val legalMidnightTime = legalTime.getWithinStatutoryMidNightTime().getTime();
			this.legalMidnightTime = this.legalMidnightTime.addMinutes(
					legalMidnightTime.getTime().v(), legalMidnightTime.getCalcTime().v());
			val illegalMidnightTime = illegalTime.getExcessOfStatutoryMidNightTime();
			this.illegalMidnightTime.addMinutesToTime(
					illegalMidnightTime.getTime().getTime().v(), illegalMidnightTime.getTime().getCalcTime().v());
			this.illegalMidnightTime.addMinutesToBeforeTime(
					illegalMidnightTime.getBeforeApplicationTime().v());
			if (illegalTime.getOverTimeWork().isPresent()){
				val overTime = illegalTime.getOverTimeWork().get();
				val overWorkMidnightTime = overTime.getExcessOverTimeWorkMidNightTime().get().getTime();
				this.overWorkMidnightTime = this.overWorkMidnightTime.addMinutes(
						overWorkMidnightTime.getTime().v(), overWorkMidnightTime.getCalcTime().v());
				//*****（未）　残業深夜時間の集計先として、所定外深夜時間のどの項目にするか確認要。
			}
			//*****（未）　休出深夜時間から、祝日休出深夜時間へ集計する。日別側のクラス実装待ち。
		}
	}
}
