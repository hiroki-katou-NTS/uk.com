package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.VacationClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.AdditionAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;


/**
 * 日別勤怠の早退時間
 * @author ken_takasu
 *
 */
@Getter
public class LeaveEarlyTimeOfDaily {
	//早退時間
	private TimeWithCalculation leaveEarlyTime;
	//早退控除時間
	private TimeWithCalculation leaveEarlyDeductionTime;
	//勤務No
	private WorkNo workNo;
	//休暇使用時間
	private TimevacationUseTimeOfDaily timePaidUseTime;
	//インターバル時間
	private IntervalExemptionTime intervalTime;
	
	
	public LeaveEarlyTimeOfDaily(TimeWithCalculation leaveEarlyTime, TimeWithCalculation lateDeductionTime, WorkNo workNo,
			TimevacationUseTimeOfDaily timePaidUseTime, IntervalExemptionTime exemptionTime) {
		this.leaveEarlyTime = leaveEarlyTime;
		this.leaveEarlyDeductionTime = lateDeductionTime;
		this.workNo = workNo;
		this.timePaidUseTime = timePaidUseTime;
		this.intervalTime = exemptionTime;
	}
	
	/**
	 * 早退時間のみ更新
	 * @param leaveEarlyTime
	 */
	public void rePlaceLeaveEarlyTime(TimeWithCalculation leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	public static LeaveEarlyTimeOfDaily noLeaveEarlyTimeOfDaily() {
		return new LeaveEarlyTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 TimeWithCalculation.sameTime(new AttendanceTime(0)),
										 new WorkNo(1),
										 TimevacationUseTimeOfDaily.defaultValue(),
										 IntervalExemptionTime.defaultValue());
	}
	
	/**
	 * 日別実績の早退時間
	 * @param recordClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @param vacationClass 休暇クラス
	 * @param workType 勤務種類
	 * @param conditionItem 労働条件項目
	 * @param predetermineTimeSetByPersonInfo 計算用所定時間設定
	 * @param leaveLateSet 遅刻早退を控除する
	 * @param recordWorkTimeCode 就業時間帯コード
	 * @return 日別実績の早退時間(List)
	 */
	public static List<LeaveEarlyTimeOfDaily> calcList(
			ManageReGetClass recordClass,
			VacationClass vacationClass,
			WorkType workType,
			WorkingConditionItem conditionItem,
			Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
			DeductLeaveEarly leaveLateSet,
			Optional<WorkTimeCode> recordWorkTimeCode) {
		
		//日別実績の早退時間
		List<LeaveEarlyTimeOfDaily> leaveEarlyTime = new ArrayList<>();
		if(recordClass.getCoreTimeSetting().isPresent() && recordClass.getCoreTimeSetting().get().getTimesheet().isNOT_USE()) {
			//こちらのケースは早退は常に0：00
			leaveEarlyTime.add(LeaveEarlyTimeOfDaily.noLeaveEarlyTimeOfDaily());
		}else {
			//早退（時間帯から計算）
			for(TimeLeavingWork work : recordClass.getCalculationRangeOfOneDay().getAttendanceLeavingWork().getTimeLeavingWorks()) {
				leaveEarlyTime.add(LeaveEarlyTimeOfDaily.calcLeaveEarlyTime(
						recordClass.getCalculationRangeOfOneDay(),
						work.getWorkNo(),
						recordClass.getIntegrationOfDaily().getCalAttr().getLeaveEarlySetting().isLeaveEarly(),
						recordClass.getHolidayCalcMethodSet(),
						recordClass.getWorkTimezoneCommonSet()));
			}
		}
		return leaveEarlyTime;
	}
	
	/**
	 * 早退時間の計算
	 * @param oneDay 1日の計算範囲
	 * @param workNo 勤務No
	 * @param leaveEarly 日別実績の計算区分.遅刻早退の自動計算設定.早退
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 日別実績の早退時間
	 */
	public static LeaveEarlyTimeOfDaily calcLeaveEarlyTime(
			CalculationRangeOfOneDay oneDay,
			WorkNo workNo,
			boolean leaveEarly, //日別実績の計算区分.遅刻早退の自動計算設定.早退
			HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<WorkTimezoneCommonSet> commonSetting) {
		
		//勤務Noに一致する早退時間をListで取得する
		List<LeaveEarlyTimeSheet> leaveEarlyTimeSheetList = oneDay.getWithinWorkingTimeSheet().isPresent()
				? oneDay.getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
						.map(t -> t.getLeaveEarlyTimeSheet().orElse(null))
						.filter(t -> t != null && workNo.compareTo(t.getWorkNo()) == 0 && t.getForDeducationTimeSheet().isPresent())
						.sorted((leaveEarlyTimeSheet1,leaveEarlyTimeSheet2) -> leaveEarlyTimeSheet1.getForDeducationTimeSheet().get().getTimeSheet().getStart().compareTo(
								leaveEarlyTimeSheet2.getForDeducationTimeSheet().get().getTimeSheet().getStart()))
						.collect(Collectors.toList())
				: new ArrayList<>();
		
		LateLeaveEarlyTimeSheet forRecordTimeSheet = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));

		LateLeaveEarlyTimeSheet forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
				new TimeSpanForDailyCalc(new TimeWithDayAttr(0),new TimeWithDayAttr(0)),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));
		
		if(!leaveEarlyTimeSheetList.isEmpty()) {
			if(leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().isPresent()) {
				//早退時間帯を１つの時間帯にする。
				forRecordTimeSheet = new LateLeaveEarlyTimeSheet(
						new TimeSpanForDailyCalc(
								leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().get().getTimeSheet().getStart(),
								leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForRecordTimeSheet().get().getTimeSheet().getEnd()),
						leaveEarlyTimeSheetList.get(0).getForRecordTimeSheet().get().getRounding());
				
				forRecordTimeSheet.setDeductionTimeSheet(
						leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
				forRecordTimeSheet.setRecordedTimeSheet(
						leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForRecordTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			}
			forDeductTimeSheet = new LateLeaveEarlyTimeSheet(
					new TimeSpanForDailyCalc(
							leaveEarlyTimeSheetList.get(0).getForDeducationTimeSheet().get().getTimeSheet().getStart(),
							leaveEarlyTimeSheetList.get(leaveEarlyTimeSheetList.size()-1).getForDeducationTimeSheet().get().getTimeSheet().getEnd()),
					leaveEarlyTimeSheetList.get(0).getForDeducationTimeSheet().get().getRounding());
			
			forDeductTimeSheet.setDeductionTimeSheet(
					leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
			forDeductTimeSheet.setRecordedTimeSheet(
					leaveEarlyTimeSheetList.stream().flatMap(t -> t.getForDeducationTimeSheet().get().getDeductionTimeSheet().stream()).collect(Collectors.toList()));
		}
		
		LeaveEarlyTimeSheet leaveEarlyTimeSheet = new LeaveEarlyTimeSheet(Optional.of(forRecordTimeSheet), Optional.of(forDeductTimeSheet), workNo.v(), Optional.empty());
		
		NotUseAtr notDeductLateLeaveEarly = NotUseAtr.NOT_USE;
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()) {
			if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().isDeductLateLeaveEarly(commonSetting)) {
				notDeductLateLeaveEarly = NotUseAtr.USE;
			}
		}
		
		//早退計上時間の計算
		TimeWithCalculation leaveEarlyTime = leaveEarlyTimeSheet.calcForRecordTime(leaveEarly);
		//早退控除時間の計算
		TimeWithCalculation leaveEarlyDeductionTime = leaveEarlyTimeSheet.calcDedctionTime(leaveEarly,notDeductLateLeaveEarly);
		
		LeaveEarlyTimeOfDaily LeaveEarlyTimeOfDaily = new LeaveEarlyTimeOfDaily(
				leaveEarlyTime,
				leaveEarlyDeductionTime,
				workNo,
				TimevacationUseTimeOfDaily.defaultValue(),
				IntervalExemptionTime.defaultValue());
		return LeaveEarlyTimeOfDaily;
	}
	
	/**
	 * 早退時間のエラーチェック 
	 * @return エラーである。
	 */
	public List<EmployeeDailyPerError>  checkError(String employeeId,
												  GeneralDate targetDate,
												  String searchWord,
												  AttendanceItemDictionaryForCalc attendanceItemDictionary,
												  ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorList = new ArrayList<>();
		if(this.getLeaveEarlyTime().getTime().greaterThan(0)) {
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
		if(this.leaveEarlyTime.getCalcTime().lessThanOrEqualTo(totalAddTime)) {
			result = this.leaveEarlyTime.getCalcTime().valueAsMinutes();
		}else {
			result = totalAddTime;
		}
		return result;
	}
}
