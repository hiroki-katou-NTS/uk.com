package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workingtime.StayingTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 
 * @author nampt
 * 日別実績の勤怠時間 - root
 *
 */
@Getter
public class AttendanceTimeOfDailyPerformance extends AggregateRoot {

	//社員ID
	private String employeeId;
	
	//年月日
	private GeneralDate ymd;
	
	//勤務予定時間 - 日別実績の勤務予定時間
	private WorkScheduleTimeOfDaily workScheduleTimeOfDaily;
	
	//実働時間/実績時間  - 日別実績の勤務実績時間
	private ActualWorkingTimeOfDaily actualWorkingTimeOfDaily;
	
	//滞在時間 - 日別実績の滞在時間 change tyle
	private StayingTimeOfDaily stayingTime;
	
	//不就労時間 - 勤怠時間
	private AttendanceTime unEmployedTime;
	
	//予実差異時間 - 勤怠時間
	private AttendanceTime budgetTimeVariance;
	
	//医療時間 - 日別実績の医療時間
	private MedicalCareTimeOfDaily medicalCareTime;
	
	
//	public AttendanceTimeOfDailyPerformance createFromJavaType() {
//		
//	}
	
	/**
	 * Constructor
	 * @param actualWorkingTimeOfDaily
	 */
	private AttendanceTimeOfDailyPerformance(ActualWorkingTimeOfDaily actualWorkingTimeOfDaily) {
		this.actualWorkingTimeOfDaily = actualWorkingTimeOfDaily;
	}
	
	public AttendanceTimeOfDailyPerformance (String employeeId,
											 GeneralDate ymd,
											 WorkScheduleTimeOfDaily schedule,
											 ActualWorkingTimeOfDaily actual,
											 StayingTimeOfDaily stay,
											 AttendanceTime budget,
											 AttendanceTime unEmploy) {
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workScheduleTimeOfDaily = schedule;
		this.actualWorkingTimeOfDaily = actual;
		this.stayingTime = stay;
		this.budgetTimeVariance = budget;
		this.unEmployedTime = unEmploy;
	}
	
	public AttendanceTimeOfDailyPerformance(String employeeId, GeneralDate ymd,
			WorkScheduleTimeOfDaily workScheduleTimeOfDaily, ActualWorkingTimeOfDaily actualWorkingTimeOfDaily,
			StayingTimeOfDaily stayingTime, AttendanceTime unEmployedTime, AttendanceTime budgetTimeVariance,
			MedicalCareTimeOfDaily medicalCareTime) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workScheduleTimeOfDaily = workScheduleTimeOfDaily;
		this.actualWorkingTimeOfDaily = actualWorkingTimeOfDaily;
		this.stayingTime = stayingTime;
		this.unEmployedTime = unEmployedTime;
		this.budgetTimeVariance = budgetTimeVariance;
		this.medicalCareTime = medicalCareTime;
	}
	
	/**
	 * 日別実績の勤怠時間の計算
	 * @param oneDay 1日の範囲クラス
	 * @param schePreTimeSet 
	 * @param ootsukaFixCalsSet 
	 * @return 日別実績(Work)クラス
	 */
	public static IntegrationOfDaily calcTimeResult(CalculationRangeOfOneDay oneDay,IntegrationOfDaily integrationOfDaily,AutoCalOvertimeSetting overTimeAutoCalcSet,AutoCalSetting holidayAutoCalcSetting,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType workType,
			   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   WorkingSystem workingSystem,
			   AddSettingOfIrregularWork addSettingOfIrregularWork,
			   AddSettingOfFlexWork addSettingOfFlexWork,
			   AddSettingOfRegularWork addSettingOfRegularWork,
			   VacationAddTimeSet vacationAddTimeSet,
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
			   WorkTimeDailyAtr workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   AutoCalRaisingSalarySetting raisingAutoCalcSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			   Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDaily,
			   Optional<PCLogOnInfoOfDaily> pCLogOnInfoOfDaily,
			   Optional<TimeLeavingOfDailyPerformance> attendanceLeave,
			   DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			   List<DivergenceTime> divergenceTimeList,
			   CalculateOfTotalConstraintTime calculateOfTotalConstraintTime, 
			   Optional<PredetermineTimeSetForCalc> schePreTimeSet, Optional<FixedWorkCalcSetting> ootsukaFixCalsSet,
			   Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSetting) {
		integrationOfDaily.setAttendanceTimeOfDailyPerformance(Optional.of(collectCalculationResult(oneDay,oneDay, overTimeAutoCalcSet,holidayAutoCalcSetting,
				   																		personalCondition,
				   																		 vacationClass,
				   																		 workType,
				   																		 late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   																		 leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				   																		 workingSystem,
				   																		 addSettingOfIrregularWork,
				   																		 addSettingOfFlexWork,
				   																		 addSettingOfRegularWork,
				   																		 vacationAddTimeSet,
				   																		 overTimeAutoCalcAtr,
				   																		 workTimeDailyAtr,
				   																		 flexCalcMethod,
				   																		 holidayCalcMethodSet,
				   																	     raisingAutoCalcSet,
				   																	     bonusPayAutoCalcSet,
				   																	     calcAtrOfDaily,
				   																	     eachWorkTimeSet,
				   																	     eachCompanyTimeSet,
				   																	     attendanceLeavingGateOfDaily,
				   																	     pCLogOnInfoOfDaily,
				   																	     attendanceLeave,
				   																	     forCalcDivergenceDto,
				   																	     divergenceTimeList,
				   																	     calculateOfTotalConstraintTime,
				   																	     schePreTimeSet,
				   																	     integrationOfDaily.getBreakTime().size(),
				   																	     ootsukaFixCalsSet,
				   																	     fixRestTimeSetting)));
		
		return integrationOfDaily;
	}
	
	/**
	 * 時間の計算結果をまとめて扱う
	 * @param schePreTimeSet 
	 * @param breakTimeCount 
	 * @param ootsukaFixCalsSet 
	 * @param 1日の範囲クラス
	 */
	private static AttendanceTimeOfDailyPerformance collectCalculationResult(CalculationRangeOfOneDay recordOneDay,CalculationRangeOfOneDay scheduleOneDay,
			   AutoCalOvertimeSetting overTimeAutoCalcSet,
			   AutoCalSetting holidayAutoCalcSetting,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType workType,
			   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   WorkingSystem workingSystem,
			   AddSettingOfIrregularWork addSettingOfIrregularWork,
			   AddSettingOfFlexWork addSettingOfFlexWork,
			   AddSettingOfRegularWork addSettingOfRegularWork,
			   VacationAddTimeSet vacationAddTimeSet,
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
			   WorkTimeDailyAtr workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   AutoCalRaisingSalarySetting raisingAutoCalcSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			   Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDaily,
			   Optional<PCLogOnInfoOfDaily> pCLogOnInfoOfDaily,
			   Optional<TimeLeavingOfDailyPerformance> attendanceLeave,
			   DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			   List<DivergenceTime> divergenceTimeList,
			   CalculateOfTotalConstraintTime calculateOfTotalConstraintTime, Optional<PredetermineTimeSetForCalc> schePreTimeSet, int breakTimeCount,
			   Optional<FixedWorkCalcSetting> ootsukaFixCalsSet,
			   Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSetting
			   ) {
		
		/*日別実績の勤務予定時間の計算*/
		//実績,予定で渡す予定(今は実績のみ渡してる)　****要修正***
		val workScheduleTime = calcWorkSheduleTime(recordOneDay,scheduleOneDay,schePreTimeSet, workType);
		/*日別実績の実績時間の計算*/
		val actualWorkingTimeOfDaily = ActualWorkingTimeOfDaily.calcRecordTime(recordOneDay,overTimeAutoCalcSet,holidayAutoCalcSetting,personalCondition,
				    vacationClass,
				    workType,
				    late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				    leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				    workingSystem,
				    addSettingOfIrregularWork,
				    addSettingOfFlexWork,
				    addSettingOfRegularWork,
				    vacationAddTimeSet,
				    overTimeAutoCalcAtr,
				    workTimeDailyAtr,
				    flexCalcMethod,
				    holidayCalcMethodSet,
					raisingAutoCalcSet,
					bonusPayAutoCalcSet,
					calcAtrOfDaily,
					eachWorkTimeSet,
					eachCompanyTimeSet,
					forCalcDivergenceDto,
					divergenceTimeList
					/*計画所定時間*/
					/*実績所定労働時間*/
					/*勤務予定時間の計算*/,
					schePreTimeSet,
					breakTimeCount,
					ootsukaFixCalsSet,
					fixRestTimeSetting);
		

		/*滞在時間の計算*/
		StayingTimeOfDaily stayingTime = new StayingTimeOfDaily(pCLogOnInfoOfDaily.isPresent()?pCLogOnInfoOfDaily.get().calcPCLogOnCalc(attendanceLeave,GoLeavingWorkAtr.LEAVING_WORK):new AttendanceTime(0),
																pCLogOnInfoOfDaily.isPresent()?pCLogOnInfoOfDaily.get().calcPCLogOnCalc(attendanceLeave,GoLeavingWorkAtr.GO_WORK):new AttendanceTime(0),
																attendanceLeavingGateOfDaily.isPresent()?attendanceLeavingGateOfDaily.get().calcBeforeAttendanceTime(attendanceLeave,GoLeavingWorkAtr.GO_WORK):new AttendanceTime(0),
																StayingTimeOfDaily.calcStayingTimeOfDaily(attendanceLeavingGateOfDaily,pCLogOnInfoOfDaily,attendanceLeave,calculateOfTotalConstraintTime),
																attendanceLeavingGateOfDaily.isPresent()?attendanceLeavingGateOfDaily.get().calcBeforeAttendanceTime(attendanceLeave,GoLeavingWorkAtr.LEAVING_WORK):new AttendanceTime(0));
			
		/*不就労時間*/
		val unEmployedTime = new AttendanceTime(0);
		/*予定差異時間の計算*/
		val budgetTimeVariance = new AttendanceTime(0);
		/*医療時間*/
		val medicalCareTime = new MedicalCareTimeOfDaily(WorkTimeNightShift.DAY_SHIFT,
														 new AttendanceTime(0),
														 new AttendanceTime(0),
														 new AttendanceTime(0));

//      下書き(ここに持ってくる前に書いていたコード達)
//		OverTimeWorkOfDaily overTimeWorkTime = overTimeWorkSheet.calcOverTimeWork()；/*残業時間の計算*/
//		int totalOverTimeWorkTime = overTimeWorkTime.getOverTimeWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*残業時間の計算*/
//		HolidayWorkTimeOfDaily holidayWorkTime = holidayWorkTimeSheet.calcHolidayWorkTime();/*休日出勤の計算*/
//		int totalHolidayWorkTime = holidayWorkTime.getHolidayWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*休日出勤の計算*/
//		int deductionBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Deduction);/*休憩時間の計算*/
//		int recordBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Appropriate);/*計上用の休憩時間の計算*/
////		int deductionGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Deduction);/*控除用の外出時間の計算*/
////		int recordGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Appropriate);/*計上用の外出時間の計算*/
//		return /*法定労働時間*/ - calcWithinWorkTime;
		
		return new AttendanceTimeOfDailyPerformance(recordOneDay.getWorkInformationOfDaily().getEmployeeId(),
													recordOneDay.getAttendanceLeavingWork().getYmd(),
													workScheduleTime,
													actualWorkingTimeOfDaily,
													stayingTime,
													unEmployedTime,
													budgetTimeVariance,
													medicalCareTime);
		//return new AttendanceTimeOfDailyPerformance(ActualWorkingTimeOfDaily.calcRecordTime(oneDay));
		
	}
	
	/**
	 * 計画所定の算出
	 * @param recordOneDay　実績の1日の範囲クラス
	 * @param scheduleOneDay　予定の1日の範囲クラス
	 * @param schePreTime　労働条件の個人勤務区分別の就時コードから取得した所定時間クラス
	 * @param workType
	 * @return
	 */
	private static WorkScheduleTimeOfDaily calcWorkSheduleTime(CalculationRangeOfOneDay recordOneDay, CalculationRangeOfOneDay scheduleOneDay,
															   Optional<PredetermineTimeSetForCalc> schePreTime,WorkType workType) {
		//計画所定時間の計算
		//実績所定労働時間の計算
		val actualPredWorkTime = (recordOneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode() == null)?new AttendanceTime(0):recordOneDay.getPredetermineTimeSetForCalc().getpredetermineTime(workType.getDailyWork());
		//勤務予定時間を計算
		val shedulePreWorkTime = (schePreTime.isPresent())? schePreTime.get().getpredetermineTime(workType.getDailyWork()):new AttendanceTime(0);
		//val workSheduleTime
		return new WorkScheduleTimeOfDaily(new WorkScheduleTime(new AttendanceTime(510),new AttendanceTime(0),new AttendanceTime(510)),shedulePreWorkTime,actualPredWorkTime);
	}

	/**
	 * エラーチェックの指示メソッド 
	 * @param attendanceItemConverter 
	 * @return 社員のエラーチェック一覧
	 */
	public List<EmployeeDailyPerError> getErrorList(String employeeId,GeneralDate targetDate,
			   										SystemFixedErrorAlarm fixedErrorAlarmCode, CheckExcessAtr checkAtr) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getActualWorkingTimeOfDaily() != null) {
			getActualWorkingTimeOfDaily().requestCheckError(employeeId, targetDate, fixedErrorAlarmCode, checkAtr);
		}
		return returnErrorItem;
	}
	


	
}
