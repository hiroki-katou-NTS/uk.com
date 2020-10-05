package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.CompensatoryLeaveUseTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 月別実績の代休使用時間
 * @author shuichi_ishida
 */
@Getter
public class CompensatoryLeaveUseTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 使用時間 */
	private AttendanceTimeMonth useTime;
	/** 時系列ワーク */
	private Map<GeneralDate, CompensatoryLeaveUseTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public CompensatoryLeaveUseTimeOfMonthly(){
		
		this.useTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param useTime 使用時間
	 * @return 月別実績の代休使用時間
	 */
	public static CompensatoryLeaveUseTimeOfMonthly of(
			AttendanceTimeMonth useTime){
		
		val domain = new CompensatoryLeaveUseTimeOfMonthly();
		domain.useTime = useTime;
		return domain;
	}
	
	@Override
	public CompensatoryLeaveUseTimeOfMonthly clone() {
		CompensatoryLeaveUseTimeOfMonthly cloned = new CompensatoryLeaveUseTimeOfMonthly();
		try {
			cloned.useTime = new AttendanceTimeMonth(this.useTime.v());
			// ※　Shallow Copy.
			cloned.timeSeriesWorks = this.timeSeriesWorks;
		}
		catch (Exception e){
			throw new RuntimeException("CompensatoryLeaveUseTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 代休使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 */
	public void confirm(RequireM1 require, DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailyMap){

		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.entrySet()) {
			val ymd = attendanceTimeOfDaily.getKey();
			
			// 期間外はスキップする
			if (!datePeriod.contains(ymd)) continue;
			
			// 「日別実績の代休」を取得する
			val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getValue().getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			if (totalWorkingTime.getHolidayOfDaily() == null) return;
			val holidayOfDaily = totalWorkingTime.getHolidayOfDaily();
			if (holidayOfDaily.getSubstitute() == null) return;
			val substitute = holidayOfDaily.getSubstitute();
			
			// 期間中の勤務種類を取得する
			String workTypeCode = null;
			if (workInfoOfDailyMap.containsKey(ymd)) {
				val workInfo = workInfoOfDailyMap.get(ymd);
				if (workInfo.getRecordInfo() != null) {
					if (workInfo.getRecordInfo().getWorkTypeCode() != null) {
						workTypeCode = workInfo.getRecordInfo().getWorkTypeCode().v();
					}
				}
			}
			
			// 取得した使用時間を「月別実績の代休使用時間」に入れる
			HolidayAtr holidayAtr = HolidayAtr.STATUTORY_HOLIDAYS;
			if (workTypeCode != null) {
				val workType = require.workType(AppContexts.user().companyId(), workTypeCode).orElse(null);
				if (workType != null) {
					Optional<HolidayAtr> holidayAtrOpt = workType.getHolidayAtr();
					if (holidayAtrOpt.isPresent()) holidayAtr = holidayAtrOpt.get();
				}
			}
			val compensatoryLeaveUseTime = CompensatoryLeaveUseTimeOfTimeSeries.of(ymd, substitute, holidayAtr);
			this.timeSeriesWorks.putIfAbsent(ymd, compensatoryLeaveUseTime);
		}
	}
	
	/**
	 * 代休使用時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		this.useTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			this.addMinuteToUseTime(timeSeriesWork.getSubstituteHolidayUseTime().getUseTime().v());
		}
	}
	
	/**
	 * 代休使用時間を求める
	 * @param datePeriod 期間
	 * @return 代休使用時間
	 */
	public AttendanceTimeMonth getTotalUseTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			returnTime = returnTime.addMinutes(timeSeriesWork.getSubstituteHolidayUseTime().getUseTime().v());
		}
		return returnTime;
	}
	
	/**
	 * 法定内代休時間の計算
	 * @param datePeriod 期間
	 * @return 法定内代休時間
	 */
	public AttendanceTimeMonth calcLegalTime(DatePeriod datePeriod){
		
		int legalMinutes = 0;
		for (val timeSeriesWork : this.timeSeriesWorks.values()){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			
			// 休日区分の判断
			if (timeSeriesWork.getHolidayAtr() == HolidayAtr.STATUTORY_HOLIDAYS){
				legalMinutes += timeSeriesWork.getSubstituteHolidayUseTime().getUseTime().v();
			}
		}
		return new AttendanceTimeMonth(legalMinutes);
	}
	
	/**
	 * 使用時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinuteToUseTime(int minutes){
		this.useTime = this.useTime.addMinutes(minutes);
	}
	
	public static interface RequireM1 { 

		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
}