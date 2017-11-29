package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;

/**
 * 月別実績の休暇使用時間
 * @author shuichi_ishida
 */
@Getter
public class HolidayUseTimeOfMonthly {
	
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
	public HolidayUseTimeOfMonthly(){
		
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
	public static HolidayUseTimeOfMonthly of(
			AnnualLeaveUseTimeOfMonthly annualLeave,
			RetentionYearlyUseTimeOfMonthly retentionYearly,
			SpecialHolidayUseTimeOfMonthly specialHoliday,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeave){

		HolidayUseTimeOfMonthly domain = new HolidayUseTimeOfMonthly();
		domain.annualLeave = annualLeave;
		domain.retentionYearly = retentionYearly;
		domain.specialHoliday = specialHoliday;
		domain.compensatoryLeave = compensatoryLeave;
		return domain;
	}
	
	/**
	 * 休暇使用時間を確認する
	 * @param attendanceTimeOfDailys リスト：日別実績の勤怠時間
	 */
	public void confirm(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		// 年休使用時間を確認する
		this.annualLeave.confirm(attendanceTimeOfDailys);

		// 積立年休使用時間を確認する
		this.retentionYearly.confirm(attendanceTimeOfDailys);

		// 特別休暇使用時間を確認する
		this.specialHoliday.confirm(attendanceTimeOfDailys);

		// 代休使用時間を確認する
		this.compensatoryLeave.confirm(attendanceTimeOfDailys);
	}
	
	/**
	 * 休暇使用時間を集計する
	 */
	public void aggregate(){
		
		// 年休使用時間を集計する
		this.annualLeave.aggregate();
		
		// 積立年休使用時間を集計する
		this.retentionYearly.aggregate();
		
		// 特別年休使用時間を集計する
		this.specialHoliday.aggregate();
		
		// 代休使用時間を集計する
		this.compensatoryLeave.aggregate();
	}
}
