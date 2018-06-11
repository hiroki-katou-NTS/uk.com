package nts.uk.ctx.at.record.dom.actualworkinghours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CheckExcessAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.VacationClass;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.DiverdenceReasonCode;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceReasonContent;
import nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTimeOfDaily;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
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
    
    public ActualWorkingTimeOfDaily inssertTotalWorkingTime(TotalWorkingTime time) {
    	return new ActualWorkingTimeOfDaily(this.constraintDifferenceTime,this.constraintTime,this.timeDifferenceWorkingHours,time,this.divTime,this.premiumTimeOfDailyPerformance);
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
	public static ActualWorkingTimeOfDaily calcRecordTime(CalculationRangeOfOneDay oneDay,
			   Optional<PersonalLaborCondition> personalCondition,
			   VacationClass vacationClass,
			   WorkType workType,
			   WorkingSystem workingSystem,
			   WorkDeformedLaborAdditionSet illegularAddSetting,
			   WorkFlexAdditionSet flexAddSetting,
			   WorkRegularAdditionSet regularAddSetting,
			   HolidayAddtionSet holidayAddtionSet,
			   AutoCalOverTimeAttr overTimeAutoCalcAtr,
		       Optional<WorkTimeDailyAtr> workTimeDailyAtr,
			   Optional<SettingOfFlexWork> flexCalcMethod,
			   HolidayCalcMethodSet holidayCalcMethodSet,
			   BonusPayAutoCalcSet bonusPayAutoCalcSet,
			   CalAttrOfDailyPerformance calcAtrOfDaily,
			   List<WorkTimezoneOtherSubHolTimeSet> eachWorkTimeSet,
			   List<CompensatoryOccurrenceSetting> eachCompanyTimeSet,
			   DailyRecordToAttendanceItemConverter forCalcDivergenceDto,
			   List<DivergenceTime> divergenceTimeList, Optional<PredetermineTimeSetForCalc> schePreTimeSet, 
			   int breakTimeCount, Optional<FixedWorkCalcSetting> ootsukaFixedCalcSet,
			   Optional<FixRestTimezoneSet> fixRestTimeSetting, 
			   IntegrationOfDaily integrationOfDaily,
			   Optional<WorkType> scheWorkType,
			   AutoCalFlexOvertimeSetting flexAutoCalSet,
			   DailyUnit dailyUnit, WorkScheduleTimeOfDaily workScheduleTime,Optional<CoreTimeSetting> coreTimeSetting
			   ,List<OverTimeFrameNo> statutoryFrameNoList) {

		
		/* 総労働時間の計算 */
		val totalWorkingTime = TotalWorkingTime.calcAllDailyRecord(oneDay,
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
					coreTimeSetting,
					dailyUnit, 
					statutoryFrameNoList
					);
		
		
		/*大塚残業*/
		val calcResultOotsuka = calcOotsuka(workingSystem,
											totalWorkingTime,
											fixRestTimeSetting,
											oneDay.getPredetermineTimeSetForCalc().getAdditionSet().getPredTime().getOneDay(),
											ootsukaFixedCalcSet,
											calcAtrOfDaily.getOvertimeSetting(),
											dailyUnit,
											oneDay.getAttendanceLeavingWork(),
											workType);
		
		/*大塚モードの計算（欠勤控除時間）*/
		//1日出勤系の場合は処理を呼ばないように作成が必要
//		if(workType.getDailyWork().decisionNeedPredTime() != AttendanceHolidayAttr.FULL_TIME) {
//			//2018/05/25はここからスタート
//			calcResultOotsuka = calcResultOotsuka.reCalcLateLeave(holidayCalculation);
//		}
		
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
		

//		val replaceDto =  rePlaceIntegrationDto(forCalcDivergenceDto,
//				   								employeeId,
//				   								ymd,
//				   								totalWorkingTime,
//				   								constraintDifferenceTime,
//				   								constraintTime,
//				   								timeDifferenceWorkingHours,
//				   								premiumTime,
//				   								workScheduleTime); 	
		val returnList = calcDivergenceTime(forCalcDivergenceDto, divergenceTimeList);
		//returnする
		return new DivergenceTimeOfDaily(returnList);
	}


	/**
	 * 乖離時間の計算 
	 * @return
	 */
	private static List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime>   calcDivergenceTime(DailyRecordToAttendanceItemConverter forCalcDivergenceDto,List<DivergenceTime> divergenceTimeList) {
		val integrationOfDailyInDto = forCalcDivergenceDto.toDomain();
		if(integrationOfDailyInDto == null
			|| integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance() == null
			|| !integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().isPresent()
			|| integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() == null)
			return Collections.emptyList();
		
		List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> divergenceTime = new ArrayList<>();
		
		DivergenceTimeOfDaily div_time = integrationOfDailyInDto.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getDivTime();
		for(int i=0 ; i<10 ;i++) {
			String reasonContent = "";
			String reasonCode = "";
			
			int div_index = i+1;
			if(div_time != null) {
				List<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime> obj
					= div_time.getDivergenceTime().stream().filter(c->c.getDivTimeId()==div_index).collect(Collectors.toList());
				
				if(!obj.isEmpty()) {
					if(obj.get(0).getDivReason() != null) reasonContent = obj.get(0).getDivReason().v();
					if(obj.get(0).getDivResonCode() != null) reasonCode = obj.get(0).getDivResonCode().v();
				}
					
			}
			
			nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime obj = new nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime(
					new AttendanceTime(0),
					new AttendanceTime(0),
					new AttendanceTime(0),
					div_index,
					new DivergenceReasonContent(reasonContent),
					new DiverdenceReasonCode(reasonCode));
			
			divergenceTime.add(obj);
		}
		
		
		val divergenceTimeInIntegrationOfDaily = new DivergenceTimeOfDaily(divergenceTime);
		val returnList = new ArrayList<nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime>(); 
		//乖離時間算出のアルゴリズム実装
		for(DivergenceTime divergenceTimeClass : divergenceTimeList) {
			if(divergenceTimeClass.getDivTimeUseSet().isUse()) {
				divergenceTimeInIntegrationOfDaily.getDivergenceTime().stream()
										.filter(tc -> tc.getDivTimeId() == divergenceTimeClass.getDivergenceTimeNo())
										.findFirst().ifPresent(tdi -> {
											int totalTime = divergenceTimeClass.totalDivergenceTimeWithAttendanceItemId(forCalcDivergenceDto);
											int deductionTime = (tdi.getDeductionTime() == null ?  0 : tdi.getDeductionTime().valueAsMinutes());
											returnList.add(new nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTime(new AttendanceTime(totalTime - deductionTime), 
																																tdi.getDeductionTime(), 
																																new AttendanceTime(totalTime), 
																																tdi.getDivTimeId(), 
																														 		tdi.getDivReason(), 
																														 		tdi.getDivResonCode()));
										});
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
	 * @param timeLeavingOfDailyPerformance 
	 * @param workType 
	 * @return
	 */
	private static TotalWorkingTime calcOotsuka(WorkingSystem workingSystem, TotalWorkingTime totalWorkingTime,
									Optional<FixRestTimezoneSet> fixRestTimeSetting,
									AttendanceTime predetermineTime,
									Optional<FixedWorkCalcSetting> ootsukaFixedCalcSet,
									AutoCalOvertimeSetting autoCalcSet, DailyUnit dailyUnit, 
									TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance, 
									WorkType workType
									) {
		if((workingSystem.isRegularWork() || workingSystem.isVariableWorkingTimeWork())&&fixRestTimeSetting.isPresent()&& !workType.getDailyWork().isHolidayWork()) {
			//休憩未取得時間の計算
			AttendanceTime unUseBreakTime = workingSystem.isRegularWork()?totalWorkingTime.getBreakTimeOfDaily().calcUnUseBrekeTime(fixRestTimeSetting.get(),timeLeavingOfDailyPerformance):new AttendanceTime(0);
			unUseBreakTime = unUseBreakTime.greaterThan(0)?unUseBreakTime:new AttendanceTime(0);
			//日別実績の総労働からとってくる
			AttendanceTime vacationAddTime = totalWorkingTime.getVacationAddTime();
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
			
			//休暇加算を残業として計算する場合、ロジックの関係上、就業時間計算時に休暇加算が合算されてしまう
			//ここでは、合算されてしまっている休暇加算を差し引いている
			if(ootsukaFixedCalcSet != null && ootsukaFixedCalcSet.isPresent() ) {
				if(ootsukaFixedCalcSet.get().getExceededPredAddVacationCalc().getCalcMethod() != null
					 && ootsukaFixedCalcSet.get().getExceededPredAddVacationCalc().getCalcMethod().isCalcAsOverTime()
					 && totalWorkingTime.getWithinStatutoryTimeOfDaily().getWorkTime().greaterThan(predetermineTime.valueAsMinutes())) {
					totalWorkingTime.setWithinWorkTime(predetermineTime);
				}
			}

			//就業時間から休憩未取得時間を減算(休憩未取得を残業時間として計算する　であれば差し引く)
			if(ootsukaFixedCalcSet != null
			   && ootsukaFixedCalcSet.isPresent()
			   && ootsukaFixedCalcSet.get().getOverTimeCalcNoBreak() != null
			   && ootsukaFixedCalcSet.get().getOverTimeCalcNoBreak().getCalcMethod() != null
			   && !ootsukaFixedCalcSet.get().getOverTimeCalcNoBreak().getCalcMethod().isCalcAsWorking() ) {
				totalWorkingTime.getWithinStatutoryTimeOfDaily().workTimeMinusUnUseBreakTimeForOotsuka(unUseBreakTime);
			}
			

		}
		return totalWorkingTime;
	}


}
