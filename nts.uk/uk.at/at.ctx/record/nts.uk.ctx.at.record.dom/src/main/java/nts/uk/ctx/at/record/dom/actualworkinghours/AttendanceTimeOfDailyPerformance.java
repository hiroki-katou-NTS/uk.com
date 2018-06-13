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
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
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
	private AttendanceTimeOfExistMinus unEmployedTime;
	
	//予実差異時間 - 勤怠時間
	private AttendanceTimeOfExistMinus budgetTimeVariance;
	
	//医療時間 - 日別実績の医療時間
	private MedicalCareTimeOfDaily medicalCareTime;
	
	
	public AttendanceTimeOfDailyPerformance (String employeeId,
											 GeneralDate ymd,
											 WorkScheduleTimeOfDaily schedule,
											 ActualWorkingTimeOfDaily actual,
											 StayingTimeOfDaily stay,
											 AttendanceTimeOfExistMinus budget,
											 AttendanceTimeOfExistMinus unEmploy) {
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
			StayingTimeOfDaily stayingTime, AttendanceTimeOfExistMinus unEmployedTime, AttendanceTimeOfExistMinus budgetTimeVariance,
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
	
	
	public AttendanceTimeOfDailyPerformance inssertActualWorkingTimeOfDaily(ActualWorkingTimeOfDaily time) {
		return new AttendanceTimeOfDailyPerformance(this.employeeId, this.ymd, this.workScheduleTimeOfDaily, time, this.stayingTime, this.budgetTimeVariance, this.unEmployedTime); 
	}
	
	
	/**
	 * 日別実績の勤怠時間の計算
	 * @param oneDay 1日の範囲クラス
	 * @param schePreTimeSet 
	 * @param ootsukaFixCalsSet 
	 * @param scheduleReGetClass 
	 * @param integrationOfDaily2 
	 * @return 日別実績(Work)クラス
	 */
	public static IntegrationOfDaily calcTimeResult(CalculationRangeOfOneDay recordOneDay,
			   CalculationRangeOfOneDay scheOneDay,
			   IntegrationOfDaily integrationOfDaily,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType recordWorkType,
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   AutoCalAtrOvertime overTimeAutoCalcAtr,
			   Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
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
			   Optional<PredetermineTimeSetForCalc> schePreTimeSet, 
			   Optional<FixedWorkCalcSetting> ootsukaFixCalsSet,
			   Optional<FixRestTimezoneSet> fixRestTimeSetting,
			   Optional<WorkType> scheWorkType,
			   AutoCalFlexOvertimeSetting flexAutoCalSet,
			   DailyUnit dailyUnit,
			   int breakCount,Optional<CoreTimeSetting> coreTimeSetting) {
		integrationOfDaily.setAttendanceTimeOfDailyPerformance(Optional.of(collectCalculationResult(recordOneDay,scheOneDay,
				   																		personalCondition,
				   																		 vacationClass,
				   																		 recordWorkType,
				   																		 workingSystem,
				   																		 illegularAddSetting,
				   																		 flexAddSetting,
				   																		 regularAddSetting,
				   																		 holidayAddtionSet,
				   																		 overTimeAutoCalcAtr,
				   																		 workTimeDailyAtr,
				   																		 flexCalcMethod,
				   																		 holidayCalcMethodSet,
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
				   																	     breakCount,
				   																	     ootsukaFixCalsSet,
				   																	     fixRestTimeSetting,
				   																	     integrationOfDaily, 
				   																	     scheWorkType,
				   																	     flexAutoCalSet,
				   																	     dailyUnit,coreTimeSetting
				   																	     )));
		
		return integrationOfDaily;
	}
	
	/**
	 * 時間の計算結果をまとめて扱う
	 * @param schePreTimeSet 
	 * @param breakTimeCount 
	 * @param ootsukaFixCalsSet 
	 * @param integrationOfDaily 
	 * @param flexSetting 
	 * @param 1日の範囲クラス
	 */
	private static AttendanceTimeOfDailyPerformance collectCalculationResult(CalculationRangeOfOneDay recordOneDay,CalculationRangeOfOneDay scheduleOneDay,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType recordWorkType,
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   AutoCalAtrOvertime overTimeAutoCalcAtr,
			   Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
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
			   Optional<FixRestTimezoneSet> fixRestTimeSetting, IntegrationOfDaily integrationOfDaily,
			   Optional<WorkType> scheWorkType, 
			   AutoCalFlexOvertimeSetting flexSetting,
			   DailyUnit dailyUnit,Optional<CoreTimeSetting> coreTimeSetting
			   ) {
		
		/*日別実績の勤務予定時間の計算*/
		//実績,予定で渡す予定(今は実績のみ渡してる)　****要修正***
		val workScheduleTime = calcWorkSheduleTime(recordOneDay,scheduleOneDay,schePreTimeSet, recordWorkType,scheWorkType, 
												   personalCondition, vacationClass, workingSystem, 
												   illegularAddSetting, flexAddSetting, regularAddSetting, holidayAddtionSet, overTimeAutoCalcAtr, 
												   workTimeDailyAtr, flexCalcMethod, holidayCalcMethodSet, bonusPayAutoCalcSet, 
												   calcAtrOfDaily, eachWorkTimeSet, eachCompanyTimeSet, breakTimeCount, integrationOfDaily, 
												   flexSetting, coreTimeSetting);
		/*日別実績の実績時間の計算*/
		val actualWorkingTimeOfDaily = ActualWorkingTimeOfDaily.calcRecordTime(recordOneDay,personalCondition,
				    vacationClass,
				    recordWorkType,
				    workingSystem,
				    illegularAddSetting,
				    flexAddSetting,
				    regularAddSetting,
				    holidayAddtionSet,
				    overTimeAutoCalcAtr,
				    workTimeDailyAtr,
				    flexCalcMethod,
				    holidayCalcMethodSet,
					bonusPayAutoCalcSet,
					calcAtrOfDaily,
					eachWorkTimeSet,
					eachCompanyTimeSet,
					forCalcDivergenceDto,
					divergenceTimeList,
					schePreTimeSet,
					breakTimeCount,
					ootsukaFixCalsSet,
					fixRestTimeSetting,
					integrationOfDaily,
					scheWorkType, 
					flexSetting, 
					dailyUnit,
					workScheduleTime,coreTimeSetting);
		

		/*滞在時間の計算*/
		StayingTimeOfDaily stayingTime = new StayingTimeOfDaily(pCLogOnInfoOfDaily.isPresent()?pCLogOnInfoOfDaily.get().calcPCLogOnCalc(attendanceLeave,GoLeavingWorkAtr.LEAVING_WORK):new AttendanceTime(0),
																pCLogOnInfoOfDaily.isPresent()?pCLogOnInfoOfDaily.get().calcPCLogOnCalc(attendanceLeave,GoLeavingWorkAtr.GO_WORK):new AttendanceTime(0),
																attendanceLeavingGateOfDaily.isPresent()?attendanceLeavingGateOfDaily.get().calcBeforeAttendanceTime(attendanceLeave,GoLeavingWorkAtr.GO_WORK):new AttendanceTime(0),
																StayingTimeOfDaily.calcStayingTimeOfDaily(attendanceLeavingGateOfDaily,pCLogOnInfoOfDaily,attendanceLeave,calculateOfTotalConstraintTime),
																attendanceLeavingGateOfDaily.isPresent()?attendanceLeavingGateOfDaily.get().calcBeforeAttendanceTime(attendanceLeave,GoLeavingWorkAtr.LEAVING_WORK):new AttendanceTime(0));
			
		/*不就労時間*/
		val unEmployedTime = new AttendanceTimeOfExistMinus(0);
		/*予定差異時間の計算*/
		val budgetTimeVariance = new AttendanceTimeOfExistMinus(0);
		/*医療時間*/
		val medicalCareTime = new MedicalCareTimeOfDaily(WorkTimeNightShift.DAY_SHIFT,
														 new AttendanceTime(0),
														 new AttendanceTime(0),
														 new AttendanceTime(0));

		return new AttendanceTimeOfDailyPerformance(recordOneDay.getWorkInformationOfDaily().getEmployeeId(),
													recordOneDay.getAttendanceLeavingWork().getYmd(),
													workScheduleTime,
													actualWorkingTimeOfDaily,
													stayingTime,
													unEmployedTime,
													budgetTimeVariance,
													medicalCareTime);
		
	}
	
	/**
	 * 計画所定の算出
	 * @param recordOneDay　実績の1日の範囲クラス
	 * @param scheduleOneDay　予定の1日の範囲クラス
	 * @param schePreTime　労働条件の個人勤務区分別の就時コードから取得した所定時間クラス
	 * @param workType
	 * @param scheWorkType 
	 * @param overTimeAutoCalcSet 
	 * @param holidayAutoCalcSetting 
	 * @param personalCondition 
	 * @param vacationClass 
	 * @param late 
	 * @param leaveEarly 
	 * @param workingSystem 
	 * @param illegularAddSetting 
	 * @param flexAddSetting 
	 * @param regularAddSetting 
	 * @param holidayAddtionSet 
	 * @param overTimeAutoCalcAtr 
	 * @param workTimeDailyAtr 
	 * @param flexCalcMethod 
	 * @param holidayCalcMethodSet 
	 * @param raisingAutoCalcSet 
	 * @param bonusPayAutoCalcSet 
	 * @param calcAtrOfDaily 
	 * @param eachWorkTimeSet 
	 * @param eachCompanyTimeSet 
	 * @param breakTimeCount 
	 * @param integrationOfDaily 
	 * @param flexAutoCalSet 
	 * @param coreTimeSetting 
	 * @return
	 */
	private static WorkScheduleTimeOfDaily calcWorkSheduleTime(CalculationRangeOfOneDay recordOneDay, CalculationRangeOfOneDay scheduleOneDay,
															   Optional<PredetermineTimeSetForCalc> schePreTime,WorkType workType, Optional<WorkType> scheWorkType, 
															   Optional<PersonalLaborCondition> personalCondition, VacationClass vacationClass, 
															   WorkingSystem workingSystem, 
															   WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
															   WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet, 
															   AutoCalAtrOvertime overTimeAutoCalcAtr, Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
															   Optional<SettingOfFlexWork> flexCalcMethod, HolidayCalcMethodSet holidayCalcMethodSet, 
															   BonusPayAutoCalcSet bonusPayAutoCalcSet, 
															   CalAttrOfDailyPerformance calcAtrOfDaily, List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet, 
															   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet, int breakTimeCount, 
															   IntegrationOfDaily integrationOfDaily, AutoCalFlexOvertimeSetting flexAutoCalSet, 
															   Optional<CoreTimeSetting> coreTimeSetting) {
		//勤務予定時間を計算
		val totalWorkingTime = TotalWorkingTime.calcAllDailyRecord(scheduleOneDay,
																   personalCondition, 
																   vacationClass, 
																   workType, 
																   workingSystem, 
																   illegularAddSetting, 
																   flexAddSetting, 
																   regularAddSetting, 
																   holidayAddtionSet, 
																   overTimeAutoCalcAtr, 
																   workTimeDailyAtr, 
																   flexCalcMethod, 
																   holidayCalcMethodSet, 
																   bonusPayAutoCalcSet, 
																   calcAtrOfDaily, 
																   eachWorkTimeSet, 
																   eachCompanyTimeSet, 
																   breakTimeCount, 
																   integrationOfDaily, 
																   flexAutoCalSet, 
																   coreTimeSetting);
		int overWorkTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()?totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().calcTotalFrameTime():0;
		overWorkTime += totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()?totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().calcTransTotalFrameTime():0;
		int holidayWorkTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()?totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().calcTotalFrameTime():0;
		holidayWorkTime += totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()?totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().calcTransTotalFrameTime():0;
		//実績所定労働時間の計算
		val actualPredWorkTime = (recordOneDay.getWorkInformationOfDaily().getRecordInfo().getWorkTimeCode() == null)?new AttendanceTime(0):recordOneDay.getPredetermineTimeSetForCalc().getPredetermineTimeByAttendanceAtr(workType.getDailyWork().decisionNeedPredTime());
		//計画所定時間の計算
		val shedulePreWorkTime = (schePreTime.isPresent()&&scheWorkType.isPresent())? schePreTime.get().getPredetermineTimeByAttendanceAtr(scheWorkType.get().getDailyWork().decisionNeedPredTime()):new AttendanceTime(0);
		//val workSheduleTime
		return new WorkScheduleTimeOfDaily(new WorkScheduleTime(totalWorkingTime.getTotalTime(),new AttendanceTime(overWorkTime + holidayWorkTime),totalWorkingTime.getTotalTime()),shedulePreWorkTime,actualPredWorkTime);
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
