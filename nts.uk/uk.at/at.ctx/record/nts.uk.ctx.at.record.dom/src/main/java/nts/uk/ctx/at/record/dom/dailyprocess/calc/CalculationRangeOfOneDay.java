package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.SubHolOccurrenceInfo;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.AutoCalOverTimeAttr;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinPremiumTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfEachPremiumHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaisingSalaryTime;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.workrule.waytowork.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 1日の計算�?��
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalculationRangeOfOneDay {

	private Finally<WithinWorkTimeSheet> withinWorkingTimeSheet = Finally.empty();

	@Setter
	private Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet = Finally.empty();

	private TimeSpanForCalc oneDayOfRange;

	private WorkInfoOfDailyPerformance workInformationOfDaily;
	
	@Setter
	private TimeLeavingOfDailyPerformance attendanceLeavingWork;

	private PredetermineTimeSetForCalc predetermineTimeSetForCalc;

	private Finally<TimevacationUseTimeOfDaily> timeVacationAdditionRemainingTime = Finally.empty();// 時間休暇�?算残時�?


	public CalculationRangeOfOneDay(Finally<WithinWorkTimeSheet> withinWorkingTimeSheet,
			Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet, TimeSpanForCalc oneDayOfRange,
			TimeLeavingOfDailyPerformance attendanceLeavingWork, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			Finally<TimevacationUseTimeOfDaily> timeVacationAdditionRemainingTime,
			WorkInfoOfDailyPerformance workInformationofDaily) {
		this.withinWorkingTimeSheet = withinWorkingTimeSheet;
		this.outsideWorkTimeSheet = outsideWorkTimeSheet;
		this.oneDayOfRange = oneDayOfRange;
		this.attendanceLeavingWork = attendanceLeavingWork;
		this.predetermineTimeSetForCalc = predetermineTimeSetForCalc;
		this.timeVacationAdditionRemainingTime = timeVacationAdditionRemainingTime;
		this.workInformationOfDaily = workInformationofDaily;
	}

	/**
	 * 就業時間帯の作�?
	 * 
	 * @param workingSystem
	 * @param setMethod
	 * @param clockManage
	 * @param dailyGoOutSheet
	 * @param CommonSet
	 * @param fixedCalc
	 * @param workTimeDivision
	 * @param noStampSet
	 * @param fluidSet
	 * @param breakmanage
	 * @param workTimeMethodSet
	 * @param fluRestTime
	 * @param fluidprefixBreakTimeSet
	 * @param predetermineTimeSet
	 * @param fixedWorkSetting
	 * @param workTimeCommonSet
	 * @param bonusPaySetting
	 * @param overTimeHourSetList
	 * @param fixOff
	 * @param dayEndSet
	 * @param overDayEndSet
	 * @param holidayTimeWorkItem
	 * @param beforeDay
	 *            前日の勤務種�?
	 * @param toDay
	 *            当日の勤務種�?
	 * @param afterDay
	 *            翌日の勤務種�?
	 * @param breakdownTimeDay
	 * @param dailyTime
	 *            法定労働時�?
	 * @param calcSetinIntegre
	 * @param statutorySet
	 * @param prioritySet
	 * @param integrationOfDaily 
	 */
	public void createWithinWorkTimeSheet(WorkingSystem workingSystem, WorkTimeMethodSet setMethod,
			RestClockManageAtr clockManage, OutingTimeOfDailyPerformance dailyGoOutSheet, CommonRestSetting commonSet,
			Optional<FixedRestCalculateMethod> fixedCalc, WorkTimeDivision workTimeDivision, 
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			FixedWorkSetting fixedWorkSetting, BonusPaySetting bonusPaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, List<HDWorkTimeSheetSetting> fixOff, OverDayEndCalcSet dayEndSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet, WorkTimeSetting workTime,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList
			,MidNightTimeSheet midNightTimeSheet,DailyCalculationPersonalInformation personalInfo,Optional<CoreTimeSetting> coreTimeSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
    		VacationClass vacationClass, TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
    		Optional<WorkTimeCode> siftCode, Optional<PersonalLaborCondition> personalCondition, 
    		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
    		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet) {
		/* 固定控除時間帯の作�? */
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createTimeSheetForFixBreakTime(
				setMethod, clockManage, dailyGoOutSheet, this.oneDayOfRange, commonSet, attendanceLeavingWork,
				fixedCalc, workTimeDivision, breakTimeOfDailyList);
		
		val fixedWorkTImeZoneSet = new CommonFixedWorkTimezoneSet();
		fixedWorkTImeZoneSet.forFixed(fixedWorkSetting.getLstHalfDayWorkTimezone());
		theDayOfWorkTimesLoop(workingSystem, predetermineTimeSetForCalc, fixedWorkTImeZoneSet,fixedWorkSetting.getCommonSetting(), bonusPaySetting,
				overTimeHourSetList, fixOff, dayEndSet, holidayTimeWorkItem, beforeDay, toDay, afterDay,
				breakdownTimeDay, dailyTime, calcSetinIntegre, statutorySet, prioritySet, deductionTimeSheet,
				workTime,midNightTimeSheet,personalInfo,holidayCalcMethodSet,coreTimeSetting,dailyUnit,breakTimeList, 
				vacationClass, timevacationUseTimeOfDaily,  
				siftCode, personalCondition, leaveEarly, leaveEarly, illegularAddSetting, 
				flexAddSetting, regularAddSetting, holidayAddtionSet);
	}

	/**
	 * 時間帯作�?(勤務回数�??ルー�?) 就業時間�??外�?処�?
	 * 
	 * @param workingSystem
	 *            労働制クラス
	 * @param predetermineTimeSet
	 *            �?定時間設定クラス
	 * @param fixedWorkSetting
	 *            固定勤務設定クラス
	 * @param workTimeCommonSet
	 *            就業時間帯の共通設定クラス
	 * @param bonusPaySetting
	 *            �?給設定クラス
	 * @param overTimeHourSetList
	 *            残業時間の時間帯設定クラス
	 * @param fixOff
	 *            固定勤務�?休日出勤用勤務時間帯クラス
	 * @param dayEndSet
	 *            0時跨ぎ計算設定クラス
	 * @param overDayEndSet
	 *            就業時間帯の共通設定クラス
	 * @param holidayTimeWorkItem
	 *            休�?�?時間帯
	 * @param beforeDay
	 *            勤務種類クラス
	 * @param toDay
	 *            勤務種類クラス
	 * @param afterDay
	 *            勤務種類クラス
	 * @param breakdownTimeDay
	 *            1日の時間�?��クラス
	 * @param dailyTime
	 *            法定労働時�?
	 * @param calcSetinIntegre
	 *            残業時間の自動計算設定クラス
	 * @param statutorySet
	 *            法定�?残業設�?
	 * @param prioritySet
	 *            法定�?優先設�?
	 * @param deductionTimeSheet
	 *            控除時間帯
	 * @param integrationOfDaily 
	 */
	public void theDayOfWorkTimesLoop(WorkingSystem workingSystem, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			WorkTimezoneCommonSet workTimeCommonSet, BonusPaySetting bonusPaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, List<HDWorkTimeSheetSetting> fixOff, OverDayEndCalcSet dayEndSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet,
			DeductionTimeSheet deductionTimeSheet, WorkTimeSetting workTime,MidNightTimeSheet midNightTimeSheet,
			DailyCalculationPersonalInformation personalInfo,HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<CoreTimeSetting> coreTimeSetting,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
    		VacationClass vacationClass, TimevacationUseTimeOfDaily timevacationUseTimeOfDaily, 
    		Optional<WorkTimeCode> siftCode, Optional<PersonalLaborCondition> personalCondition, 
    		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
    		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet) {
		if (workingSystem.isExcludedWorkingCalculate()) {
			/* 計算対象外�?処�? */
			return;
		}
		for (int workNumber = 1; workNumber <= attendanceLeavingWork.getTimeLeavingWorks().size(); workNumber++) {
			
			/* 就業�??時間帯作�? */
			//打刻はある前提で動く
			val createWithinWorkTimeSheet = WithinWorkTimeSheet.createAsFixed(attendanceLeavingWork.getAttendanceLeavingWork(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(workNumber)).get(),
																			  toDay,
																			  predetermineTimeSetForCalc, 
																			  lstHalfDayWorkTimezone,
																			  workTimeCommonSet,
																			  deductionTimeSheet,
																			  bonusPaySetting,
																			  midNightTimeSheet,
																			  workNumber,
																			  coreTimeSetting,
																			  holidayCalcMethodSet,
																			  workTimeCommonSet.getLateEarlySet(),
																			  dailyUnit,breakTimeList,
																			  personalCondition, 
																			  vacationClass, 
																			  late, 
																				leaveEarly, 
																				workingSystem, 
																				illegularAddSetting, 
																				flexAddSetting, 
																				regularAddSetting, 
																				holidayAddtionSet, 
																				CalcMethodOfNoWorkingDay.isCalculateFlexTime, 
																				AutoCalOverTimeAttr.CALCULATION_FROM_STAMP, 
																				Optional.of(new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
																						                                                       new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay)))), 
																				workTime.getWorkTimeDivision().getWorkTimeDailyAtr(), 
																				siftCode, 
																				new AttendanceTime(1440), 
																				Finally.of(timevacationUseTimeOfDaily));
			if(withinWorkingTimeSheet.isPresent()) {
				withinWorkingTimeSheet.get().getWithinWorkTimeFrame().addAll(createWithinWorkTimeSheet.getWithinWorkTimeFrame());
			}
			else {
				withinWorkingTimeSheet.set(createWithinWorkTimeSheet);
			}
			/* 就業外�?時間帯作�? */
			//打刻はある前提で動く
			val createOutSideWorkTimeSheet = OutsideWorkTimeSheet.createOutsideWorkTimeSheet(overTimeHourSetList, fixOff,
					attendanceLeavingWork.getAttendanceLeavingWork(new nts.uk.ctx.at.shared.dom.worktime.common.WorkNo(workNumber)).get(),
					workNumber, dayEndSet, workTimeCommonSet, holidayTimeWorkItem, beforeDay, toDay, afterDay, workTime,
					workingSystem, breakdownTimeDay, dailyTime, calcSetinIntegre.getOvertimeSetting(), statutorySet, prioritySet
					,bonusPaySetting,midNightTimeSheet,personalInfo,deductionTimeSheet,dailyUnit,holidayCalcMethodSet,createWithinWorkTimeSheet, 
					vacationClass, timevacationUseTimeOfDaily, predetermineTimeSetForCalc, 
					siftCode, personalCondition, leaveEarly, leaveEarly, illegularAddSetting, flexAddSetting, regularAddSetting, holidayAddtionSet
					);
			if(!outsideWorkTimeSheet.isPresent()) {
				//outsideWorkTimeSheet.set(createOutSideWorkTimeSheet);
				this.outsideWorkTimeSheet = Finally.of(createOutSideWorkTimeSheet);
			}
			else {
				//残業
				if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
					List<OverTimeFrameTimeSheetForCalc> addOverList = createOutSideWorkTimeSheet.getOverTimeWorkSheet().isPresent()? createOutSideWorkTimeSheet.getOverTimeWorkSheet().get().getFrameTimeSheets():Collections.emptyList();
					outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets().addAll(addOverList);
				}
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(createOutSideWorkTimeSheet.getOverTimeWorkSheet(),this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet()));
				}
				//休�?
				if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
					List<HolidayWorkFrameTimeSheetForCalc> addHolList = createOutSideWorkTimeSheet.getHolidayWorkTimeSheet().isPresent()? createOutSideWorkTimeSheet.getHolidayWorkTimeSheet().get().getWorkHolidayTime():Collections.emptyList();
					outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().getWorkHolidayTime().addAll(addHolList);
				}
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(this.outsideWorkTimeSheet.get().getOverTimeWorkSheet(),createOutSideWorkTimeSheet.getHolidayWorkTimeSheet()));
				}
			}
		}
		List<OverTimeFrameTimeSheetForCalc> paramList = new ArrayList<>();
		if(this.outsideWorkTimeSheet.isPresent()
			&& this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
			paramList = this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets();
		}
		val overTimeFrame = forOOtsukaPartMethod(statutorySet, 
							 dailyTime,
							 paramList,
							 calcSetinIntegre.getOvertimeSetting(),
							 breakdownTimeDay,
							 overTimeHourSetList,
							 dailyUnit,
							 holidayCalcMethodSet,
							 vacationClass,
							 timevacationUseTimeOfDaily,
							 afterDay,
							 predetermineTimeSetForCalc,
							 siftCode,
							 personalCondition,
							 leaveEarly,
							 workingSystem,
							 holidayAddtionSet,
							 regularAddSetting,
							 flexAddSetting,
							 illegularAddSetting,
							 leaveEarly);
		if(!overTimeFrame.isEmpty()) {
			if(outsideWorkTimeSheet.isPresent()) {
				if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
					outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets().addAll(overTimeFrame);
					return;
				}
				//残業追�?
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(Optional.of(new OverTimeSheet(new RaisingSalaryTime(),
						  																					  	  overTimeFrame,
						  																					  	  new SubHolOccurrenceInfo()
																					)),
												this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet()
												));
				}
			}
			//�?定外インスタンス作�?
			else {
				this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(Optional.of(new OverTimeSheet(new RaisingSalaryTime(),
																										  overTimeFrame,
																										  new SubHolOccurrenceInfo()
							   						 						)),
					   														Optional.of(new HolidayWorkTimeSheet(new RaisingSalaryTime(),
					   																							 Collections.emptyList(), 
					   																							 new SubHolOccurrenceInfo()))
					   														));
			}
		}		
	}

	
	/**
	 * 大塚�??固定勤務�?流動残業対�?(�?定�?割増を残業時間帯へ移�?)
	 * @param statutorySet
	 * @param dailyTime
	 * @param overTimeWorkFrameTimeSheetList
	 * @param autoCalculationSet
	 * @param breakdownTimeDay
	 * @param overTimeHourSetList
	 * @param dailyUnit
	 * @param holidayCalcMethodSet
	 * @param vacationClass
	 * @param timevacationUseTimeOfDaily
	 * @param workType
	 * @param predetermineTimeSet
	 * @param siftCode
	 * @param personalCondition
	 * @param late
	 * @param workingSystem
	 * @param holidayAddtionSet
	 * @param regularAddSetting
	 * @param flexAddSetting
	 * @param illegularAddSetting
	 * @param leaveEarly
	 * @return
	 */
	private List<OverTimeFrameTimeSheetForCalc> forOOtsukaPartMethod(LegalOTSetting statutorySet, DailyTime dailyTime, List<OverTimeFrameTimeSheetForCalc> overTimeWorkFrameTimeSheetList, 
									  AutoCalOvertimeSetting autoCalculationSet, BreakDownTimeDay breakdownTimeDay, List<OverTimeOfTimeZoneSet> overTimeHourSetList, 
									  DailyUnit dailyUnit, HolidayCalcMethodSet holidayCalcMethodSet,
									  VacationClass vacationClass, TimevacationUseTimeOfDaily timevacationUseTimeOfDaily, WorkType workType, 
									  PredetermineTimeSetForCalc predetermineTimeSet, Optional<WorkTimeCode> siftCode, Optional<PersonalLaborCondition> personalCondition, 
									  boolean late, WorkingSystem workingSystem, HolidayAddtionSet holidayAddtionSet, WorkRegularAdditionSet regularAddSetting, 
									  WorkFlexAdditionSet flexAddSetting, WorkDeformedLaborAdditionSet illegularAddSetting, boolean leaveEarly) {
		
		if(!this.withinWorkingTimeSheet.isPresent())
			return Collections.emptyList();
		List<WithinWorkTimeFrame> renewWithinFrame = new ArrayList<>();
		List<OverTimeFrameTimeSheetForCalc> returnList = new ArrayList<>();
		//�?定�?就業時間�?のルー�?
		for(WithinWorkTimeFrame timeSheet : this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame()) {
			//割増時間帯が作�?されて�?��か確�?
			if(timeSheet.getPremiumTimeSheetInPredetermined().isPresent()) {
				
					val newTimeSpan = timeSheet.timeSheet.getTimeSpan().getNotDuplicationWith(timeSheet.getPremiumTimeSheetInPredetermined().get().getTimeSheet());
					//就業時間�?時間帯と割増時間帯の重なって�?���?���?���?
					//就業時間�?時間帯を作り直�?
					if(newTimeSpan.isPresent()) {
						renewWithinFrame.add(new WithinWorkTimeFrame(timeSheet.getWorkingHoursTimeNo(),
																	 new TimeZoneRounding(newTimeSpan.get().getStart(),newTimeSpan.get().getEnd(),timeSheet.getTimeSheet().getRounding()),
																	 newTimeSpan.get().getSpan(),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan.get()),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan.get()),
																	 timeSheet.getDuplicatedBonusPayNotStatic(timeSheet.getBonusPayTimeSheet(), newTimeSpan.get()),//�?給
																	 timeSheet.getMidNightTimeSheet().isPresent()
																	 	?timeSheet.getDuplicateMidNightNotStatic(timeSheet.getMidNightTimeSheet().get(),newTimeSpan.get())
																	 	:Optional.empty(),//深�?
																	 timeSheet.getDuplicatedSpecBonusPayzNotStatic(timeSheet.getSpecBonusPayTimesheet(), newTimeSpan.get()),//特定日�?給
																	 timeSheet.getLateTimeSheet(),
																	 timeSheet.getLeaveEarlyTimeSheet()
											 ));
						
					}
					
					returnList.add(new OverTimeFrameTimeSheetForCalc(new TimeZoneRounding(timeSheet.getPremiumTimeSheetInPredetermined().get().getTimeSheet().getStart(),timeSheet.getPremiumTimeSheetInPredetermined().get().getTimeSheet().getEnd(),null),
																						timeSheet.getPremiumTimeSheetInPredetermined().get().getTimeSheet(),
													  								 Collections.emptyList(),
													  								 Collections.emptyList(),
													  								 Collections.emptyList(),
													  								 Collections.emptyList(),
													  								 Optional.empty(),
													  								 new OverTimeFrameTime(new OverTimeFrameNo(10),
													  										 			   TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
													  										 			   TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)),
													  										 			   new AttendanceTime(0),
													  										 			   new AttendanceTime(0)),
													  								 StatutoryAtr.Excess,
													  								 false,
													  								 new EmTimezoneNo(0),
													  								 false,
													  								 Optional.empty(),
													  								 Optional.empty()
													  								 ));
				
			}
		}
		//�?定�?割増時間�?期化
		if(this.withinWorkingTimeSheet.isPresent()) {
			this.withinWorkingTimeSheet.get().resetPremiumTimeSheet();
		}
		if(!renewWithinFrame.isEmpty()) {
			this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame().clear();
			this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame().addAll(renewWithinFrame);
		}
		return OverTimeFrameTimeSheetForCalc.diciaionCalcStatutory(statutorySet, 
															dailyTime, 
															returnList, 
															autoCalculationSet, 
															breakdownTimeDay, 
															overTimeHourSetList, 
															dailyUnit, 
															holidayCalcMethodSet, 
															withinWorkingTimeSheet.get(), 
															vacationClass, 
															timevacationUseTimeOfDaily, 
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
															holidayAddtionSet);
	}

	/**
	 * �?��夜時間�?算�?結果から深夜時間�?合計を算�?する
	 * 
	 * @return 深夜時�?
	 */
	public ExcessOfStatutoryTimeOfDaily calcMidNightTime(ExcessOfStatutoryTimeOfDaily excessOfDaily) {
		// ExcessOverTimeWorkMidNightTime excessHolidayWorkMidNight =
		// excessOfDaily.getOverTimeWork().get().calcMidNightTimeIncludeOverTimeWork();
		// HolidayMidnightWork excessMidNight =
		// excessOfDaily.getWorkHolidayTime().get().calcMidNightTimeIncludeHolidayWorkTime(autoCalcSet);
		int beforeTime = 0;
		int totalTime = 0/* 残業深夜と休�?深夜�?合計算�? */;
		excessOfDaily.setExcessOfStatutoryMidNightTime(
				new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(totalTime)), new AttendanceTime(beforeTime)));
		return excessOfDaily;
	}

	/**
	 * 就�??残業�??休�?時間�??�?給時間の合計を求め�?
	 */
	public List<BonusPayTime> calcBonusPayTime(AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,
											   CalAttrOfDailyPerformance calcAtrOfDaily, BonusPayAtr bonusPayAtr) {
		
		List<BonusPayTime> overTimeBonusPay = new ArrayList<>();
		List<BonusPayTime> holidayWorkBonusPay = new ArrayList<>();
		List<BonusPayTime> withinBonusPay = new ArrayList<>();
		if(withinWorkingTimeSheet.isPresent())
			withinBonusPay = withinWorkingTimeSheet.get().calcBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);
		
		if(outsideWorkTimeSheet.isPresent())
		{
			if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) { 
				overTimeBonusPay = outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().calcBonusPayTimeInOverWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
			
			if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
				holidayWorkBonusPay = outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().calcBonusPayTimeInHolidayWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
		}
		return calcBonusPayTime(withinBonusPay,overTimeBonusPay,holidayWorkBonusPay);
	}
	
	/**
	 * 就�??残業�??休�?時間�??特定加給時間の合計を求め�?
	 */
	public List<BonusPayTime> calcSpecBonusPayTime(AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,
												   CalAttrOfDailyPerformance calcAtrOfDaily,BonusPayAtr bonusPayAtr){
		List<BonusPayTime> overTimeBonusPay = new ArrayList<>();
		List<BonusPayTime> holidayWorkBonusPay = new ArrayList<>();
		List<BonusPayTime> withinBonusPay = new ArrayList<>();
		
		if(withinWorkingTimeSheet.isPresent())
				withinWorkingTimeSheet.get().calcSpecifiedBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);

		if(outsideWorkTimeSheet.isPresent())
		{
			if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) { 
				overTimeBonusPay = outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().calcSpecBonusPayTimeInOverWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
			
			if(outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
				holidayWorkBonusPay = outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().get().calcSpecBonusPayTimeInHolidayWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
		}
		return calcBonusPayTime(withinBonusPay,overTimeBonusPay,holidayWorkBonusPay);
	}
	
	/**
	 * 就・残�?休�?�?給時間を合計す�?
	 * @param withinBonusPay
	 * @param overTimeBonusPay
	 * @param holidayWorkBonusPay
	 * @return�?合計後�?�?算時�?(Noでユニ�?ク)
	 */
	private List<BonusPayTime> calcBonusPayTime(List<BonusPayTime> withinBonusPay ,
								   List<BonusPayTime> overTimeBonusPay ,
								   List<BonusPayTime> holidayWorkBonusPay){
		List<BonusPayTime> returnList = new ArrayList<>();
		
		overTimeBonusPay.addAll(holidayWorkBonusPay);
		val excessPayTime = overTimeBonusPay;
		for(int bonusPayNo = 1 ; bonusPayNo <= 10 ; bonusPayNo++) {
			returnList.add(addWithinAndExcessBonusTime(sumBonusPayTime(getByBonusPayNo(withinBonusPay,bonusPayNo),bonusPayNo),
													   sumBonusPayTime(getByBonusPayNo(excessPayTime,bonusPayNo),bonusPayNo)
													   ,bonusPayNo));
		}
		return returnList;
	}
	
	/**
	 * 受け取っ�?2つの�?給時間が持つ時間を合�?
	 * @param within
	 * @param excess
	 * @param bonusPayNo
	 * @return
	 */
	private BonusPayTime addWithinAndExcessBonusTime(BonusPayTime within,BonusPayTime excess,int bonusPayNo) {
		return new BonusPayTime(bonusPayNo,
								within.getBonusPayTime().addMinutes(excess.getBonusPayTime().valueAsMinutes()),
								within.getWithinBonusPay().addMinutes(excess.getWithinBonusPay().getTime(), 
																	  excess.getWithinBonusPay().getCalcTime()),
								within.getExcessBonusPayTime().addMinutes(excess.getExcessBonusPayTime().getTime(), 
										  								  excess.getExcessBonusPayTime().getCalcTime())
								);
	}
	
	/**
	 * �?��間�?合計を算�?
	 * @param bonusPayList�?�?給時間のリス�?
	 * @param bonusPayNo�?�?給時間?�??
	 * @return�?合計時間�?�?給時間
	 */
	private BonusPayTime sumBonusPayTime(List<BonusPayTime> bonusPayList, int bonusPayNo) {
		AttendanceTime bonusPayTime =  new AttendanceTime(bonusPayList.stream().map(tc -> tc.getBonusPayTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime withinTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getWithinBonusPay().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime withinCalcTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getWithinBonusPay().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime excessTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getExcessBonusPayTime().getTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		AttendanceTime excessCalcTime = new AttendanceTime(bonusPayList.stream().map(tc -> tc.getExcessBonusPayTime().getCalcTime().valueAsMinutes()).collect(Collectors.summingInt(tc -> tc)));
		
		return new BonusPayTime(bonusPayNo,
								bonusPayTime,
								TimeWithCalculation.createTimeWithCalculation(withinTime, withinCalcTime),
								TimeWithCalculation.createTimeWithCalculation(excessTime, excessCalcTime));
	}
	
	/**
	 * 受け取った加給時間?�?�を持つ�?給時間を取�?
	 * @param bonusPayTime �?給時間
	 * @param bonusPayNo�?�?給時間?�??
	 * @return�?�?給時間リス�?
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	
	/**
	 * 控除時間を取�?
	 * @param dedClassification 
	 * @param dedAtr
	 * @param statutoryAtrs
	 * @param pertimesheet
	 * @return
	 */
	public TimeWithCalculation calcWithinTotalTime(ConditionAtr dedClassification, DeductionAtr dedAtr,StatutoryAtr statutoryAtr,TimeSheetRoundingAtr pertimesheet) {
		if(statutoryAtr.isStatutory()) {
			if(this.withinWorkingTimeSheet.isPresent()) {
				return TimeWithCalculation.sameTime(this.withinWorkingTimeSheet.get().calculationAllFrameDeductionTime(dedAtr, dedClassification));
			}
		}
		else if(statutoryAtr.isExcess()) {
			if(this.getOutsideWorkTimeSheet().isPresent()) {
				AttendanceTime overTime = this.getOutsideWorkTimeSheet().get().caluclationAllOverTimeFrameTime(dedAtr, dedClassification);
				AttendanceTime holidaytime = this.getOutsideWorkTimeSheet().get().caluclationAllHolidayFrameTime(dedAtr, dedClassification);
				return TimeWithCalculation.sameTime(overTime.addMinutes(holidaytime.valueAsMinutes()));
			}
		}
		return TimeWithCalculation.sameTime(new AttendanceTime(0));
	}

	 /**
	 * フレ�?��スの時間帯作�?
	 * @param integrationOfDaily 
	 * @param integrationOfDaily 
	 */
	 public void createTimeSheetAsFlex(
			 		WorkingSystem workingSystem, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
					BonusPaySetting bonusPaySetting,
					List<HDWorkTimeSheetSetting> fixOff,List<OverTimeOfTimeZoneSet> overTimeHourSetList,List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem,  
					OverDayEndCalcSet dayEndSet,WorkType beforeDay, WorkType toDay, WorkType afterDay,
					BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
					LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet,
					WorkTimeSetting workTime,
					FlexWorkSetting flexWorkSetting,OutingTimeOfDailyPerformance outingTimeSheetofDaily,
					TimeSpanForCalc oneDayTimeSpan,TimeLeavingOfDailyPerformance attendanceLeaveWork,WorkTimeDivision workTimeDivision
					,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,MidNightTimeSheet midNightTimeSheet,DailyCalculationPersonalInformation personalInfo,
					HolidayCalcMethodSet holidayCalcMethodSet,Optional<CoreTimeSetting> coreTimeSetting,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
            		VacationClass vacationClass, TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
            		Optional<WorkTimeCode> siftCode, Optional<PersonalLaborCondition> personalCondition, 
            		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
            		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet){
		 //if(!flexTimeSet.getUseFixedRestTime()){
			// predetermineTimeSetForCalc.correctPredetermineTimeSheet(dailyWork);
			 /*�?��早�?処�?*/
			// for() {
			//	 WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSet,fixedWorkSetting);
				 /*�?��時間の計�?*/
				 /*早�?時間の計�?*/
			// }
			 //WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSetForCalc,fixedWorkSetting);
		 //}
		 //控除時間帯の作�?
		 val deductionTimeSheet = provisionalDeterminationOfDeductionTimeSheet(outingTimeSheetofDaily,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flexWorkSetting.getOffdayWorkTime().getRestTimezone(),flexWorkSetting.getRestSetting());
		 /*固定勤務�?時間帯作�?*/
		 val fixedWorkTimeZoneSet = new CommonFixedWorkTimezoneSet();
		 fixedWorkTimeZoneSet.forFlex(flexWorkSetting.getLstHalfDayWorkTimezone());
		 theDayOfWorkTimesLoop( workingSystem,  predetermineTimeSetForCalc,
				 	fixedWorkTimeZoneSet,  flexWorkSetting.getCommonSetting(),  bonusPaySetting,
					overTimeHourSetList,  fixOff,  dayEndSet,
					holidayTimeWorkItem,  beforeDay,  toDay,  afterDay,
					 breakdownTimeDay,  dailyTime,  calcSetinIntegre,
					 statutorySet,  prioritySet,
					 deductionTimeSheet,  workTime,midNightTimeSheet,personalInfo,holidayCalcMethodSet,coreTimeSetting,dailyUnit,breakTimeList,
					 vacationClass, timevacationUseTimeOfDaily, siftCode, 
					 personalCondition, leaveEarly, leaveEarly, illegularAddSetting, flexAddSetting, regularAddSetting, holidayAddtionSet);
		 /*コアタイ�?のセ�?��*/
		 //this.withinWorkingTimeSheet.set(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting()));
		 if(this.withinWorkingTimeSheet.isPresent())
			 this.withinWorkingTimeSheet = Finally.of(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting()));
	 }
	
//	 /**
//	 * 流動休�?用の控除時間帯作�?
//	 */
//	 public void createFluidBreakTime(DeductionAtr deductionAtr) {
//	 DeductionTimeSheet.createDedctionTimeSheet(acqAtr, setMethod,
//	 clockManage, dailyGoOutSheet, oneDayRange, CommonSet,
//	 attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fixedSet,
//	 breakTimeSheet);
//	
//	 }

	// ?�＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊�?
	//
	// /**
	// * 流動勤務�?時間帯作�?
	// */
	// public void createFluidWork(
	// int workNo,
	// WorkTime workTime,
	// AttendanceLeavingWorkOfDaily attendanceLeavingWork,
	// DeductionTimeSheet deductionTimeSheet,
	// PredetermineTimeSet predetermineTimeSet,
	// WithinWorkTimeSheet withinWorkTimeSheet,
	// WithinWorkTimeFrame withinWorkTimeFrame,
	// HolidayWorkTimeSheet holidayWorkTimeSheet,
	// WorkType worktype) {
	// //�?定時間設定をコピ�?して計算用の�?定時間設定を作�?する
	// this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
	// predetermineTimeSet.getAdditionSet(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getAMEndTime(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getPMStartTime());
	// //出�?勤�?��ー�?
	// for(AttendanceLeavingWork attendanceLeavingWork :
	// attendanceLeavingWork.getAttendanceLeavingWork(workNo)) {
	// //事前に�?��早�?、控除時間帯を取得す�?
	// this.getForDeductionTimeSheetList(workNo, attendanceLeavingWork,
	// predetermineTimeSet, deductionTimeSheet ,workInformationOfDaily,
	// workType, withinWorkTimeFrame);
	// }
	// //「�?勤系」か「休�?系」か判断する
	// boolean isWeekDayAttendance = worktype.isWeekDayAttendance();
	// //時間休暇�?算残時間未割当�?時間休暇�?算残時�?
	//
	// if(isWeekDayAttendance) {//出勤系の場�?
	// //流動勤務（就�??�平日??
	// WithinWorkTimeSheet newWithinWorkTimeSheet =
	// withinWorkTimeSheet.createAsFluidWork(predetermineTimeSetForCalc,
	// worktype, workInformationOfDaily, fluidWorkSetting, deductionTimeSheet);
	// //流動勤務（就外�?�平日??
	//
	// }else{//休�?系の場�?
	// //流動勤務（休日出勤??
	// HolidayWorkTimeSheet holidayWorkTimeSheet =
	// holidayWorkTimeSheet.createholidayWorkTimeSheet(attendanceLeavingWork,
	// workingTimes, deductionTimeSheet, worktype, holidayWorkTimeOfDaily,
	// calcRange);
	// }
	//
	//
	// }
	//
	// /**
	// * 事前に�?��早�?、控除時間帯を取得す�?
	// * @param workNo
	// * @param attendanceLeavingWork 出�?勤
	// * @return
	// */
	// public List<TimeSheetOfDeductionItem> getForDeductionTimeSheetList(
	// int workNo,
	// AttendanceLeavingWork attendanceLeavingWork,
	// PredetermineTimeSet predetermineTimeSet,
	// DeductionTimeSheet deductionTimeSheet,
	// WorkInformationOfDaily workInformationOfDaily,
	// WorkType workType,
	// WithinWorkTimeFrame withinWorkTimeFrame){
	//
	// //�?定時間帯を取得す�?(流動計算で使用する�?定時間�?作�?)
	// createPredetermineTimeSheetForFluid(workNo, predetermineTimeSet,
	// workType, workInformationOfDaily);
	// //計算�?��を判断する
	// withinWorkTimeFrame.createWithinWorkTimeFrameForFluid(attendanceLeavingWork,
	// dailyWork, predetermineTimeSetForCalc);
	// //�?��時間帯を控除
	// withinWorkTimeFrame.getLateTimeSheet().lateTimeCalcForFluid(withinWorkTimeFrame,
	// lateRangeForCalc, workTimeCommonSet, lateDecisionClock,
	// deductionTimeSheet);
	// //控除時間帯の仮確�?
	// this.provisionalDeterminationOfDeductionTimeSheet(deductionTimeSheet);
	// //早�?時間帯を控除
	//
	// //勤務間の休�?設定を取�?
	//
	// }
	//
	// /**
	// * 計算用�?定時間設定を作�?する?�流動用??
	// * @return
	// */
	// public void createPredetermineTimeSheetForFluid(
	// int workNo,
	// PredetermineTimeSet predetermineTimeSet,
	// WorkType workType,
	// WorkInformationOfDaily workInformationOfDaily) {
	//
	// //予定と実績が同じ勤務かど�?��確�?
	// if(workInformationOfDaily.isMatchWorkInfomation()/*予定時間帯に値が�?って�?��か�?チェ�?��を追�?する�?��あ�?*/)
	// {
	// //予定時間帯を取得す�?
	// ScheduleTimeSheet scheduleTimeSheet =
	// workInformationOfDaily.getScheduleTimeSheet(workNo);
	// //�?定時間帯設定�?時間帯を�?て取得す�?
	// List<TimeSheetWithUseAtr> timeSheetList =
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets();
	// //変更対象の時間帯を取�?
	// List<TimeSheetWithUseAtr> list = timeSheetList.stream().filter(ts ->
	// ts.getCount()==workNo).collect(Collectors.toList());
	// TimeSheetWithUseAtr timeSheet = list.get(0);
	// //予定時間帯と変更対象の時間帯を基に時間帯を作�?
	// TimeSheetWithUseAtr targetTimeSheet = new TimeSheetWithUseAtr(
	// timeSheet.getUseAtr(),
	// scheduleTimeSheet.getAttendance(),
	// scheduleTimeSheet.getLeaveWork(),
	// workNo);
	// //変更対象以外�?時間帯を取�?
	// List<TimeSheetWithUseAtr> list2 = timeSheetList.stream().filter(ts ->
	// ts.getCount()!=workNo).collect(Collectors.toList());
	// TimeSheetWithUseAtr timeSheet2 = list2.get(0);
	//
	// List<TimeSheetWithUseAtr> newTimeSheetList =
	// Arrays.asList(targetTimeSheet,timeSheet2);
	//
	// this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
	// this.predetermineTimeSetForCalc.getAdditionSet(),
	// newTimeSheetList,
	// this.predetermineTimeSetForCalc.getAMEndTime(),
	// this.predetermineTimeSetForCalc.getPMStartTime());
	// }
	// //午前勤務�?�午後勤務�?場合に時間帯を補正する処�?
	// this.predetermineTimeSetForCalc.getPredetermineTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());
	// }
	//
	//
	 /**
	 * 控除時間帯の仮確�?
	 */
	 public static DeductionTimeSheet provisionalDeterminationOfDeductionTimeSheet(OutingTimeOfDailyPerformance outingTimeSheetofDaily,
				TimeSpanForCalc oneDayTimeSpan,TimeLeavingOfDailyPerformance attendanceLeaveWork,WorkTimeDivision workTimeDivision
				,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,FlowWorkRestTimezone flowRestTimezone,FlowWorkRestSetting flowRestSetting) {
		 //控除用
		 val dedTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Deduction, outingTimeSheetofDaily,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting);
	 	 //計上用
	 	 val recordTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Appropriate, outingTimeSheetofDaily,
	 			 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting);
	 
	 	return new DeductionTimeSheet(dedTimeSheet,recordTimeSheet); 
	 }
	 
	 /**
	  * 大塚モード使用時専用の�?��、早�?削除処�?
	  */
	 public void cleanLateLeaveEarlyTimeForOOtsuka() {
		 if(this.getWithinWorkingTimeSheet() != null
			&& this.getWithinWorkingTimeSheet().isPresent()){
			 this.withinWorkingTimeSheet.get().cleanLateLeaveEarlyTimeForOOtsuka();
		 }
	 }
	 
	 public void clearLeavingTime() {
		 this.attendanceLeavingWork = new TimeLeavingOfDailyPerformance(this.getAttendanceLeavingWork().getEmployeeId(), new WorkTimes(0), Collections.emptyList(), this.getAttendanceLeavingWork().getYmd());
	 }
	 
}
