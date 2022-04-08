package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriodOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.CompLeaveDeductTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryLaborDivisionService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/** 法定内として扱う時間を求める */
public class FlexLegalTimeGetter {

	/**
	 * 法定内として扱う時間を求める
	 * @param require Require
	 * @param cacheCarrier Cache
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param yearMonth 年月（度）
	 * @param aggregateAtr 集計区分
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @param agggregateTotalWorkingTime 集計総労働時間
	 * @param monthlyCalculatingDailys 月の計算中の日別実績データ
	 * @return 法定内として扱う時間
	 */
	public static AttendanceTimeMonth askTreatLegalTime(Require require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, DatePeriod period, YearMonth yearMonth, MonthlyAggregateAtr aggregateAtr, 
			MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex,
			ClosureId closureId, ClosureDate closureDate, Optional<AggregateTotalWorkingTime> aggregateTotalWorkingTime, 
			MonthlyCalculatingDailys monthlyCalculatingDailys, FlexCarryforwardTime flexCarryforwardTime,
			FlexTimeCurrentMonth flexTimeCurrentMonth) {
		
		int result = 0;
		val flexAggrSet = settingsByFlex.getFlexAggrSet();
//		FlexTimeCurrentMonth flexTimeCurrentMonth = this.flexTime.getFlexTimeCurrentMonth();
//		if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
//			// 時間外超過の時、時間外超過のフレックス時間を利用する
//			flexTimeCurrentMonth = this.flexTimeOfExcessOutsideTime.getFlexTimeCurrentMonth();
//		}
		// 期間終了日時点の雇用コードを取得する
		Optional<BsEmploymentHistoryImport> employmentOpt = employeeSets.getEmployment(period.end());
		// 「不足設定．清算期間」を確認する
		if (flexAggrSet.getInsufficSet().getSettlePeriod() == SettlePeriod.SINGLE_MONTH){
			// 単月
			// 法定労働時間を取得する（フレックス用）
			MonthlyFlexStatutoryLaborTime monStatTime = new MonthlyFlexStatutoryLaborTime();
			if (employmentOpt.isPresent()){
				String employmentCd = employmentOpt.get().getEmploymentCode();
				monStatTime = getFlexStatutoryLaborTime(
						require, cacheCarrier, companySets, employeeSets, settingsByFlex,
						true, yearMonth, companyId, employmentCd, employeeId,
						period.end(), Optional.of(period), closureId, closureDate, 
						aggregateTotalWorkingTime, monthlyCalculatingDailys);
			}
			// 「法定内として扱う時間」を求める　（法定労働時間－所定労働時間（基準時間）－繰越勤務時間）
			result += monStatTime.getStatutorySetting().valueAsMinutes();
			result -= flexTimeCurrentMonth.getStandardTime().v();
			result -= flexCarryforwardTime.getFlexCarryforwardWorkTime().v();
			// 「法定内として扱う時間」を返す
			return new AttendanceTimeMonth(result < 0 ? 0 : result);
		}

		// 複数月
		// フレックス清算期間の取得
		SettlePeriodOfFlex settlePeriod = flexAggrSet.getInsufficSet().getSettlePeriod(require, cacheCarrier, employeeId, period, yearMonth);
		
		int totalStandMinutes = 0;		// 基準時間合計（分）
		int totalStatMinutes = 0;		// 法定労働時間合計（分）
		
		// 開始月～清算月を年月ごとにループする
		YearMonth indexYm = settlePeriod.getStartYm();
		for (; indexYm.lessThanOrEqualTo(settlePeriod.getSettleYm()); indexYm = indexYm.nextMonth()){
			// ループ中の年月と「年月」を比較する
			if (indexYm.lessThan(yearMonth)){	// 当月以外
				// ループ中の年月の「月別実績の勤怠時間」を取得する
				val prevAttendanceTimeList = require.attendanceTimeOfMonthly(employeeId, indexYm, closureId, closureDate);
				// 「基準時間合計」に「基準時間」を加算する
				if (prevAttendanceTimeList.isPresent()) {
					val prevAttendanceTime = prevAttendanceTimeList.get();
					val prevFlexTime = prevAttendanceTime.getMonthlyCalculation().getFlexTime();
					FlexTimeCurrentMonth prevCurrentMonth = prevFlexTime.getFlexTime().getFlexTimeCurrentMonth();
					if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){
						// 時間外超過の時、時間外超過のフレックス時間を利用する
						prevCurrentMonth = prevFlexTime.getFlexTimeOfExcessOutsideTime().getFlexTimeCurrentMonth();
					}
					totalStandMinutes += prevCurrentMonth.getStandardTime().v();
				}
			} else if (indexYm.equals(yearMonth)) {		// 当月
				// 「基準時間合計」に「基準時間」を加算する
				totalStandMinutes += flexTimeCurrentMonth.getStandardTime().v();
			}
			if (employmentOpt.isPresent()){
				String employmentCd = employmentOpt.get().getEmploymentCode();
				// 法定労働時間を取得する（フレックス用）
				MonthlyFlexStatutoryLaborTime monStatTime = getFlexStatutoryLaborTime(
						require, cacheCarrier, companySets, employeeSets, settingsByFlex,
						indexYm.equals(yearMonth), indexYm, companyId, employmentCd, employeeId,
						period.end(), Optional.of(period), closureId, closureDate, 
						aggregateTotalWorkingTime, monthlyCalculatingDailys);
				// 「法定労働時間合計」に「法定労働時間」を加算する
				totalStatMinutes += monStatTime.getStatutorySetting().valueAsMinutes();
			}
		}
		// 「法定内として扱う時間」を求める
		result = totalStatMinutes - totalStandMinutes;
		if (result < 0) result = 0;
		// 「法定内として扱う時間」を返す
		return new AttendanceTimeMonth(result);
	}
	
	/**
	 * 法定労働時間を取得する（フレックス用）
	 * @return 月の法定労働時間
	 */
	public static MonthlyFlexStatutoryLaborTime getFlexStatutoryLaborTime(RequireM1 require,
			CacheCarrier cacheCarrier,MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			SettingRequiredByFlex settingsByFlex, boolean isCurrentMonth, YearMonth ym, String cid,
			String employmentCode, String sid, GeneralDate baseDate, Optional<DatePeriod> period,
			ClosureId closureId, ClosureDate closureDate, Optional<AggregateTotalWorkingTime> agggregateTotalWorkingTime,
			MonthlyCalculatingDailys monthlyCalculatingDailys) {
		
		// 按分した週、月の法定労働時間を取得(フレックス用)
		val workingTime = MonthlyStatutoryLaborDivisionService.getDivisiedStatutoryLabor(
				require, cacheCarrier, cid, employmentCode, sid, baseDate, period, ym, closureId,
				settingsByFlex.getFlexAggregateMethodMonthly());
		// 当月かどうか確認する
		if(isCurrentMonth) {
			// 「会社別フレックス勤務集計方法」を取得する
			if (!settingsByFlex.getComFlexSetOpt().isPresent()) return new MonthlyFlexStatutoryLaborTime();
			// 「所定労動時間使用区分」を確認する
			int prescribedTime = settingsByFlex.getComFlexSetOpt().map(c -> {
				// 使用する時、設定値の按分結果を取得
				if (c.isWithinTimeUsageAttr()) return workingTime.getSpecifiedSetting().v();
				// 使用しない時、当月の実績所定労働合計時間を取得する
				if (!period.isPresent()) return 0;
				return agggregateTotalWorkingTime.get().getPrescribedWorkingTime()
						.getTotalRecordPrescribedWorkingTime(period.get()).valueAsMinutes();
			}).get();
			// 代休控除時間の計算
			CompLeaveDeductTime compLeaveDeductTime = calcCompLeaveDeductTime(
					require, companySets, employeeSets, settingsByFlex, baseDate, period, monthlyCalculatingDailys);
			int compLeaveDeductMinutes = compLeaveDeductTime.getForActualWork().valueAsMinutes();
			// 法定労働時間から代休控除時間を減算する
			int statutoryTime = workingTime.getStatutorySetting().v() - compLeaveDeductMinutes;
			if (statutoryTime < 0) statutoryTime = 0;
			// 所定労働時間から代休控除時間を減算する
			prescribedTime -= compLeaveDeductMinutes;
			if (prescribedTime < 0) prescribedTime = 0;
			// 月の法定労働時間（フレックス用）を返す
			return new MonthlyFlexStatutoryLaborTime(
					new MonthlyEstimateTime(statutoryTime),
					new MonthlyEstimateTime(prescribedTime),
					workingTime.getWeekAveSetting());
		}
		
		/** 社員の労働条件を取得する */
		val workCondition = require.workingConditionItem(sid, ym.lastGeneralDate());
		if (workCondition.map(c -> c.getLaborSystem() != WorkingSystem.FLEX_TIME_WORK).orElse(true))
			return new MonthlyFlexStatutoryLaborTime(
					new MonthlyEstimateTime(0),
					new MonthlyEstimateTime(0),
					new MonthlyEstimateTime(0));
			
			
		// 月別実績の勤怠時間を取得する
		val attendanceTime = require.attendanceTimeOfMonthly(sid, ym, closureId, closureDate);
		return attendanceTime.map(c -> {
			// 法定労働時間＝取得した月別実績の法定労働時間
			val statutory = c.getMonthlyCalculation().getStatutoryWorkingTime();
			// 所定労働時間＝当月フレックス時間．基準時間
			val specifi = c.getMonthlyCalculation().getFlexTime().getFlexTimeOfExcessOutsideTime().getFlexTimeCurrentMonth().getStandardTime();
			return new MonthlyFlexStatutoryLaborTime(
					new MonthlyEstimateTime(statutory.valueAsMinutes()),
					new MonthlyEstimateTime(specifi.valueAsMinutes()),
					workingTime.getWeekAveSetting());
		}).orElseGet(() -> {
			return new MonthlyFlexStatutoryLaborTime(
					new MonthlyEstimateTime(0),
					new MonthlyEstimateTime(0),
					workingTime.getWeekAveSetting());
		});
	}
	
	/**
	 * 代休控除時間の計算
	 * @return 代休控除時間
	 */
	private static CompLeaveDeductTime calcCompLeaveDeductTime(RequireM1 require, MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets, SettingRequiredByFlex settingsByFlex, GeneralDate baseDate,
			Optional<DatePeriod> periodOpt, MonthlyCalculatingDailys monthlyCalculatingDailys){

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
			Optional<WorkTimeCode> workTimeCodeOpt = workInfo.getRecordInfo().getWorkTimeCodeForPred(require, companySets.getCompanyId(), conditionItemOpt.get());
			if (!workTimeCodeOpt.isPresent()) continue;
			WorkTimeCode workTimeCode = workTimeCodeOpt.get();
			// 日単位の代休控除時間の計算
			result.add(calcCompLeaveDeductTimeEachDay(require, companySets, settingsByFlex,
					employeeSets.getEmployeeId(), baseDate, workInfo, workType, workTimeCode));
			// 時間単位の代休控除時間の計算
			result.add(calcCompLeaveDeductTimeEachTime(settingsByFlex, attendanceTime));
		}
		// 代休控除時間を返す
		return result;
	}
	
	/**
	 * 日単位の代休控除時間の計算
	 * @param require Require
	 * @param companySets 月別集計で必要な会社別設定
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workInfo 日別勤怠の勤務情報
	 * @param workType 勤務種類
	 * @param workTimeCode 就業時間帯コード
	 * @return 代休控除時間
	 */
	private static CompLeaveDeductTime calcCompLeaveDeductTimeEachDay(RequireM1 require, MonAggrCompanySettings companySets,
			SettingRequiredByFlex settingsByFlex, String employeeId, GeneralDate baseDate, WorkInfoOfDailyAttendance workInfo, WorkType workType, WorkTimeCode workTimeCode){
		
		// 所定時間設定を取得
		PredetemineTimeSetting predTimeSet = companySets.getPredetemineTimeSetMap(require, workTimeCode.v());
		if (predTimeSet == null) return new CompLeaveDeductTime();
		PredetermineTimeSetForCalc predetermineTimeSet = PredetermineTimeSetForCalc.convertMastarToCalc(predTimeSet);
		// 所定時間の内訳を取得
		BreakDownTimeDay breakDown = VacationClass.getBreakDownOfPredTime(require, employeeId, baseDate, workInfo,
				VacationCategory.SubstituteHoliday, Optional.of(predetermineTimeSet), Optional.empty());
		// 日単位の所定控除時間の計算
		AttendanceTime forActual = settingsByFlex.getDailyCalcSetOfFlex().getCompLeave().calcDeductPredTimeOfDay(
				workType, false, breakDown, settingsByFlex.getFlexAggrSet());
		AttendanceTime forPremium = settingsByFlex.getDailyCalcSetOfFlex().getCompLeave().calcDeductPredTimeOfDay(
				workType, false, breakDown, settingsByFlex.getFlexAggrSet());
		// 代休控除時間を返す
		return new CompLeaveDeductTime(
				new AttendanceTimeMonth(forActual.valueAsMinutes()),
				new AttendanceTimeMonth(forPremium.valueAsMinutes()));
	}

	/**
	 * 時間単位の代休控除時間の計算
	 * @return 代休控除時間
	 */
	private static AttendanceTimeMonth calcCompLeaveDeductTimeEachTime(
			SettingRequiredByFlex settingsByFlex, AttendanceTimeOfDailyAttendance dailyAttendanceTime){
		
		// 遅刻、早退、外出の相殺時間を取得する
		AttendanceTime offsetTime = dailyAttendanceTime.getTotalOffsetCompLeaveTime();
		// 遅刻、早退、外出の使用時間を取得する
		AttendanceTime useTime = dailyAttendanceTime.getTotalTimeCompLeaveUseTime();
		// 時間単位の所定控除時間の計算
		AttendanceTime deductTime = settingsByFlex.getDailyCalcSetOfFlex().getCompLeave().calcDeductPredTimeOfTime(
				offsetTime, useTime, settingsByFlex.getFlexAggrSet());
		// 代休控除時間を返す
		return new AttendanceTimeMonth(deductTime.valueAsMinutes());
	}
	
	public static interface Require extends RequireM1, ShortageFlexSetting.Require {
		
	}
	
	public static interface RequireM1 extends MonthlyStatutoryLaborDivisionService.Require,
		 MonAggrCompanySettings.RequireM4, WorkInformation.RequireM1, MonAggrCompanySettings.RequireM2,
		 VacationClass.Require {
		
		Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	}
}
