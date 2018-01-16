package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays.PayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.SpecificDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AttendanceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayWorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.PredeterminedDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TemporaryWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TwoTimesWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkTimesOfMonthly;

/**
 * 月別実績の勤務日数
 * @author shuichu_ishida
 */
@Getter
public class WorkDaysOfMonthly {

	/** 出勤日数 */
	private AttendanceDaysOfMonthly attendanceDays;
	/** 欠勤日数 */
	private AbsenceDaysOfMonthly absenceDays;
	/** 所定日数 */
	private PredeterminedDaysOfMonthly predetermineDays;
	/** 勤務日数 */
	private WorkDaysOfMonthly workDays;
	/** 休日日数 */
	private HolidayDaysOfMonthly holidayDays;
	/** 特定日数 */
	private SpecificDaysOfMonthly specificDays;
	/** 休出日数 */
	private HolidayWorkDaysOfMonthly holidayWorkDays;
	/** 給与用日数 */
	private PayDaysOfMonthly payDays;
	/** 勤務回数 */
	private WorkTimesOfMonthly workTimes;
	/** 二回勤務回数 */
	private TwoTimesWorkTimesOfMonthly twoTimesWorkTimes;
	/** 臨時勤務回数 */
	private TemporaryWorkTimesOfMonthly temporaryWorkTimes;
	/** 休業 */
	private LeaveOfMonthly leave;
	
	/**
	 * コンストラクタ
	 */
	public WorkDaysOfMonthly(){
		
		this.attendanceDays = new AttendanceDaysOfMonthly();
		this.absenceDays = new AbsenceDaysOfMonthly();
		this.predetermineDays = new PredeterminedDaysOfMonthly();
		this.workDays = new WorkDaysOfMonthly();
		this.holidayDays = new HolidayDaysOfMonthly();
		this.specificDays = new SpecificDaysOfMonthly();
		this.holidayWorkDays = new HolidayWorkDaysOfMonthly();
		this.payDays = new PayDaysOfMonthly();
		this.workTimes = new WorkTimesOfMonthly();
		this.twoTimesWorkTimes = new TwoTimesWorkTimesOfMonthly();
		this.temporaryWorkTimes = new TemporaryWorkTimesOfMonthly();
		this.leave = new LeaveOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param attendanceDays 出勤日数
	 * @param absenceDays 欠勤日数
	 * @param predetermineDays 所定日数
	 * @param workDays 勤務日数
	 * @param holidayDays 休日日数
	 * @param specficDays 特定日数
	 * @param holidayWorkDays 休出日数
	 * @param payDays 給与用日数
	 * @param workTimes 勤務回数
	 * @param twoTimesWorkTimes 二回勤務回数
	 * @param temporaryWorkTimes 臨時勤務回数
	 * @param leave 休業
	 * @return 月別実績の勤務日数
	 */
	public static WorkDaysOfMonthly of(
			AttendanceDaysOfMonthly attendanceDays,
			AbsenceDaysOfMonthly absenceDays,
			PredeterminedDaysOfMonthly predetermineDays,
			WorkDaysOfMonthly workDays,
			HolidayDaysOfMonthly holidayDays,
			SpecificDaysOfMonthly specficDays,
			HolidayWorkDaysOfMonthly holidayWorkDays,
			PayDaysOfMonthly payDays,
			WorkTimesOfMonthly workTimes,
			TwoTimesWorkTimesOfMonthly twoTimesWorkTimes,
			TemporaryWorkTimesOfMonthly temporaryWorkTimes,
			LeaveOfMonthly leave){
		
		val domain = new WorkDaysOfMonthly();
		domain.attendanceDays = attendanceDays;
		domain.absenceDays = absenceDays;
		domain.predetermineDays = predetermineDays;
		domain.workDays = workDays;
		domain.holidayDays = holidayDays;
		domain.specificDays = specficDays;
		domain.holidayWorkDays = holidayWorkDays;
		domain.payDays = payDays;
		domain.workTimes = workTimes;
		domain.twoTimesWorkTimes = twoTimesWorkTimes;
		domain.temporaryWorkTimes = temporaryWorkTimes;
		domain.leave = leave;
		return domain;
	}
}
