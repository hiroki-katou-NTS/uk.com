package nts.uk.ctx.at.record.dom.daily;

import java.util.Collections;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.StatutoryDivision;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 日別実績の所定外時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ExcessOfStatutoryTimeOfDaily {
	@Setter
	private ExcessOfStatutoryMidNightTime ExcessOfStatutoryMidNightTime;
	private Optional<OverTimeOfDaily> OverTimeWork;
	private Optional<HolidayWorkTimeOfDaily> WorkHolidayTime;
	
	
	/**
	 * Constructor
	 * @param excessOfStatutoryMidNightTime
	 * @param overTimeWork
	 * @param workHolidayTime
	 */
	public ExcessOfStatutoryTimeOfDaily(
			ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime,
			Optional<OverTimeOfDaily> overTimeWork,
			Optional<HolidayWorkTimeOfDaily> workHolidayTime) {
		super();
		ExcessOfStatutoryMidNightTime = excessOfStatutoryMidNightTime;
		OverTimeWork = overTimeWork;
		WorkHolidayTime = workHolidayTime;
	}
	
	/**
	 * 各時間の計算を指示するクラス
	 * @return
	 */
	public static ExcessOfStatutoryTimeOfDaily calculationExcessTime(CalculationRangeOfOneDay oneDay,AutoCalculationOfOverTimeWork overTimeAutoCalcSet,AutoCalSetting holidayAutoCalcSetting,
																	 CalcMethodOfNoWorkingDay calcMethod,HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,
																	 Optional<SettingOfFlexWork> flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
			   														 VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
			   														 StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
			   														 Optional<PersonalLaborCondition> personalCondition, LateTimeSheet lateTimeSheet,LeaveEarlyTimeSheet leaveEarlyTimeSheet,LateTimeOfDaily lateTimeOfDaily,
			   														 LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			   														 boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			   														 WorkingSystem workingSystem,AddSettingOfIrregularWork addSettingOfIrregularWork,AddSettingOfFlexWork addSettingOfFlexWork,AddSettingOfRegularWork addSettingOfRegularWork,
			   														 VacationAddTimeSet vacationAddTimeSet,WorkTimeDailyAtr workTimeDailyAtr) {
		//所定外深夜
		val excessOfStatutoryMidNightTime = new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)),new AttendanceTime(0)); 
		//val excessOfStatutoryMidNightTime = calcExcessMidNightTime(oneDay);//new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)),new AttendanceTime(0));
		//残業時間
		val overTime = calculationOverTime(oneDay,overTimeAutoCalcSet,calcMethod,holidayCalcMethodSet,autoCalcAtr,workType,flexCalcMethod,
										   predetermineTimeSet,vacationClass,timevacationUseTimeOfDaily,
				   						   statutoryDivision,siftCode,
				   						   personalCondition, lateTimeSheet,leaveEarlyTimeSheet,lateTimeOfDaily,
				   						   leaveEarlyTimeOfDaily,late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				   						   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				   						   workingSystem,addSettingOfIrregularWork,addSettingOfFlexWork,addSettingOfRegularWork,
				   						   vacationAddTimeSet,workTimeDailyAtr);
		//休出時間
		val workHolidayTime = calculationHolidayTime(oneDay,holidayAutoCalcSetting);
		//new HolidayWorkTimeOfDaily(Collections.emptyList(), Collections.emptyList(),Finally.empty(), new AttendanceTime(0)); 
		
		return new ExcessOfStatutoryTimeOfDaily(excessOfStatutoryMidNightTime, Optional.of(overTime), Optional.of(workHolidayTime));
	}
	


	/**
	 * 残業時間の計算
	 * @param oneDay 
	 */
	private static OverTimeOfDaily calculationOverTime(CalculationRangeOfOneDay oneDay,AutoCalculationOfOverTimeWork overTimeAutoCalcSet,
													   CalcMethodOfNoWorkingDay calcMethod,HolidayCalcMethodSet holidayCalcMethodSet,AutoCalOverTimeAttr autoCalcAtr,WorkType workType,
													   Optional<SettingOfFlexWork> flexCalcMethod,PredetermineTimeSetForCalc predetermineTimeSet,
													   VacationClass vacationClass,TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
													   StatutoryDivision statutoryDivision,Optional<WorkTimeCode> siftCode,
													   Optional<PersonalLaborCondition> personalCondition, LateTimeSheet lateTimeSheet,LeaveEarlyTimeSheet leaveEarlyTimeSheet,LateTimeOfDaily lateTimeOfDaily,
													   LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
													   boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
													   WorkingSystem workingSystem,AddSettingOfIrregularWork addSettingOfIrregularWork,AddSettingOfFlexWork addSettingOfFlexWork,AddSettingOfRegularWork addSettingOfRegularWork,
													   VacationAddTimeSet vacationAddTimeSet,WorkTimeDailyAtr workTimeDailyAtr) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().isPresent()) {
				return OverTimeOfDaily.calculationTime(oneDay.getOutsideWorkTimeSheet().get().getOverTimeWorkSheet().get(), overTimeAutoCalcSet,
													   oneDay.getWithinWorkingTimeSheet().get(),calcMethod,holidayCalcMethodSet,autoCalcAtr,workType,flexCalcMethod,predetermineTimeSet,
													   oneDay.getTemporaryDeductionTimeSheet().get(),vacationClass,timevacationUseTimeOfDaily,
													   statutoryDivision,siftCode,
													   personalCondition, lateTimeSheet,leaveEarlyTimeSheet,lateTimeOfDaily,
													   leaveEarlyTimeOfDaily,late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
													   leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
													   workingSystem,addSettingOfIrregularWork,addSettingOfFlexWork,addSettingOfRegularWork,
													   vacationAddTimeSet,workTimeDailyAtr);
			}
		}
		return new OverTimeOfDaily(Collections.emptyList(),
								   Collections.emptyList(),
								   Finally.of(new ExcessOverTimeWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(0)))),
								   new AttendanceTime(0),
								   new FlexTime(TimeWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(0)), new AttendanceTime(0)),
								   new AttendanceTime(0));
	}
	
	/**
	 * 休出時間の計算
	 * @return
	 */
	private static HolidayWorkTimeOfDaily calculationHolidayTime(CalculationRangeOfOneDay oneDay,AutoCalSetting holidayAutoCalcSetting) {
		if(oneDay.getOutsideWorkTimeSheet().isPresent()) {
			if(oneDay.getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().isPresent()) {
				return HolidayWorkTimeOfDaily.calculationTime(oneDay.getOutsideWorkTimeSheet().get().getHolidayWorkTimeSheet().get(), holidayAutoCalcSetting);
			}
		}
		return new HolidayWorkTimeOfDaily(Collections.emptyList(),
				   						  Collections.emptyList(),
				   						  Finally.of(new HolidayMidnightWork(Collections.emptyList())),
				   						  new AttendanceTime(0));
	}
	
	


}
