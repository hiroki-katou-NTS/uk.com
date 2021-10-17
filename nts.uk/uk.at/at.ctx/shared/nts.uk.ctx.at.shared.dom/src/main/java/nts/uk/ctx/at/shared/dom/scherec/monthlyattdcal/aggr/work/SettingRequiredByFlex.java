package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work;

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
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryLaborDivisionService;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * フレックス勤務が必要とする設定
 * @author shuichi_ishida
 */
@Setter
@Getter
public class SettingRequiredByFlex {

	/** フレックス時間勤務の月の集計設定 */
	private FlexMonthWorkTimeAggrSet flexAggrSet;
	/** フレックス勤務の月別集計設定 */
	private Optional<MonthlyAggrSetOfFlex> monthlyAggrSetOfFlexOpt;
	/** フレックス勤務の日別計算設定 */
	private FlexSet dailyCalcSetOfFlex;
	/** 会社別フレックス勤務集計方法 */
	private Optional<ComFlexMonthActCalSet> comFlexSetOpt;
	/** フレックス不足の年休補填管理 */
	private Optional<InsufficientFlexHolidayMnt> insufficientFlexOpt;
	/** フレックス不足の繰越上限管理 */
	private Optional<FlexShortageLimit> flexShortageLimitOpt;
	/** 休暇加算時間設定 */
	private Map<String, AggregateRoot> holidayAdditionMap;
	/** 月間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 月間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	/** 週平均時間 */
	private AttendanceTimeMonth weekAverageTime;
	/** 翌月繰越可能時間 */
	private AttendanceTimeMonth canNextCarryforwardTimeMonth;
	/** 休出枠の役割 */
	private Map<Integer, WorkdayoffFrame> roleHolidayWorkFrameMap;
	/** 月別実績集計のフレックス集計方法 */
	private FlexAggregateMethodOfMonthly flexAggregateMethodMonthly;
	
	/**
	 * コンストラクタ
	 */
	public SettingRequiredByFlex() {
		
		this.flexAggrSet = null;
		this.monthlyAggrSetOfFlexOpt = Optional.empty();
		this.dailyCalcSetOfFlex = new FlexSet();
		this.comFlexSetOpt = Optional.empty();
		this.insufficientFlexOpt = Optional.empty();
		this.flexShortageLimitOpt = Optional.empty();
		this.holidayAdditionMap = new HashMap<>();
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.weekAverageTime = new AttendanceTimeMonth(0);
		this.canNextCarryforwardTimeMonth = new AttendanceTimeMonth(0);
		this.roleHolidayWorkFrameMap = new HashMap<>();
		this.flexAggregateMethodMonthly = new FlexAggregateMethodOfMonthly();
	}
	
	/**
	 * 按分した週、月の法定労働時間（代休控除後）を取得
	 * @param require Require
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 法定労働時間（代休控除後）
	 */
	public AttendanceTimeMonth getStatutoryWorkingTimeMonth(
			RequireM1 require,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			YearMonth yearMonth,
			DatePeriod datePeriod,
			ClosureId closureId,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {

		// 在籍日数で按分した時間を取得する　←　法定労働時間
		MonthlyEstimateTime statTime = flexAggregateMethodMonthly.getTimeDivisionByWorkingDays(require, yearMonth,
				new MonthlyEstimateTime(this.statutoryWorkingTimeMonth.valueAsMinutes()), datePeriod, closureId);
		// 代休控除時間の計算
		int compLeaveDeductMinutes = 0;
//		CompLeaveDeductTime compLeaveDeductTime = this.calcCompLeaveDeductTime(
//				require, companySets, employeeSets, baseDate, period, monthlyCalculatingDailys);
		// 法定労働時間（代休控除後）を返す
		return new AttendanceTimeMonth(statTime.valueAsMinutes() - compLeaveDeductMinutes);
	}
	
	/**
	 * 按分した週、月の所定労働時間（代休控除後）を取得
	 * @param require Require
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param yearMonth 年月(度)
	 * @param datePeriod 期間
	 * @param closureId 締めID
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 法定労働時間（代休控除後）
	 */
	public AttendanceTimeMonth getPrescribedWorkingTimeMonth(
			RequireM1 require,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			YearMonth yearMonth,
			DatePeriod datePeriod, 
			ClosureId closureId,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 在籍日数で按分した時間を取得する　←　所定労働時間
		MonthlyEstimateTime predTime = flexAggregateMethodMonthly.getTimeDivisionByWorkingDays(require, yearMonth, 
				new MonthlyEstimateTime(this.prescribedWorkingTimeMonth.valueAsMinutes()), datePeriod, closureId);
		// 代休控除時間の計算
		int compLeaveDeductMinutes = 0;
//		CompLeaveDeductTime compLeaveDeductTime = this.calcCompLeaveDeductTime(
//				require, companySets, employeeSets, baseDate, period, monthlyCalculatingDailys);
		// 所定労働時間（代休控除後）を返す
		return new AttendanceTimeMonth(predTime.valueAsMinutes() - compLeaveDeductMinutes);
	}

	public static interface RequireM1 extends FlexAggregateMethodOfMonthly.Require,
		MonAggrCompanySettings.RequireM2, VacationClass.Require {}
	
	/**
	 * 法定労働時間を取得する（フレックス用）
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param isCurrentMonth 当月かどうか
	 * @param ym 年月
	 * @param cid 会社ID
	 * @param employmentCode 社員コード
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @param period 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param agggregateTotalWorkingTime 集計総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 月の法定労働時間
	 */
	public Optional<MonthlyFlexStatutoryLaborTime> getFlexStatutoryLaborTime(
			RequireM2 require,
			CacheCarrier cacheCarrier,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			boolean isCurrentMonth,
			YearMonth ym, 
			String cid,
			String employmentCode,
			String sid,
			GeneralDate baseDate,
			Optional<DatePeriod> period,
			ClosureId closureId, 
			ClosureDate closureDate,
			Optional<AggregateTotalWorkingTime> agggregateTotalWorkingTime,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		/** 按分した週、月の法定労働時間を取得(フレックス用) */
		val workingTime = MonthlyStatutoryLaborDivisionService.getDivisiedStatutoryLabor(
				require, cacheCarrier, cid, employmentCode, sid, baseDate, period, ym, closureId,
				this.flexAggregateMethodMonthly);
		
		/** 当月かを確認する */
		if(isCurrentMonth) {
			/** ○「フレックス勤務所定労働時間取得」を取得する */
			if (!this.comFlexSetOpt.isPresent()) {
				/** エラーログ書き込み */
//				this.errorInfos.add(new MonthlyAggregationErrorInfo("016", new ErrMessageContent(TextResource.localize("Msg_1243"))));
				return Optional.empty();
			}
			/** ○「フレックス勤務所定労働時間取得」を確認する */
			int prescribedTime = this.comFlexSetOpt.map(c -> {
				if (c.isWithinTimeUsageAttr())
					return workingTime.getSpecifiedSetting().v();

				/** 当月の月別実績の勤怠時間を取得する */
				/** 所定労働時間を上書きする */
				return agggregateTotalWorkingTime.get().getPrescribedWorkingTime().getTimeSeriesWorks().stream()
							.mapToInt(t -> t.getPrescribedWorkingTime().getRecordPrescribedLaborTime().valueAsMinutes())
							.sum();
			}).get();
			// 代休控除時間の計算
			int compLeaveDeductMinutes = 0;
//			CompLeaveDeductTime compLeaveDeductTime = this.calcCompLeaveDeductTime(
//					require, companySets, employeeSets, baseDate, period, monthlyCalculatingDailys);
			// 法定労働時間から代休控除時間を減算する
			int statutoryTime = workingTime.getStatutorySetting().v() - compLeaveDeductMinutes;
			// 所定労働時間から代休控除時間を減算する
			prescribedTime -= compLeaveDeductMinutes;
			// 月の法定労働時間（フレックス用）を返す
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
	
	/**
	 * 代休控除時間の計算
	 * @param require Require
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param baseDate 基準日
	 * @param periodOpt 期間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 代休控除時間
	 */
	private CompLeaveDeductTime calcCompLeaveDeductTime(
			RequireM2 require,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			GeneralDate baseDate,
			Optional<DatePeriod> periodOpt,
			MonthlyCalculatingDailys monthlyCalculatingDailys){

		CompLeaveDeductTime result = new CompLeaveDeductTime();
		
		if (!periodOpt.isPresent()) return new CompLeaveDeductTime();
		DatePeriod period = periodOpt.get();
		for (GeneralDate procDate = period.start(); procDate.beforeOrEquals(period.end()); procDate = procDate.addDays(1)){
			// 日別勤怠の勤務情報
			WorkInfoOfDailyAttendance workInfo = monthlyCalculatingDailys.getWorkInfoOfDailyMap().get(procDate);
			if (workInfo == null) continue;
			// 日別勤怠の勤怠時間
			AttendanceTimeOfDailyAttendance attendanceTime = monthlyCalculatingDailys.getAttendanceTimeOfDailyMap().get(procDate);
			if (attendanceTime == null) continue;
			// 勤務種類を取得
			WorkType workType = companySets.getWorkTypeMap(require, workInfo.getRecordInfo().getWorkTypeCode().v());
			if (workType == null) continue;
			// 所定用就業時間帯コードを取得する
			Optional<WorkingConditionItem> conditionItemOpt = employeeSets.getWorkingConditionItem(baseDate);
			if (!conditionItemOpt.isPresent()) continue;
			Optional<WorkTimeCode> workTimeCodeOpt = workInfo.getRecordInfo().getWorkTimeCodeForPred(require, conditionItemOpt.get());
			if (!workTimeCodeOpt.isPresent()) continue;
			WorkTimeCode workTimeCode = workTimeCodeOpt.get();
			// 日単位の代休控除時間の計算
			result.add(this.calcCompLeaveDeductTimeEachDay(
					require, companySets, employeeSets.getEmployeeId(), baseDate, workType, workTimeCode));
			// 時間単位の代休控除時間の計算
			result.add(this.calcCompLeaveDeductTimeEachTime(attendanceTime));
		}
		// 代休控除時間を返す
		return result;
	}
	
	/**
	 * 日単位の代休控除時間の計算
	 * @param require Require
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workType 勤務種類
	 * @param workTimeCode 就業時間帯コード
	 * @return 代休控除時間
	 */
	private CompLeaveDeductTime calcCompLeaveDeductTimeEachDay(
			RequireM2 require,
			MonAggrCompanySettings companySets,
			String employeeId,
			GeneralDate baseDate,
			WorkType workType,
			WorkTimeCode workTimeCode){
		
		// 所定時間設定を取得
		PredetemineTimeSetting predTimeSet = companySets.getPredetemineTimeSetMap(require, workTimeCode.v());
		if (predTimeSet == null) return new CompLeaveDeductTime();
		PredetermineTimeSetForCalc predetermineTimeSet = PredetermineTimeSetForCalc.convertMastarToCalc(predTimeSet);
		// 所定時間の内訳を取得
		BreakDownTimeDay breakDown = VacationClass.getBreakDownOfPredTime(require, employeeId, baseDate,
				VacationCategory.SubstituteHoliday, Optional.of(predetermineTimeSet), Optional.empty());
		// 日単位の所定控除時間の計算
		AttendanceTime forActual = this.dailyCalcSetOfFlex.getCompLeave().calcDeductPredTimeOfDay(
				workType, false, breakDown, this.flexAggrSet);
		AttendanceTime forPremium = this.dailyCalcSetOfFlex.getCompLeave().calcDeductPredTimeOfDay(
				workType, false, breakDown, this.flexAggrSet);
		// 代休控除時間を返す
		return new CompLeaveDeductTime(
				new AttendanceTimeMonth(forActual.valueAsMinutes()),
				new AttendanceTimeMonth(forPremium.valueAsMinutes()));
	}

	/**
	 * 時間単位の代休控除時間の計算
	 * @param dailyAttendanceTime 日別勤怠の勤怠時間
	 * @return 代休控除時間
	 */
	private AttendanceTimeMonth calcCompLeaveDeductTimeEachTime(
			AttendanceTimeOfDailyAttendance dailyAttendanceTime){
		
		// 遅刻、早退、外出の相殺時間を取得する
		AttendanceTime offsetTime = dailyAttendanceTime.getTotalOffsetCompLeaveTime();
		// 遅刻、早退、外出の使用時間を取得する
		AttendanceTime useTime = dailyAttendanceTime.getTotalTimeCompLeaveUseTime();
		// 時間単位の所定控除時間の計算
		AttendanceTime deductTime = this.dailyCalcSetOfFlex.getCompLeave().calcDeductPredTimeOfTime(
				offsetTime, useTime, this.flexAggrSet);
		// 代休控除時間を返す
		return new AttendanceTimeMonth(deductTime.valueAsMinutes());
	}
	
	public static interface RequireM2 extends MonthlyStatutoryLaborDivisionService.Require,
		MonAggrCompanySettings.RequireM2, MonAggrCompanySettings.RequireM4,
		WorkInformation.RequireM1, AttendanceTimeOfMonthly.Require, VacationClass.Require {}
}
