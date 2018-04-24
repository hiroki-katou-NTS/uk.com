package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
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
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTimeOfDaily;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 
 * @author nampt 日別実績の勤務実績時間
 *
 */
@Getter
public class ActualWorkingTimeOfDaily {

	// 割増時間
	private PremiumTimeOfDailyPerformance premiumTimeOfDailyPerformance;

	// 拘束差異時間
	private AttendanceTime constraintDifferenceTime;

	// 拘束時間
	private ConstraintTime constraintTime;

	// 時差勤務時間
	private AttendanceTime timeDifferenceWorkingHours;

	// 総労働時間
	private TotalWorkingTime totalWorkingTime;

	// //代休発生情報
	// private SubHolOccurrenceInfo subHolOccurrenceInfo;

	// 乖離時間
	private DivergenceTimeOfDaily divTime;

	/**
	 * Constructor
	 */

	private ActualWorkingTimeOfDaily(AttendanceTime constraintDiffTime, ConstraintTime constraintTime,
			AttendanceTime timeDiff, TotalWorkingTime totalWorkingTime, DivergenceTimeOfDaily divTime,
			PremiumTimeOfDailyPerformance premiumTime) {
		this.premiumTimeOfDailyPerformance = premiumTime;
		this.constraintDifferenceTime = constraintDiffTime;
		this.constraintTime = constraintTime;
		this.timeDifferenceWorkingHours = timeDiff;
		this.totalWorkingTime = totalWorkingTime;
		this.divTime = divTime;
	}

	public static ActualWorkingTimeOfDaily of(TotalWorkingTime totalWorkTime, int midBind, int totalBind, int bindDiff,
			int diffTimeWork) {
		return new ActualWorkingTimeOfDaily(new AttendanceTime(bindDiff),
				new ConstraintTime(new AttendanceTime(midBind), new AttendanceTime(totalBind)),
				new AttendanceTime(diffTimeWork), totalWorkTime, new DivergenceTimeOfDaily(),
				new PremiumTimeOfDailyPerformance());
	}

    public static ActualWorkingTimeOfDaily of(TotalWorkingTime totalWorkTime, int midBind, int totalBind, int bindDiff,
            int diffTimeWork, DivergenceTimeOfDaily divTime) {
        return new ActualWorkingTimeOfDaily(new AttendanceTime(bindDiff),
                new ConstraintTime(new AttendanceTime(midBind), new AttendanceTime(totalBind)),
                new AttendanceTime(diffTimeWork), totalWorkTime, divTime, new PremiumTimeOfDailyPerformance());
    }

    public static ActualWorkingTimeOfDaily of(TotalWorkingTime totalWorkTime, int midBind, int totalBind, int bindDiff,
            int diffTimeWork, DivergenceTimeOfDaily divTime, PremiumTimeOfDailyPerformance premiumTime) {
        return new ActualWorkingTimeOfDaily(new AttendanceTime(bindDiff),
                new ConstraintTime(new AttendanceTime(midBind), new AttendanceTime(totalBind)),
                new AttendanceTime(diffTimeWork), totalWorkTime, divTime, premiumTime);
    }
    
    public static ActualWorkingTimeOfDaily of(AttendanceTime constraintDiffTime, ConstraintTime constraintTime,
			AttendanceTime timeDiff, TotalWorkingTime totalWorkingTime, DivergenceTimeOfDaily divTime,
			PremiumTimeOfDailyPerformance premiumTime) {
    	return new ActualWorkingTimeOfDaily(constraintDiffTime,constraintTime,
    										timeDiff,totalWorkingTime,divTime,premiumTime);
    }
    
	/**
	 * 日別実績の実働時間の計算
	 * @param breakTimeCount 
	 * @param schePreTimeSet 
	 * @param schePreTimeSet 
	 * @param ootsukaFixedCalcSet 
	 * @param integrationOfDaily 
	 * @param dailyUnit 
	 * @param workScheduleTime 
	 * @param flexSetting 
	 */
	public static ActualWorkingTimeOfDaily calcRecordTime(CalculationRangeOfOneDay oneDay,AutoCalOvertimeSetting overTimeAutoCalcSet,AutoCalSetting holidayAutoCalcSetting,
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
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
		       Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   AutoCalRaisingSalarySetting raisingAutoCalcSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			   DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			   List<DivergenceTime> divergenceTimeList, Optional<PredetermineTimeSetForCalc> schePreTimeSet, 
			   int breakTimeCount, Optional<FixedWorkCalcSetting> ootsukaFixedCalcSet,
			   Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSetting, 
			   IntegrationOfDaily integrationOfDaily,
			   Optional<WorkType> scheWorkType,
			   AutoCalFlexOvertimeSetting flexAutoCalSet,
			   DailyUnit dailyUnit, WorkScheduleTimeOfDaily workScheduleTime
				/*計画所定時間*/
				/*実績所定労働時間*/) {

		
		/* 総労働時間の計算 */
		val totalWorkingTime = TotalWorkingTime.calcAllDailyRecord(oneDay,overTimeAutoCalcSet,holidayAutoCalcSetting,
				    personalCondition,
				    vacationClass,
				    workType,
				    late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
				    leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
				    workingSystem,
				    illegularAddSetting,
				    flexAddSetting,
				    regularAddSetting,
				    holidayAddtionSet,
				    overTimeAutoCalcAtr,
				    workTimeDailyAtr,
				    flexCalcMethod,
				    holidayCalcMethodSet,
					raisingAutoCalcSet,
					bonusPayAutoCalcSet,
					calcAtrOfDaily,
					eachWorkTimeSet,
					eachCompanyTimeSet,
					breakTimeCount,
					integrationOfDaily,
					flexAutoCalSet
					/*計画所定時間*/
					/*実績所定労働時間*/);
		
		val calcResultOotsuka = calcOotsuka(workingSystem,
											totalWorkingTime,
											fixRestTimeSetting,
											oneDay.getPredetermineTimeSetForCalc().getAdditionSet().getPredTime().getOneDay(),
											ootsukaFixedCalcSet,
											overTimeAutoCalcSet,
											dailyUnit);
		
		/*拘束差異時間*/
		val constraintDifferenceTime = new AttendanceTime(0);
		/*拘異時間*/
		val constraintTime = new ConstraintTime(new AttendanceTime(0), new AttendanceTime(0));
		/* 時差勤務時間*/
		val timeDifferenceWorkingHours = new AttendanceTime(0);
		
		/* 割増時間の計算 */
		val premiumTime = new PremiumTimeOfDailyPerformance(Collections.emptyList());
		/* 乖離時間の計算 */
		val divergenceTimeOfDaily = createDivergenceTimeOfDaily(
													   oneDay.getWorkInformationOfDaily().getEmployeeId(),
													   oneDay.getWorkInformationOfDaily().getYmd(),
													   totalWorkingTime,
													   constraintDifferenceTime,
													   constraintTime,
													   timeDifferenceWorkingHours,
													   premiumTime,
													   forCalcDivergenceDto,
													   divergenceTimeList,
													   workScheduleTime
														/*実績所定労働時間*/
													   );
		
		/*返値*/
		return new ActualWorkingTimeOfDaily(
				constraintDifferenceTime,
				constraintTime,
				timeDifferenceWorkingHours,
				calcResultOotsuka,
				divergenceTimeOfDaily,
				premiumTime
				);
		
	}
	
	public static DivergenceTimeOfDaily createDivergenceTimeOfDaily(
			String employeeId,
			GeneralDate ymd,
			TotalWorkingTime totalWorkingTime,
			AttendanceTime constraintDifferenceTime, ConstraintTime constraintTime,
			AttendanceTime timeDifferenceWorkingHours, PremiumTimeOfDailyPerformance premiumTime,
			DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			List<DivergenceTime> divergenceTimeList, WorkScheduleTimeOfDaily workScheduleTime
			/*計画所定時間*/
			/*実績所定労働時間*/) {
		

		val replaceDto =  rePlaceIntegrationDto(forCalcDivergenceDto,
				   								employeeId,
				   								ymd,
				   								totalWorkingTime,
				   								constraintDifferenceTime,
				   								constraintTime,
				   								timeDifferenceWorkingHours,
				   								premiumTime,
				   								workScheduleTime); 	
		val returnList = calcDivergenceTime(replaceDto, divergenceTimeList);
		//returnする
		return new DivergenceTimeOfDaily(returnList);
	}

	/**
	 * Dtoの中身(日別実績の勤怠時間)を入れ替える 
	 * @param workScheduleTime 
	 * @return
	 */
	private static DailyRecordToAttendanceItemConverter rePlaceIntegrationDto(DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
																		   String employeeId,
																		   GeneralDate ymd,
																		   TotalWorkingTime totalWorkingTime,
																		   AttendanceTime constraintDifferenceTime,
																		   ConstraintTime constraintTime,
																		   AttendanceTime timeDifferenceWorkingHours,
																		   PremiumTimeOfDailyPerformance premiumTime, WorkScheduleTimeOfDaily workScheduleTime) {
		//Dtoの中身になっている
		val integrationOfDailyInDto = forCalcDivergenceDto.toDomain();
		//integraionOfDailyを入れ替える
		val attendanceTimeForDivergence = new AttendanceTimeOfDailyPerformance(employeeId, 
																			   ymd,
																			   /*計画所定時間 引数の計画所定に入れ替える*/
																			   //integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getWorkScheduleTimeOfDaily(),
																			   workScheduleTime,
																			   /*実績所定労働時間 引数の実績所定に入れ替える*/
																			   new ActualWorkingTimeOfDaily(constraintDifferenceTime,
																					   						constraintTime, 
																					   						timeDifferenceWorkingHours, 
																					   						totalWorkingTime,
																					   						//new DivergenceTimeOfDaily(Collections.emptyList()),
																					   						integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime(),
																					   						premiumTime),
																			   //滞在時間
																			   new StayingTimeOfDaily(new AttendanceTime(0),
																					   				  new AttendanceTime(0),
																					   				  new AttendanceTime(0),
																					   				  new AttendanceTime(0),
																					   				  new AttendanceTime(0)),
																			   
																				/*不就労時間*/
																				new AttendanceTime(0),
																				/*予定差異時間の計算*/
																				new AttendanceTime(0),
																				/*医療時間*/
																				new MedicalCareTimeOfDaily(WorkTimeNightShift.DAY_SHIFT,
																																 new AttendanceTime(0),
																																 new AttendanceTime(0),
																																 new AttendanceTime(0)));
//																			   integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getStayingTime(),
//																			   integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getUnEmployedTime(),
//																			   integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getBudgetTimeVariance(),
//																			   integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getMedicalCareTime());
		//DtoのWithを使って入替
		return forCalcDivergenceDto.withAttendanceTime(attendanceTimeForDivergence);
		
	}

	/**
	 * 乖離時間の計算 
	 * @return
	 */
	private static List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime>   calcDivergenceTime(DailyRecordToAttendanceItemConverter forCalcDivergenceDto,List<DivergenceTime> divergenceTimeList) {
		val integrationOfDailyInDto = forCalcDivergenceDto.toDomain();
		val divergenceTimeInIntegrationOfDaily = integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime();
		val returnList = new ArrayList<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime>(); 
		//乖離時間算出のアルゴリズム実装
		for(DivergenceTime divergenceTimeClass : divergenceTimeList) {
			if(divergenceTimeClass.getDivTimeUseSet().isUse()) {
				Optional<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> toAddItem = divergenceTimeInIntegrationOfDaily.getDivergenceTime().stream()
																																	  .filter(tc -> tc.getDivTimeId() == divergenceTimeClass.getDivergenceTimeNo())
																																				.findFirst();
				if(toAddItem.isPresent()) {
					int totalTime = divergenceTimeClass.totalDivergenceTimeWithAttendanceItemId(forCalcDivergenceDto);
					returnList.add(new nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime(new AttendanceTime(totalTime - toAddItem.get().getDeductionTime().valueAsMinutes()), 
																								 	 toAddItem.get().getDeductionTime(), 
																								 	 new AttendanceTime(totalTime), 
																								 	 toAddItem.get().getDivTimeId(), 
																								 	 toAddItem.get().getDivReason(), 
																								 	 toAddItem.get().getDivResonCode()));
				}
			}
		}
		return (returnList.size()>0)?returnList:divergenceTimeInIntegrationOfDaily.getDivergenceTime();
	}

	/**
	 * エラーチェックの指示メソッド 
	 * @param attendanceItemConverter 
	 * @return 社員のエラーチェック一覧
	 */
	public List<EmployeeDailyPerError> requestCheckError(String employeeId,GeneralDate targetDate,
			   											 SystemFixedErrorAlarm fixedErrorAlarmCode,
			   											 CheckExcessAtr checkAtr) {
		return this.getTotalWorkingTime().checkOverTimeExcess(employeeId, targetDate, fixedErrorAlarmCode, checkAtr);
	}
	
	/**
	 * 大塚モードの計算(休憩未取得)
	 * @param workingSystem 
	 * @param totalWorkingTime
	 * @param fixRestTimeSetting 固定休憩時間の時間帯設定
	 * @param predetermineTime 所定時間
	 * @param dailyUnit 
	 * @return
	 */
	private static TotalWorkingTime calcOotsuka(WorkingSystem workingSystem, TotalWorkingTime totalWorkingTime,
									Optional<TimezoneOfFixedRestTimeSet> fixRestTimeSetting,
									AttendanceTime predetermineTime,
									Optional<FixedWorkCalcSetting> ootsukaFixedCalcSet,
									AutoCalOvertimeSetting autoCalcSet, DailyUnit dailyUnit) {
		if((workingSystem.isRegularWork() || workingSystem.isVariableWorkingTimeWork())&&fixRestTimeSetting.isPresent()) {
			//休憩未取得時間の計算
			val unUseBreakTime = workingSystem.isRegularWork()?totalWorkingTime.getBreakTimeOfDaily().calcUnUseBrekeTime(fixRestTimeSetting.get()):new AttendanceTime(0);
			//日別実績の総労働からとってくる
			AttendanceTime vacationAddTime = new AttendanceTime(0);
			//残業時間
			if(totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
				//休憩未取得時間から残業時間計算
				totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().calcOotsukaOverTime(
						totalWorkingTime.getWithinStatutoryTimeOfDaily().getActualWorkTime(),
						unUseBreakTime,
						vacationAddTime,/*休暇加算時間*/
						predetermineTime, 
						ootsukaFixedCalcSet,
						autoCalcSet,
						dailyUnit
						);
				
			}
			//就業時間から休憩未取得時間を減算
			totalWorkingTime.getWithinStatutoryTimeOfDaily().workTimeMinusUnUseBreakTimeForOotsuka(unUseBreakTime);
			
		}
		return totalWorkingTime;
	}


}
