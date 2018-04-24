package nts.uk.ctx.at.record.dom.daily.withinworktime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AttendanceItemDictionaryForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BreakTimeManagement;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の�?定�?時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class WithinStatutoryTimeOfDaily {
	//就業時間
	private AttendanceTime workTime;
	//実働就業時間
	private AttendanceTime actualWorkTime = new AttendanceTime(0);
	//�?定�?割増時�?
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	//�?定�?深夜時�?
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
	//休暇�?算時�?
	private AttendanceTime vacationAddTime = new AttendanceTime(0);  
	
	/**
	 * Constructor
	 * @param workTime 就業時間
	 */
	private WithinStatutoryTimeOfDaily(AttendanceTime workTime,WithinStatutoryMidNightTime midNightTime) {
		this.workTime = workTime;
		this.withinStatutoryMidNightTime = midNightTime;
	}
	
	/**
	 * 全メンバ�?法定�?時間(�?定�?時間)計算指示を�?すクラス
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
			   												   boolean late,  //日別実績の計算区�?.�?��早�?の自動計算設�?.�?��
			   												   boolean leaveEarly,  //日別実績の計算区�?.�?��早�?の自動計算設�?.早�?
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
			   												   TimeLimitUpperLimitSetting flexLimitSetting, 
			   												   WorkTimeDailyAtr workTimeDailyAtr, 
			   												   Optional<WorkTimeCode> workTimeCode) {
		//法定�?時間の計�?
		AttendanceTime workTime = calcWithinStatutoryTime(oneDay,personalCondition,vacationClass,workType,
														  late,leaveEarly,workingSystem,illegularAddSetting,
														  flexAddSetting,regularAddSetting,holidayAddtionSet,holidayCalcMethodSet,
														  calcMethod,autoCalcAtr,flexCalcMethod,flexLimitSetting,workTimeDailyAtr,workTimeCode);
		//�?定�?深夜時間�?計�?
		WithinStatutoryMidNightTime midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(oneDay,autoCalcSet);

		 
		return new WithinStatutoryTimeOfDaily(workTime,midNightTime);
	}
	
	
	/**
	 * 日別実績の法定�?時間の計�?
	 */
	public static AttendanceTime calcWithinStatutoryTime(CalculationRangeOfOneDay oneDay,	Optional<PersonalLaborCondition> personalCondition,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
			   												   boolean late,  //日別実績の計算区�?.�?��早�?の自動計算設�?.�?��
			   												   boolean leaveEarly,  //日別実績の計算区�?.�?��早�?の自動計算設�?.早�?
			   												   WorkingSystem workingSystem,
			   												   WorkDeformedLaborAdditionSet illegularAddSetting,
			   												   WorkFlexAdditionSet flexAddSetting,
			   												   WorkRegularAdditionSet regularAddSetting,
			   												   HolidayAddtionSet holidayAddtionSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet,
			   												   CalcMethodOfNoWorkingDay calcMethod, 
			   												   AutoCalOverTimeAttr autoCalcAtr, 
			   												   Optional<SettingOfFlexWork> flexCalcMethod,
			   												   TimeLimitUpperLimitSetting flexLimitSetting,
			   												   WorkTimeDailyAtr workTimeDailyAtr, Optional<WorkTimeCode> workTimeCode) {
		AttendanceTime workTime = new AttendanceTime(0);
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			if(workTimeDailyAtr.isFlex()) {
				FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)oneDay.getWithinWorkingTimeSheet().get();
				workTime = changedFlexTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
															 CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME,
						  									 vacationClass,
						  									 oneDay.getTimeVacationAdditionRemainingTime().get(),
						  									 StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
						  									 workTimeCode,
						  									 personalCondition,
						  									 late,  //日別実績の計算区�?.�?��早�?の自動計算設�?.�?��
						  									 leaveEarly,  //日別実績の計算区�?.�?��早�?の自動計算設�?.早�?
						  									 workingSystem,
						  									 illegularAddSetting,
						  									 flexAddSetting,
						  									 regularAddSetting,
						  									 holidayAddtionSet,
						  									 holidayCalcMethodSet,
						  									 calcMethod,
						  									 autoCalcAtr,
						  									 flexCalcMethod.get(),
						  									 flexLimitSetting
						   );
			}
			else {
				workTime =  oneDay.getWithinWorkingTimeSheet().get().calcWorkTime(PremiumAtr.RegularWork,
																				  CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,
						  														  vacationClass,
						  														  oneDay.getTimeVacationAdditionRemainingTime().get(),
						  														  StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
						  														  workTimeCode,
						  														  personalCondition,
						  														  late,  //日別実績の計算区�?.�?��早�?の自動計算設�?.�?��
						  														  leaveEarly,  //日別実績の計算区�?.�?��早�?の自動計算設�?.早�?
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
	 * �?��した引数で日別実績の法定�?時間を作�?する
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
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,withinStatutoryMidNightTime);
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
	 * 就業時間から休�?未使用時間を減�?(大塚モード専用処�?)
	 * @param unUseBreakTime 休�?未取得時�?
	 */
	public void workTimeMinusUnUseBreakTimeForOotsuka(AttendanceTime unUseBreakTime) {
		this.workTime = this.workTime.minusMinutes(unUseBreakTime.valueAsMinutes());
		this.actualWorkTime = this.actualWorkTime.minusMinutes(unUseBreakTime.valueAsMinutes());
	}
}