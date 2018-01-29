package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class VacationUseTimeOfMonthly {
	
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
	
	/**
	 * 休暇使用時間を確認する
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(DatePeriod datePeriod,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		// 年休使用時間を確認する
		this.annualLeave.confirm(datePeriod, attendanceTimeOfDailys);

		// 積立年休使用時間を確認する
		this.retentionYearly.confirm(datePeriod, attendanceTimeOfDailys);

		// 特別休暇使用時間を確認する
		this.specialHoliday.confirm(datePeriod, attendanceTimeOfDailys);

		// 代休使用時間を確認する
		this.compensatoryLeave.confirm(datePeriod, attendanceTimeOfDailys);
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
}
