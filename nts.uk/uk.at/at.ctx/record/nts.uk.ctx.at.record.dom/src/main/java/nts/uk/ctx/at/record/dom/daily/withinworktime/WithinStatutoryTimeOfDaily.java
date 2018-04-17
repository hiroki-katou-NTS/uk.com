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
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PremiumAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
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
	private AttendanceTime workTime;
	//就業時間(休暇加算時間を含む)
	private AttendanceTime workTimeIncludeVacationTime = new AttendanceTime(0);
	//所定内割増時間
	private AttendanceTime withinPrescribedPremiumTime = new AttendanceTime(0);
	//所定内深夜時間
	private WithinStatutoryMidNightTime withinStatutoryMidNightTime = new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)));
	//休暇加算時間
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
	 * 全メンバの法定内時間(所定内時間)計算指示を出すクラス
	 * @param workTimeCode 
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily calcStatutoryTime(CalculationRangeOfOneDay oneDay,	
															   Optional<PersonalLaborCondition> personalCondition,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
//			   												   LateTimeSheet lateTimeSheet,
//			   												   LeaveEarlyTimeSheet leaveEarlyTimeSheet,
//			   												   LateTimeOfDaily lateTimeOfDaily,
//			   												   LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
			   												   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   												   WorkingSystem workingSystem,
			   												   AddSettingOfIrregularWork addSettingOfIrregularWork,
			   												   AddSettingOfFlexWork addSettingOfFlexWork,
			   												   AddSettingOfRegularWork addSettingOfRegularWork,
			   												   VacationAddTimeSet vacationAddTimeSet,
			   												   AutoCalAtrOvertime autoCalcSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet,
			   												   CalcMethodOfNoWorkingDay calcMethod, 
			   												   AutoCalOverTimeAttr autoCalcAtr, 
			   												   SettingOfFlexWork flexCalcMethod,
			   												   TimeLimitUpperLimitSetting flexLimitSetting,
			   												   WorkTimeDailyAtr workTimeDailyAtr, 
			   												   Optional<WorkTimeCode> workTimeCode) {
		//法定内時間の計算
		AttendanceTime workTime = calcWithinStatutoryTime(oneDay,personalCondition,vacationClass,workType,
//														　　lateTimeSheet,leaveEarlyTimeSheet,
//														  lateTimeOfDaily,leaveEarlyTimeOfDaily,
														  late,leaveEarly,workingSystem,addSettingOfIrregularWork,
														  addSettingOfFlexWork,addSettingOfRegularWork,vacationAddTimeSet,holidayCalcMethodSet,
														   calcMethod,autoCalcAtr,flexCalcMethod,flexLimitSetting,workTimeDailyAtr,workTimeCode);
		//所定内深夜時間の計算
		WithinStatutoryMidNightTime midNightTime = WithinStatutoryMidNightTime.calcPredetermineMidNightTime(oneDay,autoCalcSet);

		 
		return new WithinStatutoryTimeOfDaily(workTime,midNightTime);
	}
	
	
	/**
	 * 日別実績の法定内時間の計算
	 * @param workTimeCode 
	 */
	public static AttendanceTime calcWithinStatutoryTime(CalculationRangeOfOneDay oneDay,	Optional<PersonalLaborCondition> personalCondition,
			   												   VacationClass vacationClass,
			   												   WorkType workType,
//			   												   LateTimeSheet lateTimeSheet,
//			   												   LeaveEarlyTimeSheet leaveEarlyTimeSheet,
//			   												   LateTimeOfDaily lateTimeOfDaily,
//			   												   LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
			   												   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   												   WorkingSystem workingSystem,
			   												   AddSettingOfIrregularWork addSettingOfIrregularWork,
			   												   AddSettingOfFlexWork addSettingOfFlexWork,
			   												   AddSettingOfRegularWork addSettingOfRegularWork,
			   												   VacationAddTimeSet vacationAddTimeSet,
			   												   HolidayCalcMethodSet holidayCalcMethodSet,
			   												   
			   												   CalcMethodOfNoWorkingDay calcMethod, 
			   												   AutoCalOverTimeAttr autoCalcAtr, 
			   												   SettingOfFlexWork flexCalcMethod,
			   												   TimeLimitUpperLimitSetting flexLimitSetting,
			   												   WorkTimeDailyAtr workTimeDailyAtr, Optional<WorkTimeCode> workTimeCode) {
		AttendanceTime workTime = new AttendanceTime(0);
		Optional<DeductionTimeSheet> dedSheet = oneDay.getTemporaryDeductionTimeSheet().isPresent()
												?oneDay.getTemporaryDeductionTimeSheet()
												:Optional.of(new DeductionTimeSheet(Collections.emptyList(), Collections.emptyList()));
		if(oneDay.getWithinWorkingTimeSheet().isPresent()) {
			if(workTimeDailyAtr.isFlex()) {
				FlexWithinWorkTimeSheet changedFlexTimeSheet = (FlexWithinWorkTimeSheet)oneDay.getWithinWorkingTimeSheet().get();
				workTime = changedFlexTimeSheet.calcWorkTime(PremiumAtr.RegularWork,
						  CalculationByActualTimeAtr.CalculationByActualTime,
						  vacationClass,
						  oneDay.getTimeVacationAdditionRemainingTime().get(),
						  StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
						  workTimeCode,
						   personalCondition,
						   late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
						   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
						   workingSystem,
						   addSettingOfIrregularWork,
						   addSettingOfFlexWork,
						   addSettingOfRegularWork,
						   vacationAddTimeSet,
						   holidayCalcMethodSet,
						   calcMethod,
						   autoCalcAtr,
						   flexCalcMethod,
						   flexLimitSetting
						   );
			}
			else {
				workTime =  oneDay.getWithinWorkingTimeSheet().get().calcWorkTime(PremiumAtr.RegularWork,
						  CalculationByActualTimeAtr.CalculationByActualTime,
						  vacationClass,
						  oneDay.getTimeVacationAdditionRemainingTime().get(),
						  StatutoryDivision.Nomal,workType,oneDay.getPredetermineTimeSetForCalc(),
						  workTimeCode,
						   personalCondition,
						   late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
						   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
						   workingSystem,
						   addSettingOfIrregularWork,
						   addSettingOfFlexWork,
						   addSettingOfRegularWork,
						   vacationAddTimeSet,
						   holidayCalcMethodSet);
				
				
				
			}

		}
		return workTime;
	}
	
	/**
	 * 指定した引数で日別実績の法定内時間を作成する
	 * @author ken_takasu
	 * @param workTime
	 * @param workTimeIncludeVacationTime
	 * @param withinPrescribedPremiumTime
	 * @param withinStatutoryMidNightTime
	 * @param vacationAddTime
	 * @return
	 */
	public static WithinStatutoryTimeOfDaily createWithinStatutoryTimeOfDaily(AttendanceTime workTime,
																	   AttendanceTime workTimeIncludeVacationTime,
																	   AttendanceTime withinPrescribedPremiumTime,
																	   WithinStatutoryMidNightTime withinStatutoryMidNightTime,
																	   AttendanceTime vacationAddTime) {
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(workTime,withinStatutoryMidNightTime);
		withinStatutoryTimeOfDaily.workTimeIncludeVacationTime = workTimeIncludeVacationTime;
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
}