package nts.uk.ctx.at.record.dom.daily.withinworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
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
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime,AttendanceTime actualTime, WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.actualWorkTime = actualTime;
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
			   												   AutoCalAtrOvertime autoCalcSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet, 
			   												   CalcMethodOfNoWorkingDay calcMethod, 
			   												   AutoCalOverTimeAttr autoCalcAtr, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod, 
			   												   WorkTimeDailyAtr workTimeDailyAtr, 
			   												   Optional<WorkTimeCode> workTimeCode,
			   												   AttendanceTime preFlexTime,Optional<CoreTimeSetting> coreTimeSetting) {
		AttendanceTime workTime = new AttendanceTime(0);
		AttendanceTime actualTime = new AttendanceTime(0);
		//就業時間の計算
		workTime = calcWithinStatutoryTime(oneDay,personalCondition,vacationClass,workType,
														  late,leaveEarly,workingSystem,illegularAddSetting,
														  flexAddSetting,regularAddSetting,holidayAddtionSet,holidayCalcMethodSet,
														  calcMethod,autoCalcAtr,flexCalcMethod,workTimeDailyAtr,workTimeCode,preFlexTime,coreTimeSetting);
		//実働時間の計算
		if(oneDay.getWithinWorkingTimeSheet().isPresent())
			actualTime =  oneDay.getWithinWorkingTimeSheet().get().calcWorkTime(PremiumAtr.RegularWork,
																						   CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,
																						   vacationClass,
																						   oneDay.getTimeVacationAdditionRemainingTime().get(),
																						   StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
																						   workTimeCode,
																						   personalCondition,
																						   late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																						   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																						   workingSystem,
																						   illegularAddSetting,
																						   flexAddSetting,
																						   regularAddSetting,
																						   holidayAddtionSet,
																						   holidayCalcMethodSet);
			
		//所定内深夜時間の計算
		WithinStatutoryMidNightTime midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(oneDay,autoCalcSet);

		 
		return new WithinStatutoryTimeOfDaily(workTime,actualTime,midNightTime);
	}
	
	
	/**
	 * 日別実績の法定内時間の計算
	 */
	public static AttendanceTime calcWithinStatutoryTime(CalculationRangeOfOneDay oneDay,	Optional<PersonalLaborCondition> personalCondition,
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
			   												   AutoCalOverTimeAttr autoCalcAtr, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod,
			   												   WorkTimeDailyAtr workTimeDailyAtr, Optional<WorkTimeCode> workTimeCode,
			   												   AttendanceTime preFlexTime,Optional<CoreTimeSetting> coreTimeSetting) {
		AttendanceTime workTime = new AttendanceTime(0);
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			if(workTimeDailyAtr.isFlex()) {
				FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)oneDay.getWithinWorkingTimeSheet().get();
				workTime = changedFlexTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
															 flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
						  									 vacationClass,
						  									 oneDay.getTimeVacationAdditionRemainingTime().get(),
						  									 StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
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
						  									 autoCalcAtr,
						  									 flexCalcMethod.get(),
						  									 TimeLimitUpperLimitSetting.NOUPPERLIMIT,
						  									 preFlexTime,coreTimeSetting
						   );
			}
			else {
				workTime =  oneDay.getWithinWorkingTimeSheet().get().calcWorkTime(PremiumAtr.RegularWork,
																				  regularAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation(),
						  														  vacationClass,
						  														  oneDay.getTimeVacationAdditionRemainingTime().get(),
						  														  StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
						  														  workTimeCode,
						  														  personalCondition,
						  														  late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
						  														  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
						  														  workingSystem,
						  														  illegularAddSetting,
						  														  flexAddSetting,
						  														  regularAddSetting,
						  														  holidayAddtionSet,
						  														  holidayCalcMethodSet);
				
				
				
			}

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
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,actualWorkTime,withinStatutoryMidNightTime);
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
		return new WithinStatutoryTimeOfDaily(this.workTime,this.actualWorkTime,this.withinStatutoryMidNightTime!=null?this.withinStatutoryMidNightTime.calcDiverGenceTime():this.withinStatutoryMidNightTime);
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlMidTimeUpper(AttendanceTime upperTime) {
		this.withinStatutoryMidNightTime.controlUpperTime(upperTime);
	}
}