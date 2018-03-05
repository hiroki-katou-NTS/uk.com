package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * フレックス就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class FlexWithinWorkTimeSheet extends WithinWorkTimeSheet{
	private TimeSpanForCalc coreTimeSheet;

	public FlexWithinWorkTimeSheet(List<WithinWorkTimeFrame> withinWorkTimeFrame, TimeSpanForCalc coreTimeSheet) {
		super(withinWorkTimeFrame);
		this.coreTimeSheet = coreTimeSheet;
	}


	
	
//	
//	/**
//	 * 休日控除時間の計算
//	 */
//	public AttendanceTime calcHolidayDeductionTime(WorkType workType) {
//		int useTime = VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.Holiday).valueAsMinutes();
//		useTime += VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.SubstituteHoliday).valueAsMinutes();
//		return new AttendanceTime(useTime);
//	}
//	
//	/**
//	 * 代休使用時間の計算
//	 * @return
//	 */
//	public AttendanceTime calcSubstituteHoliday(WorkType workType) {
//		return VacationClass.vacationTimeOfcalcDaily(workType, VacationCategory.SubstituteHoliday);
//	}
//	
	/**
	 * フレックス時間を計算するか判定し就業時間内時間帯を作成する
	 * @return
	 */
	public FlexTime createWithinWorkTimeSheetAsFlex(CalcMethodOfNoWorkingDay calcMethod,HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
			   										DeductionTimeSheet dedTimeSheet,VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
			   										StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
			   										Optional<PersonalLaborCondition> personalCondition, LateTimeSheet lateTimeSheet,LeaveEarlyTimeSheet leaveEarlyTimeSheet,LateTimeOfDaily lateTimeOfDaily,
			   										LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   										boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   										WorkingSystem workingSystem,AddSettingOfIrregularWork addSettingOfIrregularWork,AddSettingOfFlexWork addSettingOfFlexWork,AddSettingOfRegularWork addSettingOfRegularWork,
			   										VacationAddTimeSet vacationAddTimeSet) {
		if(!calcMethod.isCalclateFlexTime()) {
			if(workType.getDailyWork().getAttendanceHolidayAttr().isHoliday()) {
				return new FlexTime(TimeWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0));
			}
		}
		/*フレックス時間の計算*/
		AttendanceTimeOfExistMinus flexTime = calcFlexTime(holidayCalcMethodSet,autoCalcAtr,workType,flexCalcMethod,predetermineTimeSet,
				   										  dedTimeSheet,vacationClass,timevacationUseTimeOfDaily,statutoryDivision,siftCode,
				   										  personalCondition,  lateTimeSheet, leaveEarlyTimeSheet, lateTimeOfDaily,
				   										  leaveEarlyTimeOfDaily, late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   										  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				   										  workingSystem, addSettingOfIrregularWork, addSettingOfFlexWork, addSettingOfRegularWork,
				   										  vacationAddTimeSet);
		/*事前申請を上限とする制御*/
		
		return new FlexTime(TimeWithCalculationMinusExist.sameTime(flexTime),new AttendanceTime(0));
	}
	
	/**
	 * フレックス時間の計算
	 */
	public AttendanceTimeOfExistMinus calcFlexTime(HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
												   DeductionTimeSheet dedTimeSheet,VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
												   StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
												   Optional<PersonalLaborCondition> personalCondition, LateTimeSheet lateTimeSheet,LeaveEarlyTimeSheet leaveEarlyTimeSheet,LateTimeOfDaily lateTimeOfDaily,
												   LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
												   WorkingSystem workingSystem,AddSettingOfIrregularWork addSettingOfIrregularWork,AddSettingOfFlexWork addSettingOfFlexWork,AddSettingOfRegularWork addSettingOfRegularWork,
												   VacationAddTimeSet vacationAddTimeSet) {
		/*法定労働時間の算出*/
		StatutoryWorkingTime houtei = calcStatutoryTime(workType,flexCalcMethod,predetermineTimeSet);
		/*実働時間の算出*/
		AttendanceTimeOfExistMinus zitudou = new AttendanceTimeOfExistMinus(calcWorkTime(PremiumAtr.RegularWork, CalculationByActualTimeAtr.CalculationByActualTime, dedTimeSheet, vacationClass, timevacationUseTimeOfDaily,
				    statutoryDivision,
				    workType,
				    predetermineTimeSet,
				    siftCode,
				    personalCondition, 
				    lateTimeSheet,
				    leaveEarlyTimeSheet,
				    lateTimeOfDaily,
				    leaveEarlyTimeOfDaily,
				    late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				    leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				    workingSystem,
				    addSettingOfIrregularWork,
				    addSettingOfFlexWork,
				    addSettingOfRegularWork,
				    vacationAddTimeSet).valueAsMinutes());
		/*実働時間の算出(割増時間含む)*/
		AttendanceTimeOfExistMinus zitudouIncludePremium = new AttendanceTimeOfExistMinus(calcWorkTime(PremiumAtr.Premium, CalculationByActualTimeAtr.CalculationByActualTime,dedTimeSheet, vacationClass, timevacationUseTimeOfDaily,
				    statutoryDivision,
				    workType,
				    predetermineTimeSet,
				    siftCode,
				    personalCondition, 
				    lateTimeSheet,
				    leaveEarlyTimeSheet,
				    lateTimeOfDaily,
				    leaveEarlyTimeOfDaily,
				    late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				    leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				    workingSystem,
				    addSettingOfIrregularWork,
				    addSettingOfFlexWork,
				    addSettingOfRegularWork,
				    vacationAddTimeSet).valueAsMinutes());
		
		AttendanceTimeOfExistMinus flexTime = new AttendanceTimeOfExistMinus(0);
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
				&& !holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
			/*フレックス時間算出*/
			flexTime = new AttendanceTimeOfExistMinus(houtei.getForActualWorkTime().v()).minusMinutes(zitudou.valueAsMinutes());
			if(flexTime.lessThan(0)) {
				flexTime = new AttendanceTimeOfExistMinus(houtei.getForWorkTimeIncludePremium().v()).minusMinutes(zitudouIncludePremium.valueAsMinutes());
				flexTime = (flexTime.greaterThan(0))?new AttendanceTimeOfExistMinus(0):flexTime;
				/*不足しているフレックス時間*/
				AttendanceTimeOfExistMinus husokuZiKasanZikan = zitudouIncludePremium.minusMinutes(zitudou.valueAsMinutes());
			}
		}
		else if(!holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
				&& !holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
			flexTime = new AttendanceTimeOfExistMinus(houtei.getForActualWorkTime().v()).minusMinutes(zitudou.valueAsMinutes());
		}
		else if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()
				&& holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculationByActualTime().isCalclationByActualTime()) {
			/*不足しているフレックス時間*/
			AttendanceTimeOfExistMinus husokuZiKasanZikan = new AttendanceTimeOfExistMinus(zitudouIncludePremium.v()).minusMinutes(zitudou.valueAsMinutes());
		}
		else {
			throw new RuntimeException("A combination that can not be selected is selected");
		}
		
		if(autoCalcAtr.isCalculateEmbossing() && flexTime.greaterThan(0)) {
			flexTime = new AttendanceTimeOfExistMinus(0);
		}
		return flexTime;
	}
	
	/**
	 * 法定労働時間から控除(フレックス用)
	 * @return
	 */
	public StatutoryWorkingTime calcStatutoryTime(WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet) {
		val predetermineTime = predetermineTimeSet.getpredetermineTime(workType.getDailyWork());
		StatutoryDeductionForFlex deductionTime = calcdeductTime(workType,flexCalcMethod);
		return new StatutoryWorkingTime( new AttendanceTime(predetermineTime.v() - deductionTime.getForActualWork().valueAsMinutes()) 
										,new AttendanceTime(predetermineTime.v() - deductionTime.getForPremium().valueAsMinutes()));
	}
	/**
	 * 控除する時間の計算
	 */
	public StatutoryDeductionForFlex calcdeductTime(WorkType workType,SettingOfFlexWork flexCalcMethod){
		/*休日控除時間の計算*/
//		AttendanceTime forHolidayTime = calcHolidayDeductionTime(workType);
//		/*代休控除時間の計算*/
//		AttendanceTime forCompensatoryLeaveTime = calcSubstituteHoliday(workType);
		
		DeductionTime deductionTime = new DeductionTime(new AttendanceTime(0),new AttendanceTime(0));//(forHolidayTime,forCompensatoryLeaveTime);
		
		return new StatutoryDeductionForFlex(deductionTime.forLackCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod())
											,deductionTime.forPremiumCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod()));
	}
}
