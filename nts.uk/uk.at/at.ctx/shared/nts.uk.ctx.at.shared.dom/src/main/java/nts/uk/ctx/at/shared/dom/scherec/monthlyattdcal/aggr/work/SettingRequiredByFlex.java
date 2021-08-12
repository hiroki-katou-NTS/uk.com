package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryLaborDivisionService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.ReferencePredTimeOfFlex;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * フレックス勤務が必要とする設定
 * @author shuichi_ishida
 */
public class SettingRequiredByFlex {

	/** フレックス時間勤務の月の集計設定 */
	@Setter
	@Getter
	private FlexMonthWorkTimeAggrSet flexAggrSet;
	/** フレックス勤務の月別集計設定 */
	@Setter
	@Getter
	private Optional<MonthlyAggrSetOfFlex> monthlyAggrSetOfFlexOpt;
	/** フレックス勤務所定労働時間取得 */
	@Setter
	@Getter
	private Optional<GetFlexPredWorkTime> getFlexPredWorkTimeOpt;
	/** フレックス不足の年休補填管理 */
	@Setter
	@Getter
	private Optional<InsufficientFlexHolidayMnt> insufficientFlexOpt;
	/** フレックス不足の繰越上限管理 */
	@Setter
	@Getter
	private Optional<FlexShortageLimit> flexShortageLimitOpt;
	/** 休暇加算時間設定 */
	@Getter
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 月間法定労働時間 */
	@Setter
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 月間所定労働時間 */
	@Setter
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	/** 週平均時間 */
	@Setter
	private AttendanceTimeMonth weekAverageTime;
	/** 翌月繰越可能時間 */
	@Setter
	@Getter
	private AttendanceTimeMonth canNextCarryforwardTimeMonth;
	/** 休出枠の役割 */
	@Getter
	private Map<Integer, WorkdayoffFrame> roleHolidayWorkFrameMap;

	@Setter
	@Getter
	private FlexAggregateMethodOfMonthly flexAggregateMethodMonthly;
	/**
	 * コンストラクタ
	 */
	public SettingRequiredByFlex(){
		
		this.flexAggrSet = null;
		this.monthlyAggrSetOfFlexOpt = Optional.empty();
		this.getFlexPredWorkTimeOpt = Optional.empty();
		this.insufficientFlexOpt = Optional.empty();
		this.flexShortageLimitOpt = Optional.empty();
		this.holidayAdditionMap = new HashMap<>();
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.weekAverageTime = new AttendanceTimeMonth(0);
		this.canNextCarryforwardTimeMonth = new AttendanceTimeMonth(0);
		this.roleHolidayWorkFrameMap = new HashMap<>();
	}
	
	public AttendanceTimeMonth getStatutoryWorkingTimeMonthRaw() {
		
		return this.statutoryWorkingTimeMonth;
	}
	
	public AttendanceTimeMonth getStatutoryWorkingTimeMonth(Require require, YearMonth yearMonth, DatePeriod datePeriod, 
			ClosureId closureId, Optional<CompensatoryLeaveUseTimeOfMonthly> monthlyCompensatoryTime,
			Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime) {
		
		val newStatutory = flexAggregateMethodMonthly.getTimeDivisionByWorkingDays(require, yearMonth, 
				new MonthlyEstimateTime(this.statutoryWorkingTimeMonth.valueAsMinutes()), datePeriod, closureId);
		
		int noCompensatoryTime = this.flexAggregateMethodMonthly.getCompensatoryTimeSet().getSubtractedTime(
				monthlyCompensatoryTime.get(), dailyAttendanceTime, datePeriod, flexAggrSet, newStatutory).v();
		
		return new AttendanceTimeMonth(noCompensatoryTime);
	}
	
	public AttendanceTimeMonth getPrescribedWorkingTimeMonthRaw() {
		
		return this.prescribedWorkingTimeMonth;
	}
	
	public AttendanceTimeMonth getPrescribedWorkingTimeMonth(Require require, YearMonth yearMonth, DatePeriod datePeriod, 
			ClosureId closureId, Optional<CompensatoryLeaveUseTimeOfMonthly> monthlyCompensatoryTime,
			Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime) {
		
		val newPres = flexAggregateMethodMonthly.getTimeDivisionByWorkingDays(require, yearMonth, 
				new MonthlyEstimateTime(this.prescribedWorkingTimeMonth.valueAsMinutes()), datePeriod, closureId);

		int noCompensatoryTime = this.flexAggregateMethodMonthly.getCompensatoryTimeSet().getSubtractedTime(
				monthlyCompensatoryTime.get(), dailyAttendanceTime, datePeriod, flexAggrSet, newPres).v();
		
		return new AttendanceTimeMonth(noCompensatoryTime);
	}
	
	public AttendanceTimeMonth getWeekAverageTimeRaw() {
		
		return this.weekAverageTime;
	}
	
	public AttendanceTimeMonth getWeekAverageTime(Require require, YearMonth yearMonth, DatePeriod datePeriod, ClosureId closureId) {
		
		val newPres = flexAggregateMethodMonthly.getTimeDivisionByWorkingDays(require, yearMonth, 
				new MonthlyEstimateTime(this.weekAverageTime.valueAsMinutes()), datePeriod, closureId);
		
		return new AttendanceTimeMonth(newPres.valueAsMinutes());
	}
	

	/** 法定労働時間を取得する（フレックス用） */
	public Optional<MonthlyFlexStatutoryLaborTime> getFlexStatutoryLaborTime(RequireM1 require, CacheCarrier cacheCarrier, boolean isCurrentMonth, YearMonth ym, 
			String cid, String employmentCode, String sid, GeneralDate baseDate, Optional<DatePeriod> period, ClosureId closureId, 
			ClosureDate closureDate, Optional<AggregateTotalWorkingTime> agggregateTotalWorkingTime,
			Collection<AttendanceTimeOfDailyAttendance> dailyAttendanceTime) {
		
		/** 按分した週、月の法定労働時間を取得(フレックス用) */
		val workingTime = MonthlyStatutoryLaborDivisionService.getDivisiedStatutoryLabor(require, cacheCarrier, cid, employmentCode, 
				sid, baseDate, period, ym, closureId, this.flexAggregateMethodMonthly);
		
		/** 当月かを確認する */
		if(isCurrentMonth) {
			/** ○「フレックス勤務所定労働時間取得」を取得する */
			if (!this.getFlexPredWorkTimeOpt.isPresent()) {
				/** エラーログ書き込み */
//				this.errorInfos.add(new MonthlyAggregationErrorInfo("016", new ErrMessageContent(TextResource.localize("Msg_1243"))));
				return Optional.empty();
			}
			/** ○「フレックス勤務所定労働時間取得」を確認する */
			int prescribedTime = this.getFlexPredWorkTimeOpt.map(c -> {
				if (c.getReference() == ReferencePredTimeOfFlex.FROM_MASTER)
					return workingTime.getSpecifiedSetting().v();

				/** 当月の月別実績の勤怠時間を取得する */
				/** 所定労働時間を上書きする */
				return agggregateTotalWorkingTime.get().getPrescribedWorkingTime().getTimeSeriesWorks().stream()
							.mapToInt(t -> t.getPrescribedWorkingTime().getRecordPrescribedLaborTime().valueAsMinutes())
							.sum();
			}).get();
			
			/** 法定労働時間から代休時間を引く時間を取得して上書きする */
			int statutoryTime = this.flexAggregateMethodMonthly.getCompensatoryTimeSet().getSubtractedTime(
					agggregateTotalWorkingTime.get().getVacationUseTime().getCompensatoryLeave(), 
					dailyAttendanceTime, period.get(), flexAggrSet, workingTime.getStatutorySetting()).v();
			
			/** 所定労働時間から代休時間を引く時間を取得して上書きする */
			prescribedTime = this.flexAggregateMethodMonthly.getCompensatoryTimeSet().getSubtractedTime(
					agggregateTotalWorkingTime.get().getVacationUseTime().getCompensatoryLeave(), 
					dailyAttendanceTime, period.get(), flexAggrSet, new MonthlyEstimateTime(prescribedTime)).v();
			
			/** 月の法定労働時間（フレックス用）を返す */
			return Optional.of(new MonthlyFlexStatutoryLaborTime(new MonthlyEstimateTime(statutoryTime),
																new MonthlyEstimateTime(prescribedTime), 
																workingTime.getWeekAveSetting()));
		}
		
		/** 月別実績の勤怠時間を取得する */
		val attendanceTime = require.attendanceTimeOfMonthly(sid, ym, closureId, closureDate);
		return attendanceTime.map(c -> {
			
			/** ・法定労働時間＝取得した月別実績の法定労働時間 */
			val statutory = c.getMonthlyCalculation().getStatutoryWorkingTime();
			/** ・所定労働時間＝当月フレックス時間．基準時間*/
			val specifi = c.getMonthlyCalculation().getFlexTime().getFlexTimeOfExcessOutsideTime().getFlexTimeCurrentMonth().getStandardTime();
			
			return Optional.of(new MonthlyFlexStatutoryLaborTime(new MonthlyEstimateTime(statutory.valueAsMinutes()),
																 new MonthlyEstimateTime(specifi.valueAsMinutes()), 
																 workingTime.getWeekAveSetting()));
		}).orElseGet(() -> {
			
			return Optional.of(new MonthlyFlexStatutoryLaborTime(new MonthlyEstimateTime(0), new MonthlyEstimateTime(0), workingTime.getWeekAveSetting()));
		});
		
	}
	
	public static interface RequireM1 extends MonthlyStatutoryLaborDivisionService.Require {
		
		Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	}
	
	public static interface Require extends FlexAggregateMethodOfMonthly.Require {}
}
