package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.timeseries.PrescribedWorkingTimeOfTimeSeries;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexLegalTimeGetter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績の所定労働時間
 * @author shuichi_ishida
 */
@Getter
public class PrescribedWorkingTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 計画所定労働時間 */
	private AttendanceTimeMonth schedulePrescribedWorkingTime;
	/** 実績所定労働時間 */
	private AttendanceTimeMonth recordPrescribedWorkingTime;
	
	/** 時系列ワーク */
	private List<PrescribedWorkingTimeOfTimeSeries> timeSeriesWorks;
	
	/**
	 * コンストラクタ
	 */
	public PrescribedWorkingTimeOfMonthly(){
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.timeSeriesWorks = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param schedulePrescribedWorkingTime 計画所定労働時間
	 * @param recordPrescribedWorkingTime 実績所定労働時間
	 * @return 月別実績の所定労働時間
	 */
	public static PrescribedWorkingTimeOfMonthly of(
			AttendanceTimeMonth schedulePrescribedWorkingTime,
			AttendanceTimeMonth recordPrescribedWorkingTime){
		
		val domain = new PrescribedWorkingTimeOfMonthly();
		domain.schedulePrescribedWorkingTime = schedulePrescribedWorkingTime;
		domain.recordPrescribedWorkingTime = recordPrescribedWorkingTime;
		return domain;
	}
	
	@Override
	public PrescribedWorkingTimeOfMonthly clone() {
		PrescribedWorkingTimeOfMonthly cloned = new PrescribedWorkingTimeOfMonthly();
		try {
			cloned.schedulePrescribedWorkingTime = new AttendanceTimeMonth(this.schedulePrescribedWorkingTime.v());
			cloned.recordPrescribedWorkingTime = new AttendanceTimeMonth(this.recordPrescribedWorkingTime.v());
			// ※　Shallow Copy.
			cloned.timeSeriesWorks = timeSeriesWorks;
		}
		catch (Exception e){
			throw new RuntimeException("PrescribedWorkingTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 所定労働時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 */
	public void confirm(DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap,
			Map<GeneralDate, SnapShot> snapshots){
		
		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.entrySet()){

			// 期間外はスキップする
			if (!datePeriod.contains(attendanceTimeOfDaily.getKey())) continue;
			
			// 「日別実績の勤務予定時間」を取得する
			val workScheduleTimeOfDaily = attendanceTimeOfDaily.getValue().getWorkScheduleTimeOfDaily();
			
			val snapshot = snapshots.get(attendanceTimeOfDaily.getKey());
			val schedulePrescribedLaborTime = snapshot == null ? new AttendanceTime(0) : snapshot.getPredetermineTime();
			
			// 取得した就業時間を「月別実績の所定労働時間」に入れる
			this.timeSeriesWorks.add(PrescribedWorkingTimeOfTimeSeries.of(
					attendanceTimeOfDaily.getKey(), workScheduleTimeOfDaily,
					schedulePrescribedLaborTime));
		}
	}
	
	/**
	 * 所定労働時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		this.schedulePrescribedWorkingTime = new AttendanceTimeMonth(0);
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			this.schedulePrescribedWorkingTime = this.schedulePrescribedWorkingTime.addMinutes(
					timeSeriesWork.getSchedulePrescribedLaborTime().v());
			this.recordPrescribedWorkingTime = this.recordPrescribedWorkingTime.addMinutes(
					prescribedWorkingTime.getRecordPrescribedLaborTime().v());
		}
	}

	/**
	 * 計画所定労働合計時間を取得する
	 * @param datePeriod 期間
	 * @return 計画所定労働合計時間
	 */
	public AttendanceTimeMonth getTotalSchedulePrescribedWorkingTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			returnTime = returnTime.addMinutes(timeSeriesWork.getSchedulePrescribedLaborTime().v());
		}
		return returnTime;
	}

	/**
	 * 実績所定労働合計時間を取得する
	 * @param datePeriod 期間
	 * @return 実績所定労働合計時間
	 */
	public AttendanceTimeMonth getTotalRecordPrescribedWorkingTime(DatePeriod datePeriod){
		
		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);
		for (val timeSeriesWork : this.timeSeriesWorks){
			if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
			val prescribedWorkingTime = timeSeriesWork.getPrescribedWorkingTime();
			returnTime = returnTime.addMinutes(prescribedWorkingTime.getRecordPrescribedLaborTime().v());
		}
		return returnTime;
	}
	
	/** フレックス勤務の所定労働時間の補正 */
	public void correctInFlexWork(Require require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId,
			ClosureDate closureDate, String employmentCode,
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex,  AggregateTotalWorkingTime aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		/** 所定労動時間使用区分を確認する */
		if (!settingsByFlex.getComFlexSetOpt().map(c -> c.isWithinTimeUsageAttr()).orElse(false)) 
			return;
		
		/** 所定労働時間を求める */
		val prescribedWorkingTime = FlexLegalTimeGetter.getFlexStatutoryLaborTime(
				require, cacheCarrier, companySets, employeeSets, settingsByFlex, true, 
				yearMonth, companyId, employmentCode, employeeId, datePeriod.end(), Optional.of(datePeriod),
				closureId, closureDate, Optional.of(aggregateTotalWorkingTime), monthlyCalculatingDailys);
		
		/** 実績所定労働時間を補正する */
		this.recordPrescribedWorkingTime = new AttendanceTimeMonth(prescribedWorkingTime.getSpecifiedSetting().valueAsMinutes());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PrescribedWorkingTimeOfMonthly target){
		
		this.schedulePrescribedWorkingTime = this.schedulePrescribedWorkingTime.addMinutes(
				target.schedulePrescribedWorkingTime.v());
		this.recordPrescribedWorkingTime = this.recordPrescribedWorkingTime.addMinutes(
				target.recordPrescribedWorkingTime.v());
	}
	
	public static interface Require extends FlexLegalTimeGetter.RequireM1 {}
}
