package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
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
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

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
	
	/**
	 * 集計
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param timeLeaveingOfDailys 日別実績の出退勤リスト
	 * @param specificDateAtrOfDailys 日別実績の特定日区分リスト
	 * @param workTypeMap 勤務種類マップ
	 */
	public void aggregate(
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<TimeLeavingOfDailyPerformance> timeLeaveingOfDailys,
			List<SpecificDateAttrOfDailyPerfor> specificDateAtrOfDailys,
			Map<String, WorkType> workTypeMap){
		
		// 出勤日数の集計
		//*****（未）　勤務種類の判断方法の設計確認要。
		this.attendanceDays.aggregate(workInfoOfDailys, workTypeMap);
		
		// 欠勤日数の集計
		//*****（未）　勤務種類の判断方法、出勤状態の判断方法の設計確認要。
		
		// 所定日数の集計
		//*****（未）　年休付与前後判断方法、勤務種類の判断方法の設計確認要。
		
		// 勤務日数の集計
		//*****（未）　勤務種類の判断方法、出勤状態の判断方法の設計確認要。
		
		// 休日日数の集計
		//*****（未）　勤務種類の判断方法の設計確認要。
		
		// 特定日日数の集計
		//*****（未）　設計確認要。途中に前日休出確認。予備区分判定の部分中心に確認要。
		
		// 休出日数の集計
		//*****（未）　勤務種類の判断方法、出勤状態の判断方法の設計確認要。
		
		// 給与支払い基礎日数の集計
		//*****（未）　回数集計（マスタ）の利用・判断方法、続く勤務種類の判断方法の設計確認要。
		
		// 勤務回数の集計
		this.workTimes.aggregate(attendanceTimeOfDailys);
		
		// 二回勤務回数の集計
		//*****（未）　設計確認要。
		this.twoTimesWorkTimes.aggregate();
		
		// 臨時勤務回数の集計
		//*****（未）　日別側の臨時勤務が出来てから。
		
		// 休業日数の集計
		//*****（未）　勤務種類の判断方法、出勤状態の判断方法の設計確認要。
	}
}
