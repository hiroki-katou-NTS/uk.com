package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の遅刻時間
 * @author ken_takasu
 *
 */
@Getter
public class LateTimeOfDaily {
	//遅刻時間
	private TimeWithCalculation lateTime;
	//遅刻控除時間
	private TimeWithCalculation lateDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime exemptionTime;
	
	public LateTimeOfDaily(TimeWithCalculation lateTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime) {		
		this.lateTime = lateTime;
		this.lateDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.exemptionTime = exemptionTime;
	}	

	/**
	 * 遅刻時間のみ更新
	 * @param lateTime
	 */
	public void rePlaceLateTime(TimeWithCalculation lateTime) {
		this.lateTime = lateTime;
	}

	/**
	 * 日別実績の遅刻時間
	 * @param recordClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @param vacationClass 休暇クラス
	 * @param workType 勤務種類
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定
	 * @param leaveLateSet 遅刻早退を控除する
	 * @param recordWorkTimeCode 就業時間帯コード
	 * @return 日別実績の遅刻時間(List)
	 */
	public static List<LateTimeOfDaily> calcList(
			ManageReGetClass recordClass,
			VacationClass vacationClass,
			WorkType workType,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			DeductLeaveEarly leaveLateSet,
			Optional<WorkTimeCode> recordWorkTimeCode) {
		
		//日別実績の遅刻時間
		List<LateTimeOfDaily> lateTime = new ArrayList<>();
		if(recordClass.getCoreTimeSetting().isPresent() && recordClass.getCoreTimeSetting().get().getTimesheet().isNOT_USE()) {
			//コア無しフレックス遅刻時間の計算
			lateTime.add(LateTimeOfDaily.calcNoCoreLateTime(
					recordClass,
					vacationClass,
					workType,
					conditionItem,
					predetermineTimeSetByPersonInfo,
					leaveLateSet,recordWorkTimeCode));
		}else {
			//遅刻時間の計算（時間帯から計算）
			for(TimeLeavingWork work : recordClass.getCalculationRangeOfOneDay().getAttendanceLeavingWork().getTimeLeavingWorks()) {
				lateTime.add(LateTimeOfDaily.calcLateTime(
						recordClass.getCalculationRangeOfOneDay(),
						work.getWorkNo(),
						recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLate(),
						recordClass.getHolidayCalcMethodSet(),
						recordClass.getWorkTimezoneCommonSet()));
			}
		}
		return lateTime;
	}
	
	/**
	 * 遅刻時間の計算
	 * @param oneDay 1日の計算範囲
	 * @param workNo 勤務No
	 * @param late 日別実績の計算区分.遅刻早退の自動計算設定.遅刻
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 日別実績の遅刻時間
	 */
	public static LateTimeOfDaily calcLateTime(
			CalculationRangeOfOneDay oneDay,
			WorkNo workNo,
			boolean late,
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		
		//勤務Noに一致する遅刻時間をListで取得する
		List<LateTimeSheet> lateTimeSheetList = oneDay.getWithinWorkingTimeSheet().isPresent()
				? oneDay.getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
						.map(t -> t.getLateTimeSheet().orElse(null))
						.filter(t -> t != null && workNo.compareTo(t.getWorkNo()) == 0 && t.getForDeducationTimeSheet().isPresent())
						.sorted((lateTimeSheet1,lateTimeSheet2) -> lateTimeSheet1.getForDeducationTimeSheet().get().getTimeSheet().getStart().compareTo(
								lateTimeSheet2.getForDeducationTimeSheet().get().getTimeSheet().getStart()))
						.collect(Collectors.toList())
				: new ArrayList<>();
		
		LateLeaveEarlyTimeSheet forRecordTimeSheet = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));

		LateLeaveEarlyTimeSheet forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));
		
		if(!lateTimeSheetList.isEmpty()) {
			if(lateTimeSheetList.get(0).getForRecordTimeSheet().isPresent()) {
				//遅刻時間帯を１つの時間帯にする。
				forRecordTimeSheet = new LateLeaveEarlyTimeSheet(
						new TimeSpanForDailyCalc(
								lateTimeSheetList.get(0).getForRecordTimeSheet().get().getTimeSheet().getStart(),
								lateTimeSheetList.get(lateTimeSheetList.size()-1).getForRecordTimeSheet().get().getTimeSheet().getEnd()),
						lateTimeSheetList.get(0).getForRecordTimeSheet().get().getRounding());
				
				forRecordTimeSheet.setDeductionTimeSheet(
						lateTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
				forRecordTimeSheet.setRecordedTimeSheet(
						lateTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			}
			forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
					new TimeSpanForDailyCalc(
							lateTimeSheetList.get(0).getForDeducationTimeSheet().get().getTimeSheet().getStart(),
							lateTimeSheetList.get(lateTimeSheetList.size()-1).getForDeducationTimeSheet().get().getTimeSheet().getEnd()),
					lateTimeSheetList.get(0).getForDeducationTimeSheet().get().getRounding());
			
			forDeductTimeSheet.setDeductionTimeSheet(
					lateTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			forDeductTimeSheet.setRecordedTimeSheet(
					lateTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
		}
		
		LateTimeSheet lateTimeSheet = new LateTimeSheet(Optional.of(forRecordTimeSheet), Optional.of(forDeductTimeSheet), workNo.v(), Optional.empty());
		
		NotUseAtr notDeductLateLeaveEarly = NotUseAtr.NOT_USE;
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
			if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
				notDeductLateLeaveEarly = NotUseAtr.USE;
			}
		}
		//遅刻計上時間の計算
		TimeWithCalculation lateTime = lateTimeSheet.calcForRecordTime(late);
		//遅刻控除時間の計算
		TimeWithCalculation lateDeductionTime = lateTimeSheet.calcDedctionTime(late,notDeductLateLeaveEarly);
		
		LateTimeOfDaily lateTimeOfDaily = new LateTimeOfDaily(
				lateTime,
				lateDeductionTime,
				workNo,
				TimevacationUseTimeOfDaily.defaultValue(),
				IntervalExemptionTime.defaultValue());
		return lateTimeOfDaily;
	}
	
	/**
	 * コア無しフレックス遅刻時間の計算
	 * @param recordClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @param vacationClass 休暇クラス
	 * @param workType 勤務種類
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定
	 * @param leaveLateSet 遅刻早退を控除する
	 * @param recordWorkTimeCode 就業時間帯コード
	 * @return 日別実績の遅刻時間
	 */
	public static LateTimeOfDaily calcNoCoreLateTime(
			ManageReGetClass recordClass,
			VacationClass vacationClass,
			WorkType workType,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			DeductLeaveEarly leaveLateSet,
			Optional<WorkTimeCode> recordWorkTimeCode) {
		
		//コアタイム無し（時間帯を使わずに計算）
		FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get();

		//計上用のコアタイム無しの遅刻時間計算
		TimeWithCalculation calcedLateTime = changedFlexTimeSheet.calcNoCoreCalcLateTime(
				DeductionAtr.Appropriate, 
				PremiumAtr.RegularWork,
				recordClass.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
				vacationClass,
				recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getTimeVacationAdditionRemainingTime().get(),
				StatutoryDivision.Nomal,
				workType,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordWorkTimeCode,
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				recordClass.getHolidayCalcMethodSet(),
				recordClass.getCoreTimeSetting(),
				recordClass.getDailyUnit(),
				recordClass.getWorkTimezoneCommonSet(),
				conditionItem,
				predetermineTimeSetByPersonInfo,
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.of(leaveLateSet),
				NotUseAtr.NOT_USE);
		
		//控除用コアタイム無しの遅刻時間計算
		TimeWithCalculation calcedLateDeductionTime = changedFlexTimeSheet.calcNoCoreCalcLateTime(
				DeductionAtr.Deduction,
				PremiumAtr.RegularWork,
				recordClass.getAddSetting().getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
				vacationClass,
				recordClass.getCalculationRangeOfOneDay().getWithinWorkingTimeSheet().get().getTimeVacationAdditionRemainingTime().get(),
				StatutoryDivision.Nomal,
				workType,
				recordClass.getCalculationRangeOfOneDay().getPredetermineTimeSetForCalc(),
				recordWorkTimeCode,
				recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting(),
				recordClass.getAddSetting(),
				recordClass.getHolidayAddtionSet().get(),
				recordClass.getHolidayCalcMethodSet(),
				recordClass.getCoreTimeSetting(),
				recordClass.getDailyUnit(),
				recordClass.getWorkTimezoneCommonSet(),
				conditionItem,
				predetermineTimeSetByPersonInfo,
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.of(leaveLateSet),
				NotUseAtr.NOT_USE);
		
		return new LateTimeOfDaily(calcedLateTime, calcedLateDeductionTime, new WorkNo(1), TimevacationUseTimeOfDaily.defaultValue(), IntervalExemptionTime.defaultValue());
	}
	
	/**
	 * 遅刻時間のエラーチェック 
	 * @return エラーである。
	 */
	public List<EmployeeDailyPerError>  checkError(String employeeId,
												  GeneralDate targetDate,
												  String searchWord,
												  AttendanceItemDictionaryForCalc attendanceItemDictionary,
												  ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getLateTime().getTime().greaterThan(0)) {
			val itemId = attendanceItemDictionary.findId(searchWord+this.workNo.v());
			if(itemId.isPresent())
				returnErrorList.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
		}
		return returnErrorList;
	}
	
	/**
	 * 休暇加算時間の計算
	 * @return
	 */
	public int calcVacationAddTime(Optional<HolidayAddtionSet> holidayAddtionSet) {
		int result = 0;	
		int totalAddTime = this.timePaidUseTime.calcTotalVacationAddTime(holidayAddtionSet, AdditionAtr.WorkingHoursOnly);	
		if(this.lateTime.getCalcTime().lessThanOrEqualTo(totalAddTime)) {
			result = this.lateTime.getCalcTime().valueAsMinutes();
		}else {
			result = totalAddTime;
		}
		return result;
	}
}