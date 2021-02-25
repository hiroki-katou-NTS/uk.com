package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 月別実績の休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class VacationUseTimeOfMonthly implements Cloneable, Serializable {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 年休 */
	private AnnualLeaveUseTimeOfMonthly annualLeave;
	/** 積立年休 */
	private RetentionYearlyUseTimeOfMonthly retentionYearly;
	/** 特別休暇 */
	private SpecialHolidayUseTimeOfMonthly specialHoliday;
	/** 代休 */
	private CompensatoryLeaveUseTimeOfMonthly compensatoryLeave;
	
	/**
	 * コンストラクタ
	 */
	public VacationUseTimeOfMonthly(){
		
		this.annualLeave = new AnnualLeaveUseTimeOfMonthly();
		this.retentionYearly = new RetentionYearlyUseTimeOfMonthly();
		this.specialHoliday = new SpecialHolidayUseTimeOfMonthly();
		this.compensatoryLeave = new CompensatoryLeaveUseTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param retentionYearly 積立年休
	 * @param specialHoliday 特別休暇
	 * @param compensatoryLeave 代休
	 * @return 月別実績の休暇使用時間
	 */
	public static VacationUseTimeOfMonthly of(
			AnnualLeaveUseTimeOfMonthly annualLeave,
			RetentionYearlyUseTimeOfMonthly retentionYearly,
			SpecialHolidayUseTimeOfMonthly specialHoliday,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeave){

		val domain = new VacationUseTimeOfMonthly();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHoliday = specialHoliday;
		domain.compensatoryLeave = compensatoryLeave;
		return domain;
	}

	@Override
	public VacationUseTimeOfMonthly clone() {
		VacationUseTimeOfMonthly cloned = new VacationUseTimeOfMonthly();
		try {
			cloned.annualLeave = this.annualLeave.clone();
			cloned.retentionYearly = this.retentionYearly.clone();
			cloned.specialHoliday = this.specialHoliday.clone();
			cloned.compensatoryLeave = this.compensatoryLeave.clone();
		}
		catch (Exception e){
			throw new RuntimeException("VacationUseTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 休暇使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param workInfoOfDailyMap 日別実績の勤務情報リスト
	 */
	public void confirm(RequireM1 require, DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyMap,
			Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailyMap){
		
		// 年休使用時間を確認する
		this.annualLeave.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 積立年休使用時間を確認する
		this.retentionYearly.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 特別休暇使用時間を確認する
		this.specialHoliday.confirm(datePeriod, attendanceTimeOfDailyMap);

		// 代休使用時間を確認する
		this.compensatoryLeave.confirm(require, datePeriod, attendanceTimeOfDailyMap, workInfoOfDailyMap);
	}
	
	/**
	 * 休暇使用時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregate(DatePeriod datePeriod){
		
		// 年休使用時間を集計する
		this.annualLeave.aggregate(datePeriod);
		
		// 積立年休使用時間を集計する
		this.retentionYearly.aggregate(datePeriod);
		
		// 特別年休使用時間を集計する
		this.specialHoliday.aggregate(datePeriod);
		
		// 代休使用時間を集計する
		this.compensatoryLeave.aggregate(datePeriod);
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(VacationUseTimeOfMonthly target){
		
		this.annualLeave.addMinuteToUseTime(target.annualLeave.getUseTime().v());
		this.retentionYearly.addMinuteToUseTime(target.retentionYearly.getUseTime().v());
		this.specialHoliday.addMinuteToUseTime(target.specialHoliday.getUseTime().v());
		this.compensatoryLeave.addMinuteToUseTime(target.compensatoryLeave.getUseTime().v());
	}
	
	public static interface RequireM1 extends CompensatoryLeaveUseTimeOfMonthly.RequireM1 {

	}
}