package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.SpecialHolidayUseTimeOfTimeSeries;

/**
 * 月別実績の特別休暇使用時間
 * @author shuichi_ishida
 *
 */
@Getter
public class SpecialHolidayUseTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 使用時間 */
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	private Map<GeneralDate, SpecialHolidayUseTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public SpecialHolidayUseTimeOfMonthly(){

		this.useTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の特別休暇使用時間
	 */
	public static SpecialHolidayUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		val domain = new SpecialHolidayUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
		
	}

	@Override
	public SpecialHolidayUseTimeOfMonthly clone() {
		SpecialHolidayUseTimeOfMonthly cloned = new SpecialHolidayUseTimeOfMonthly();
		try {
			cloned.useTime = new AttendanceTimeMonth(this.useTime.v());
			// ※　Shallow Copy.
			cloned.timeSeriesWorks = this.timeSeriesWorks;
		}
		catch (Exception e){
			throw new RuntimeException("SpecialHolidayUseTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 特別休暇使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void confirm(DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap){

		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.entrySet()) {
			val ymd = attendanceTimeOfDaily.getKey();
			
			// 期間外はスキップする
			if (!datePeriod.contains(ymd)) continue;
			
			// 「日別実績の特別休暇」を取得する
			val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getValue().getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			if (totalWorkingTime.getHolidayOfDaily() == null) return;
			val holidayOfDaily = totalWorkingTime.getHolidayOfDaily();
			if (holidayOfDaily.getSpecialHoliday() == null) return;
			val specialHoliday = holidayOfDaily.getSpecialHoliday();
			
			// 取得した使用時間を「月別実績の特別休暇使用時間」に入れる
			val specialHolidayUseTime = SpecialHolidayUseTimeOfTimeSeries.of(ymd, specialHoliday);
			this.timeSeriesWorks.putIfAbsent(ymd, specialHolidayUseTime);
		}
	}
	
	/**
	 * 特別休暇使用時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			this.addMinuteToUseTime(timeSeriesWork.getSpecialHolidayUseTime().getUseTime().v());
		}
	}
	
	/**
	 * 特別休暇使用時間を求める
	 * @param datePeriod 期間
	 * @return 特別休暇使用時間
	 */
	public AttendanceTimeMonth getTotalUseTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			returnTime = returnTime.addMinutes(timeSeriesWork.getSpecialHolidayUseTime().getUseTime().v());
		}
		return returnTime;
	}
	
	/**
	 * 使用時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinuteToUseTime(int minutes){
		this.useTime = this.useTime.addMinutes(minutes);
	}
}
