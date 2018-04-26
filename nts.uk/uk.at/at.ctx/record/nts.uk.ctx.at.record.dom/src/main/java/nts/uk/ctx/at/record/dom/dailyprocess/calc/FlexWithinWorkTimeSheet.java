package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LateDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.LeaveEarlyDecisionClock;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
//import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.ctx.at.shared.dom.PremiumAtr;

/**
 * フレックス就業時間内時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class FlexWithinWorkTimeSheet extends WithinWorkTimeSheet{
	/*コアタイム*/
	private Optional<TimeSpanForCalc> coreTimeSheet;
	/*事前申請時間*/
	private AttendanceTime preOrderTime;

	public FlexWithinWorkTimeSheet(List<WithinWorkTimeFrame> withinWorkTimeFrame, Optional<TimeSpanForCalc> coreTimeSheet) {
		super(withinWorkTimeFrame,Optional.of(new LateDecisionClock(new TimeWithDayAttr(0), 1)),Optional.of(new LeaveEarlyDecisionClock(new TimeWithDayAttr(0), 1)));
		this.coreTimeSheet = coreTimeSheet;
	}	
	
	
//	/**
//	 * 休日控除時間の計算
//	 */
//	public AttendanceTime calcHolidayDeductionTime(WorkType workType) {
//		int useTime = VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.Holiday).valueAsMinutes();
//		useTime += VacationClass.vacationTimeOfcalcDaily(workType,VacationCategory.SubstituteHoliday).valueAsMinutes();
//		return new AttendanceTime(useTime);
//	}
	
	/**
	 * 代休使用時間の計算
	 * @return
	 */
	public AttendanceTime calcSubstituteHoliday(WorkType workType,
			 									PredetermineTimeSetForCalc predetermineTimeSet,
			 									Optional<WorkTimeCode> siftCode,
			 									Optional<PersonalLaborCondition> personalCondition,
			 									HolidayAddtionSet holidayAddtionSet) {
		if(workType.getDailyWork().getOneDay().isSubstituteHoliday()
			|| workType.getDailyWork().getMorning().isSubstituteHoliday()
			|| workType.getDailyWork().getAfternoon().isSubstituteHoliday())
		{
			return VacationClass.vacationTimeOfcalcDaily(workType, 
													 VacationCategory.SubstituteHoliday,
													 predetermineTimeSet,
													 siftCode,
													 personalCondition,
													 holidayAddtionSet);
		}
		else {
			return new AttendanceTime(0);
		}
	}
	
	/**
	 * フレックス時間を計算する
	 * @return
	 */
	public FlexTime createWithinWorkTimeSheetAsFlex(CalcMethodOfNoWorkingDay calcMethod,HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
			   										VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
			   										StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
			   										Optional<PersonalLaborCondition> personalCondition,
//			   										LateTimeSheet lateTimeSheet,LeaveEarlyTimeSheet leaveEarlyTimeSheet,LateTimeOfDaily lateTimeOfDaily,
//			   										LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
			   										boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   										boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   										WorkingSystem workingSystem,WorkDeformedLaborAdditionSet illegularAddSetting,
													 WorkFlexAdditionSet flexAddSetting,
													 WorkRegularAdditionSet regularAddSetting,
													 HolidayAddtionSet holidayAddtionSet,TimeLimitUpperLimitSetting flexLimitSetting) {
		
		FlexTime flexTime = new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)),new AttendanceTime(0));
		
		//1日休日 or 1日休出
		if(workType.getDailyWork().isOneDayHoliday() || workType.getDailyWork().isHolidayWork())
			return flexTime;
		//フレックス計算しない　and 非勤務日
		if(!calcMethod.isCalclateFlexTime() && workType.getDailyWork().getAttendanceHolidayAttr().isHoliday()) 
				return flexTime; 
		/*フレックス時間の計算*/
		AttendanceTimeOfExistMinus calcflexTime = calcFlexTime(holidayCalcMethodSet,autoCalcAtr,workType,flexCalcMethod,predetermineTimeSet,
														  vacationClass,timevacationUseTimeOfDaily,statutoryDivision,siftCode,
				   										  personalCondition,
//				   										  lateTimeSheet, leaveEarlyTimeSheet, lateTimeOfDaily,
//				   										  leaveEarlyTimeOfDaily,
				   										  late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   										  leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				   										  workingSystem, illegularAddSetting, flexAddSetting, regularAddSetting,
				   										  holidayAddtionSet);
		/*事前申請を上限とする制御*/
		AttendanceTimeOfExistMinus afterLimitFlexTime = decisionLimit(flexLimitSetting,calcflexTime);
		
		return new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(afterLimitFlexTime),new AttendanceTime(0));
	}
	
	/**
	 * 事前申請上限制所処理
	 * @param flexLimitSetting フレックス超過時間の自動計算設定.自動計算設定.時間外の上限設定
	 * @param flexTime 
	 * @return
	 */
	private AttendanceTimeOfExistMinus decisionLimit(TimeLimitUpperLimitSetting flexLimitSetting, AttendanceTimeOfExistMinus flexTime) {
		switch(flexLimitSetting) {
			//事前申請を上限にする
			case LIMITNUMBERAPPLICATION:
				//上限制御をやりつつ、値を返す
				return (this.preOrderTime.greaterThan(flexTime.v()))?new AttendanceTimeOfExistMinus(this.preOrderTime.v()):flexTime;
			//指示時間を上限にする
			case INDICATEDYIMEUPPERLIMIT:	
			//上限なし	
			case NOUPPERLIMIT:
				return flexTime;
		default:
			throw new RuntimeException("unknown flex Limit setting:" + flexLimitSetting);
		}
	}




	/**
	 * フレックス時間の計算
	 */
	public AttendanceTimeOfExistMinus calcFlexTime(HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
												   VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
												   StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
												   Optional<PersonalLaborCondition> personalCondition,
												   boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
												   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
												   WorkingSystem workingSystem,WorkDeformedLaborAdditionSet illegularAddSetting,
													 WorkFlexAdditionSet flexAddSetting,
													 WorkRegularAdditionSet regularAddSetting,
													 HolidayAddtionSet holidayAddtionSet) {
		/*法定労働時間の算出*/
		StatutoryWorkingTime houtei = calcStatutoryTime(workType,flexCalcMethod,predetermineTimeSet,siftCode,personalCondition,holidayAddtionSet);
		/*実働時間の算出*/
		AttendanceTimeOfExistMinus zitudou = new AttendanceTimeOfExistMinus(super.calcWorkTime(PremiumAtr.RegularWork, 
																							   CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME, 
																							   vacationClass, 
																							   timevacationUseTimeOfDaily,
																							   statutoryDivision,
																							   workType,
																							   predetermineTimeSet,
																							   siftCode,
																							   personalCondition, 
																							   late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																							   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																							   workingSystem,
																							   illegularAddSetting,
																							   flexAddSetting,
																							   regularAddSetting,
																							   holidayAddtionSet,
																							   holidayCalcMethodSet).valueAsMinutes());
		/*実働時間の算出(割増時間含む)*/
		AttendanceTimeOfExistMinus zitudouIncludePremium = new AttendanceTimeOfExistMinus(super.calcWorkTime(PremiumAtr.Premium,
																											 CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,
																											 vacationClass, 
																											 timevacationUseTimeOfDaily,
																											 statutoryDivision,
																											 workType,
																											 predetermineTimeSet,
																											 siftCode,
																											 personalCondition, 
																											 late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
																											 leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
																											 workingSystem,
																											 illegularAddSetting,
																											 flexAddSetting,
																											 regularAddSetting,
																											 holidayAddtionSet,
																											 holidayCalcMethodSet).valueAsMinutes());
		
		AttendanceTimeOfExistMinus flexTime = new AttendanceTimeOfExistMinus(0);
		if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation()==CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME
				&& holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculateActualOperation()!=CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) {
			/*フレックス時間算出*/
			//zitudouとhouteiを入れ替える
			flexTime = new AttendanceTimeOfExistMinus(houtei.getForActualWorkTime().v()).minusMinutes(zitudou.valueAsMinutes());
			if(flexTime.lessThan(0)) {
				flexTime = new AttendanceTimeOfExistMinus(houtei.getForWorkTimeIncludePremium().v()).minusMinutes(zitudouIncludePremium.valueAsMinutes());
				flexTime = (flexTime.greaterThan(0))?new AttendanceTimeOfExistMinus(0):flexTime;
				/*不足しているフレックス時間*/
				//zitudouIncludとzitudouを入れ替える
				AttendanceTimeOfExistMinus husokuZiKasanZikan = zitudouIncludePremium.minusMinutes(zitudou.valueAsMinutes());
			}
		}
		else if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation()!=CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME
				&& holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculateActualOperation()!=CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) {
			flexTime = new AttendanceTimeOfExistMinus(houtei.getForActualWorkTime().v()).minusMinutes(zitudou.valueAsMinutes());
		}
		else if(holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getCalculateActualOperation()==CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME
				&& holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getCalculateActualOperation()==CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) {
			/*不足しているフレックス時間*/
			//flexTime = new AttendanceTimeOfExistMinus(houtei.getForWorkTimeIncludePremium().valueAsMinutes() - predetermineTimeSet.getAdditionSet().getPredTime().getPredetermineWorkTime());
			flexTime = new AttendanceTimeOfExistMinus(zitudouIncludePremium.v()).minusMinutes(houtei.getForWorkTimeIncludePremium().valueAsMinutes());
		}
		else {
			throw new RuntimeException("A combination that can not be selected is selected");
		}
		
		if((!autoCalcAtr.isCalculateEmbossing()) && flexTime.greaterThan(0)) {
			flexTime = new AttendanceTimeOfExistMinus(0);
		}
		return flexTime;
	}
	
	/**
	 * 法定労働時間から控除(フレックス用)
	 * @return
	 */
	public StatutoryWorkingTime calcStatutoryTime(WorkType workType,SettingOfFlexWork flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
												  Optional<WorkTimeCode> siftCode,Optional<PersonalLaborCondition> personalCondition,HolidayAddtionSet holidayAddtionSet) {
		val predetermineTime = predetermineTimeSet.getAdditionSet().getPredTime().getOneDay();
		StatutoryDeductionForFlex deductionTime = calcdeductTime(workType,flexCalcMethod,predetermineTimeSet,siftCode,personalCondition,holidayAddtionSet);
		return new StatutoryWorkingTime( new AttendanceTime(predetermineTime.v() - deductionTime.getForActualWork().valueAsMinutes()) 
										,new AttendanceTime(predetermineTime.v() - deductionTime.getForPremium().valueAsMinutes()));
	}
	/**
	 * 控除する時間の計算
	 */
	public StatutoryDeductionForFlex calcdeductTime(WorkType workType,SettingOfFlexWork flexCalcMethod,
													PredetermineTimeSetForCalc predetermineTimeSet,
													Optional<WorkTimeCode> siftCode,
													Optional<PersonalLaborCondition> personalCondition,
													HolidayAddtionSet holidayAddtionSet){
		/*休日控除時間の計算*/
//		AttendanceTime forHolidayTime = calcHolidayDeductionTime(workType);
		/*代休控除時間の計算*/
		AttendanceTime forCompensatoryLeaveTime = calcSubstituteHoliday(workType,predetermineTimeSet,siftCode,personalCondition,holidayAddtionSet);
		
		DeductionTime deductionTime = new DeductionTime(forCompensatoryLeaveTime,forCompensatoryLeaveTime);//(forHolidayTime,forCompensatoryLeaveTime);
		//DeductionTime deductionTime = new DeductionTime(new AttendanceTime(0),new AttendanceTime(0));//(forHolidayTime,forCompensatoryLeaveTime);
		//休日控除が算出できるようになったら、各代休控除時間に加算するように変える
		return new StatutoryDeductionForFlex(deductionTime.getForCompensatoryHolidayTime(),deductionTime.getForCompensatoryHolidayTime());
//		return new StatutoryDeductionForFlex(deductionTime.forLackCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod())
//											,deductionTime.forPremiumCalcPredetermineDeduction(flexCalcMethod.getFlexCalcMethod()));
	}
	
	public AttendanceTime calcWorkTime(PremiumAtr premiumAtr, 
									   CalcurationByActualTimeAtr calcActualTime,
									   VacationClass vacationClass,
									   TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
									   StatutoryDivision statutoryDivision,
									   WorkType workType,
									   PredetermineTimeSetForCalc predetermineTimeSet,
									   Optional<WorkTimeCode> siftCode,
									   Optional<PersonalLaborCondition> personalCondition, 
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
									   SettingOfFlexWork flexCalcMethod,
									   TimeLimitUpperLimitSetting flexLimitSetting
			   ) {
		AttendanceTime withinTime = super.calcWorkTime(premiumAtr,
													   calcActualTime,
													   vacationClass,
													   timevacationUseTimeOfDaily,
													   statutoryDivision, 
													   workType, 
													   predetermineTimeSet, 
													   siftCode, 
													   personalCondition, 
													   late, 
													   leaveEarly, 
													   workingSystem, 
													   illegularAddSetting, 
													   flexAddSetting, 
													   regularAddSetting, 
													   holidayAddtionSet, 
													   holidayCalcMethodSet);
		FlexTime flexTime = this.createWithinWorkTimeSheetAsFlex(calcMethod, 
																 holidayCalcMethodSet, 
																 autoCalcAtr, 
																 workType, 
																 flexCalcMethod, 
																 predetermineTimeSet, 
																 vacationClass, 
																 timevacationUseTimeOfDaily, 
																 statutoryDivision, 
																 siftCode, 
																 personalCondition, 
																 late, 
																 leaveEarly, 
																 workingSystem, 
																 illegularAddSetting, 
																 flexAddSetting, 
																 regularAddSetting, 
																 holidayAddtionSet, 
																 flexLimitSetting);
		if(flexTime.getFlexTime().getTime().greaterThan(0)) {
			return withinTime.minusMinutes(flexTime.getFlexTime().getTime().valueAsMinutes());
		}
		else {
			return withinTime;
		}
		
	}
}
