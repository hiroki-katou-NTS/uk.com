package nts.uk.ctx.at.record.dom.daily.withinworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の所定内時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinStatutoryTimeOfDaily {
	//就業時間
	@Setter
	private AttendanceTime workTime;
	//実働就業時間
	private AttendanceTime actualWorkTime = new AttendanceTime(0);
	//所定内割増時間
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	//所定内深夜時間
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
	//休暇加算時間
	private AttendanceTime vacationAddTime = new AttendanceTime(0);  
	
	/**
	 * Constructor
	 * @param workTime 就業時間
	 * @param actualTime 
	 */
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime,AttendanceTime actualTime, AttendanceTime premiumTime, WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.actualWorkTime = actualTime;
		this.withinPrescribedPremiumTime = premiumTime;
		this.withinStatutoryMidNightTime = midNightTime;
	}
	
	/**
	 * 全メンバを算出するために計算指示を出す
	 * @param calcMethod 
	 * @param autoCalcAtr 
	 * @param flexCalcMethod 
	 * @param flexLimitSetting 
	 * @param workTimeDailyAtr 
	 * @param workTimeCode 
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily calcStatutoryTime(CalculationRangeOfOneDay oneDay,	
															   Optional<PersonalLaborCondition> personalCondition,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
			   												   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   												   WorkingSystem workingSystem,
			   												   WorkDeformedLaborAdditionSet illegularAddSetting,
			   												   WorkFlexAdditionSet flexAddSetting,
			   												   WorkRegularAdditionSet regularAddSetting,
			   												   HolidayAddtionSet holidayAddtionSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet, 
			   												   CalcMethodOfNoWorkingDay calcMethod, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod, 
			   												   Optional<WorkTimeDailyAtr> workTimeDailyAtr, 
			   												   Optional<WorkTimeCode> workTimeCode,
			   												   AttendanceTime preFlexTime,
			   												   Optional<CoreTimeSetting> coreTimeSetting,
			   												   DailyUnit dailyUnit,WorkTimezoneCommonSet commonSetting) {
		AttendanceTime workTime = new AttendanceTime(0);
		AttendanceTime actualTime = new AttendanceTime(0);
		AttendanceTime withinpremiumTime = new AttendanceTime(0);
		//
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			//所定内割増時間の計算
			val predTime = oneDay.getPredetermineTimeSetForCalc().getpredetermineTime(workType.getDailyWork());
			withinpremiumTime = predTime.greaterThan(dailyUnit.getDailyTime().valueAsMinutes())
										?new AttendanceTime(oneDay.getWithinWorkingTimeSheet().get().getWithinWorkTimeFrame().stream()
																										  .filter(tc -> tc.getPremiumTimeSheetInPredetermined().isPresent())
																										  .map(tc -> tc.getPremiumTimeSheetInPredetermined().get().getTimeSheet().lengthAsMinutes())
																										  .collect(Collectors.summingInt(tc -> tc)))
																										  
										:new AttendanceTime(0);
			
			//就業時間の計算
			if(workTimeDailyAtr.isPresent())
				workTime = calcWithinStatutoryTime(oneDay.getWithinWorkingTimeSheet().get(),personalCondition,vacationClass,workType,
														  late,leaveEarly,workingSystem,illegularAddSetting,
														  flexAddSetting,regularAddSetting,holidayAddtionSet,holidayCalcMethodSet,
														  calcMethod,
														  flexCalcMethod,workTimeDailyAtr.get(),workTimeCode,preFlexTime,coreTimeSetting,
														  oneDay.getPredetermineTimeSetForCalc(),oneDay.getTimeVacationAdditionRemainingTime(),dailyUnit,commonSetting);
			

		}

									
		//実働時間の計算
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			actualTime =  calcWithinStatutoryTime(oneDay.getWithinWorkingTimeSheet().get(),personalCondition,vacationClass,workType,
					  							  late,leaveEarly,workingSystem,illegularAddSetting,
					  							  flexAddSetting,regularAddSetting,holidayAddtionSet,holidayCalcMethodSet,
					  							  calcMethod,
					  							  flexCalcMethod,workTimeDailyAtr.get(),workTimeCode,preFlexTime,coreTimeSetting,
					  							  oneDay.getPredetermineTimeSetForCalc(),oneDay.getTimeVacationAdditionRemainingTime(),dailyUnit,commonSetting);
			actualTime = actualTime.minusMinutes(withinpremiumTime.valueAsMinutes());
		}
			
		//所定内深夜時間の計算
		WithinStatutoryMidNightTime midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(oneDay);

		 
		return new WithinStatutoryTimeOfDaily(workTime,actualTime,withinpremiumTime,midNightTime);
	}
	
	
	/**
	 * 日別実績の法定内時間の計算
	 * @param dailyUnit 
	 * @param withinpremiumTime 
	 */
	public static AttendanceTime calcWithinStatutoryTime(WithinWorkTimeSheet withinTimeSheet,	Optional<PersonalLaborCondition> personalCondition,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
			   												   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   												   WorkingSystem workingSystem,
			   												   WorkDeformedLaborAdditionSet illegularAddSetting,
			   												   WorkFlexAdditionSet flexAddSetting,
			   												   WorkRegularAdditionSet regularAddSetting,
			   												   HolidayAddtionSet holidayAddtionSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet,
			   												   CalcMethodOfNoWorkingDay calcMethod, 
//			   												   AutoCalAtrOvertime autoCalcAtr, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod,
			   												   WorkTimeDailyAtr workTimeDailyAtr, Optional<WorkTimeCode> workTimeCode,
			   												   AttendanceTime preFlexTime,Optional<CoreTimeSetting> coreTimeSetting,
			   												   PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			   												   Finally<TimevacationUseTimeOfDaily> timeVacationAdditionRemainingTime, DailyUnit dailyUnit,
			   												   WorkTimezoneCommonSet commonSetting
			   												   ) {
		AttendanceTime workTime = new AttendanceTime(0);

		if(workTimeDailyAtr.isFlex()) {
			FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)withinTimeSheet;
			workTime = changedFlexTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
															 flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
						  									 vacationClass,
						  									 timeVacationAdditionRemainingTime.get(),
						  									 StatutoryDivision.Nomal,workType,
						  									 predetermineTimeSetForCalc,
						  									 workTimeCode,
						  									 personalCondition,
						  									 late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
						  									 leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
						  									 workingSystem,
						  									 illegularAddSetting,
						  									 flexAddSetting,
						  									 regularAddSetting,
						  									 holidayAddtionSet,
						  									 holidayCalcMethodSet,
						  									 calcMethod,
						  									 AutoCalAtrOvertime.CALCULATEMBOSS,
						  									 flexCalcMethod.get(),
						  									 TimeLimitUpperLimitSetting.NOUPPERLIMIT,
						  									 preFlexTime,coreTimeSetting,
						  									dailyUnit,commonSetting
						  									
					   );
		}
		else {
			workTime =  withinTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
																				  regularAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
						  														  vacationClass,
						  														  timeVacationAdditionRemainingTime.get(),
						  														  StatutoryDivision.Nomal,workType,
						  														  predetermineTimeSetForCalc,
						  														  workTimeCode,
						  														  personalCondition,
						  														  late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
						  														  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
						  														  workingSystem,
						  														  illegularAddSetting,
						  														  flexAddSetting,
						  														  regularAddSetting,
						  														  holidayAddtionSet,
						  														  holidayCalcMethodSet,
						  														  dailyUnit,commonSetting);
				
				
				
		}
		return workTime;
	}
	
	/**
	 * 受け取った引数で日別実績の法定内時間を作成する
	 * @author ken_takasu
	 * @param workTime
	 * @param workTimeIncludeVacationTime
	 * @param withinPrescribedPremiumTime
	 * @param withinStatutoryMidNightTime
	 * @param vacationAddTime
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily createWithinStatutoryTimeOfDaily(AttendanceTime workTime,
																	   AttendanceTime actualWorkTime,
																	   AttendanceTime withinPrescribedPremiumTime,
																	   WithinStatutoryMidNightTime withinStatutoryMidNightTime,
																	   AttendanceTime vacationAddTime) {
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,actualWorkTime,withinPrescribedPremiumTime,withinStatutoryMidNightTime);
		withinStatutoryTimeOfDaily.actualWorkTime = actualWorkTime;
		withinStatutoryTimeOfDaily.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		withinStatutoryTimeOfDaily.withinStatutoryMidNightTime = withinStatutoryMidNightTime;
		withinStatutoryTimeOfDaily.vacationAddTime = vacationAddTime;
		return withinStatutoryTimeOfDaily;
	}
	
	public List<EmployeeDailyPerError> checkWithinMidNightExcess(String employeeId,
			                                               		 GeneralDate targetDate,
																 String searchWord,
																 AttendanceItemDictionaryForCalc attendanceItemDictionary,
																 ErrorAlarmWorkRecordCode errorCode) {
		List<EmployeeDailyPerError> returnErrorItem = new ArrayList<>();
		if(this.getWithinStatutoryMidNightTime().isOverLimitDivergenceTime()) {
			val itemId = attendanceItemDictionary.findId(searchWord);
			if(itemId.isPresent())
				returnErrorItem.add(new EmployeeDailyPerError(AppContexts.user().companyCode(), employeeId, targetDate, errorCode, itemId.get()));
					
		}
		return returnErrorItem;
	}
	
	/**
	 * 就業時間から休憩未使用時間を減算(大塚モード専用処理)
	 * @param unUseBreakTime 休憩未取得時間
	 */
	public void workTimeMinusUnUseBreakTimeForOotsuka(AttendanceTime unUseBreakTime) {
		this.workTime = this.workTime.minusMinutes(unUseBreakTime.valueAsMinutes());
		this.actualWorkTime = this.actualWorkTime.minusMinutes(unUseBreakTime.valueAsMinutes());
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public WithinStatutoryTimeOfDaily calcDiverGenceTime() {
		return new WithinStatutoryTimeOfDaily(this.workTime,
											  this.actualWorkTime,
											  this.withinPrescribedPremiumTime,
											  this.withinStatutoryMidNightTime!=null?this.withinStatutoryMidNightTime.calcDiverGenceTime():this.withinStatutoryMidNightTime);
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlMidTimeUpper(AttendanceTime upperTime) {
		this.withinStatutoryMidNightTime.controlUpperTime(upperTime);
	}
}