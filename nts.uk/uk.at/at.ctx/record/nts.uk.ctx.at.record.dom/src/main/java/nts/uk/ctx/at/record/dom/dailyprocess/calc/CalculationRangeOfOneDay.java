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
 * 1æ¥ã®è¨ç®ç¯?²
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

	private Finally<TimevacationUseTimeOfDaily> timeVacationAdditionRemainingTime = Finally.empty();// æéä¼æå?ç®æ®æé?


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
	 * å°±æ¥­æéå¸¯ã®ä½æ?
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
	 *            åæ¥ã®å¤åç¨®é¡?
	 * @param toDay
	 *            å½æ¥ã®å¤åç¨®é¡?
	 * @param afterDay
	 *            ç¿æ¥ã®å¤åç¨®é¡?
	 * @param breakdownTimeDay
	 * @param dailyTime
	 *            æ³å®å´åæé?
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
		/* åºå®æ§é¤æéå¸¯ã®ä½æ? */
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
	 * æéå¸¯ä½æ?(å¤ååæ°å??ã«ã¼ã?) å°±æ¥­æéå??å¤ã?å¦ç?
	 * 
	 * @param workingSystem
	 *            å´åå¶ã¯ã©ã¹
	 * @param predetermineTimeSet
	 *            æ?å®æéè¨­å®ã¯ã©ã¹
	 * @param fixedWorkSetting
	 *            åºå®å¤åè¨­å®ã¯ã©ã¹
	 * @param workTimeCommonSet
	 *            å°±æ¥­æéå¸¯ã®å±éè¨­å®ã¯ã©ã¹
	 * @param bonusPaySetting
	 *            å?çµ¦è¨­å®ã¯ã©ã¹
	 * @param overTimeHourSetList
	 *            æ®æ¥­æéã®æéå¸¯è¨­å®ã¯ã©ã¹
	 * @param fixOff
	 *            åºå®å¤åã?ä¼æ¥åºå¤ç¨å¤åæéå¸¯ã¯ã©ã¹
	 * @param dayEndSet
	 *            0æè·¨ãè¨ç®è¨­å®ã¯ã©ã¹
	 * @param overDayEndSet
	 *            å°±æ¥­æéå¸¯ã®å±éè¨­å®ã¯ã©ã¹
	 * @param holidayTimeWorkItem
	 *            ä¼å?æ?æéå¸¯
	 * @param beforeDay
	 *            å¤åç¨®é¡ã¯ã©ã¹
	 * @param toDay
	 *            å¤åç¨®é¡ã¯ã©ã¹
	 * @param afterDay
	 *            å¤åç¨®é¡ã¯ã©ã¹
	 * @param breakdownTimeDay
	 *            1æ¥ã®æéå?¨³ã¯ã©ã¹
	 * @param dailyTime
	 *            æ³å®å´åæé?
	 * @param calcSetinIntegre
	 *            æ®æ¥­æéã®èªåè¨ç®è¨­å®ã¯ã©ã¹
	 * @param statutorySet
	 *            æ³å®å?æ®æ¥­è¨­å®?
	 * @param prioritySet
	 *            æ³å®å?åªåè¨­å®?
	 * @param deductionTimeSheet
	 *            æ§é¤æéå¸¯
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
			/* è¨ç®å¯¾è±¡å¤ã?å¦ç? */
			return;
		}
		for (int workNumber = 1; workNumber <= attendanceLeavingWork.getTimeLeavingWorks().size(); workNumber++) {
			
			/* å°±æ¥­å??æéå¸¯ä½æ? */
			//æå»ã¯ããåæã§åã
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
			/* å°±æ¥­å¤ã?æéå¸¯ä½æ? */
			//æå»ã¯ããåæã§åã
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
				//æ®æ¥­
				if(outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) {
					List<OverTimeFrameTimeSheetForCalc> addOverList = createOutSideWorkTimeSheet.getOverTimeWorkSheet().isPresent()? createOutSideWorkTimeSheet.getOverTimeWorkSheet().get().getFrameTimeSheets():Collections.emptyList();
					outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().getFrameTimeSheets().addAll(addOverList);
				}
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(createOutSideWorkTimeSheet.getOverTimeWorkSheet(),this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet()));
				}
				//ä¼å?
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
				//æ®æ¥­è¿½å?
				else {
					this.outsideWorkTimeSheet = Finally.of(new OutsideWorkTimeSheet(Optional.of(new OverTimeSheet(new RaisingSalaryTime(),
						  																					  	  overTimeFrame,
						  																					  	  new SubHolOccurrenceInfo()
																					)),
												this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet()
												));
				}
			}
			//æ?å®å¤ã¤ã³ã¹ã¿ã³ã¹ä½æ?
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
	 * å¤§å¡ã??åºå®å¤åã?æµåæ®æ¥­å¯¾å¿?(æ?å®å?å²å¢ãæ®æ¥­æéå¸¯ã¸ç§»ã?)
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
		//æ?å®å?å°±æ¥­æéæ?ã®ã«ã¼ã?
		for(WithinWorkTimeFrame timeSheet : this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame()) {
			//å²å¢æéå¸¯ãä½æ?ããã¦ã?ãç¢ºèª?
			if(timeSheet.getPremiumTimeSheetInPredetermined().isPresent()) {
				
					val newTimeSpan = timeSheet.timeSheet.getTimeSpan().getNotDuplicationWith(timeSheet.getPremiumTimeSheetInPredetermined().get().getTimeSheet());
					//å°±æ¥­æéæ?æéå¸¯ã¨å²å¢æéå¸¯ã®éãªã£ã¦ã?ªã?¨å?§ã?
					//å°±æ¥­æéæ?æéå¸¯ãä½ãç´ã?
					if(newTimeSpan.isPresent()) {
						renewWithinFrame.add(new WithinWorkTimeFrame(timeSheet.getWorkingHoursTimeNo(),
																	 new TimeZoneRounding(newTimeSpan.get().getStart(),newTimeSpan.get().getEnd(),timeSheet.getTimeSheet().getRounding()),
																	 newTimeSpan.get().getSpan(),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan.get()),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan.get()),
																	 timeSheet.getDuplicatedBonusPayNotStatic(timeSheet.getBonusPayTimeSheet(), newTimeSpan.get()),//å?çµ¦
																	 timeSheet.getMidNightTimeSheet().isPresent()
																	 	?timeSheet.getDuplicateMidNightNotStatic(timeSheet.getMidNightTimeSheet().get(),newTimeSpan.get())
																	 	:Optional.empty(),//æ·±å¤?
																	 timeSheet.getDuplicatedSpecBonusPayzNotStatic(timeSheet.getSpecBonusPayTimesheet(), newTimeSpan.get()),//ç¹å®æ¥å?çµ¦
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
		//æ?å®å?å²å¢æéå?æå
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
	 * å?·±å¤æéã?ç®å?çµæããæ·±å¤æéã?åè¨ãç®å?ãã
	 * 
	 * @return æ·±å¤æé?
	 */
	public ExcessOfStatutoryTimeOfDaily calcMidNightTime(ExcessOfStatutoryTimeOfDaily excessOfDaily) {
		// ExcessOverTimeWorkMidNightTime excessHolidayWorkMidNight =
		// excessOfDaily.getOverTimeWork().get().calcMidNightTimeIncludeOverTimeWork();
		// HolidayMidnightWork excessMidNight =
		// excessOfDaily.getWorkHolidayTime().get().calcMidNightTimeIncludeHolidayWorkTime(autoCalcSet);
		int beforeTime = 0;
		int totalTime = 0/* æ®æ¥­æ·±å¤ã¨ä¼å?æ·±å¤ã?åè¨ç®å? */;
		excessOfDaily.setExcessOfStatutoryMidNightTime(
				new ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(totalTime)), new AttendanceTime(beforeTime)));
		return excessOfDaily;
	}

	/**
	 * å°±å??æ®æ¥­å??ä¼å?æéå??å?çµ¦æéã®åè¨ãæ±ãã?
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
	 * å°±å??æ®æ¥­å??ä¼å?æéå??ç¹å®å çµ¦æéã®åè¨ãæ±ãã?
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
	 * å°±ã»æ®ã?ä¼ã?å?çµ¦æéãåè¨ãã?
	 * @param withinBonusPay
	 * @param overTimeBonusPay
	 * @param holidayWorkBonusPay
	 * @returnã?åè¨å¾ã?å?ç®æé?(Noã§ã¦ãã?ã¯)
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
	 * åãåã£ã?2ã¤ã®å?çµ¦æéãæã¤æéãåç®?
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
	 * å?éã?åè¨ãç®å?
	 * @param bonusPayListã?å?çµ¦æéã®ãªã¹ã?
	 * @param bonusPayNoã?å?çµ¦æé?®??
	 * @returnã?åè¨æéã?å?çµ¦æé
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
	 * åãåã£ãå çµ¦æé?®?ãæã¤å?çµ¦æéãåå¾?
	 * @param bonusPayTime å?çµ¦æé
	 * @param bonusPayNoã?å?çµ¦æé?®??
	 * @returnã?å?çµ¦æéãªã¹ã?
	 */
	private List<BonusPayTime> getByBonusPayNo(List<BonusPayTime> bonusPayTime,int bonusPayNo){
		return bonusPayTime.stream().filter(tc -> tc.getBonusPayTimeItemNo() == bonusPayNo).collect(Collectors.toList());
	}
	
	
	/**
	 * æ§é¤æéãåå¾?
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
	 * ãã¬ã?¯ã¹ã®æéå¸¯ä½æ?
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
			 /*é?»æ©é?å¦ç?*/
			// for() {
			//	 WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSet,fixedWorkSetting);
				 /*é?»æéã®è¨ç®?*/
				 /*æ©é?æéã®è¨ç®?*/
			// }
			 //WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSetForCalc,fixedWorkSetting);
		 //}
		 //æ§é¤æéå¸¯ã®ä½æ?
		 val deductionTimeSheet = provisionalDeterminationOfDeductionTimeSheet(outingTimeSheetofDaily,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flexWorkSetting.getOffdayWorkTime().getRestTimezone(),flexWorkSetting.getRestSetting());
		 /*åºå®å¤åã?æéå¸¯ä½æ?*/
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
		 /*ã³ã¢ã¿ã¤ã?ã®ã»ã?*/
		 //this.withinWorkingTimeSheet.set(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting()));
		 if(this.withinWorkingTimeSheet.isPresent())
			 this.withinWorkingTimeSheet = Finally.of(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting()));
	 }
	
//	 /**
//	 * æµåä¼æ?ç¨ã®æ§é¤æéå¸¯ä½æ?
//	 */
//	 public void createFluidBreakTime(DeductionAtr deductionAtr) {
//	 DeductionTimeSheet.createDedctionTimeSheet(acqAtr, setMethod,
//	 clockManage, dailyGoOutSheet, oneDayRange, CommonSet,
//	 attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fixedSet,
//	 breakTimeSheet);
//	
//	 }

	// ?ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼ï¼?
	//
	// /**
	// * æµåå¤åã?æéå¸¯ä½æ?
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
	// //æ?å®æéè¨­å®ãã³ãã?ãã¦è¨ç®ç¨ã®æ?å®æéè¨­å®ãä½æ?ãã
	// this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
	// predetermineTimeSet.getAdditionSet(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getAMEndTime(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getPMStartTime());
	// //åºé?å¤å?«ã¼ã?
	// for(AttendanceLeavingWork attendanceLeavingWork :
	// attendanceLeavingWork.getAttendanceLeavingWork(workNo)) {
	// //äºåã«é?»æ©é?ãæ§é¤æéå¸¯ãåå¾ãã?
	// this.getForDeductionTimeSheetList(workNo, attendanceLeavingWork,
	// predetermineTimeSet, deductionTimeSheet ,workInformationOfDaily,
	// workType, withinWorkTimeFrame);
	// }
	// //ãå?å¤ç³»ãããä¼å?ç³»ããå¤æ­ãã
	// boolean isWeekDayAttendance = worktype.isWeekDayAttendance();
	// //æéä¼æå?ç®æ®æéæªå²å½â?æéä¼æå?ç®æ®æé?
	//
	// if(isWeekDayAttendance) {//åºå¤ç³»ã®å ´å?
	// //æµåå¤åï¼å°±å??å¹³æ¥??
	// WithinWorkTimeSheet newWithinWorkTimeSheet =
	// withinWorkTimeSheet.createAsFluidWork(predetermineTimeSetForCalc,
	// worktype, workInformationOfDaily, fluidWorkSetting, deductionTimeSheet);
	// //æµåå¤åï¼å°±å¤ã?å¹³æ¥??
	//
	// }else{//ä¼å?ç³»ã®å ´å?
	// //æµåå¤åï¼ä¼æ¥åºå¤??
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
	// * äºåã«é?»æ©é?ãæ§é¤æéå¸¯ãåå¾ãã?
	// * @param workNo
	// * @param attendanceLeavingWork åºé?å¤
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
	// //æ?å®æéå¸¯ãåå¾ãã?(æµåè¨ç®ã§ä½¿ç¨ããæ?å®æéã?ä½æ?)
	// createPredetermineTimeSheetForFluid(workNo, predetermineTimeSet,
	// workType, workInformationOfDaily);
	// //è¨ç®ç¯?²ãå¤æ­ãã
	// withinWorkTimeFrame.createWithinWorkTimeFrameForFluid(attendanceLeavingWork,
	// dailyWork, predetermineTimeSetForCalc);
	// //é?»æéå¸¯ãæ§é¤
	// withinWorkTimeFrame.getLateTimeSheet().lateTimeCalcForFluid(withinWorkTimeFrame,
	// lateRangeForCalc, workTimeCommonSet, lateDecisionClock,
	// deductionTimeSheet);
	// //æ§é¤æéå¸¯ã®ä»®ç¢ºå®?
	// this.provisionalDeterminationOfDeductionTimeSheet(deductionTimeSheet);
	// //æ©é?æéå¸¯ãæ§é¤
	//
	// //å¤åéã®ä¼æ?è¨­å®ãåå¾?
	//
	// }
	//
	// /**
	// * è¨ç®ç¨æ?å®æéè¨­å®ãä½æ?ãã?æµåç¨??
	// * @return
	// */
	// public void createPredetermineTimeSheetForFluid(
	// int workNo,
	// PredetermineTimeSet predetermineTimeSet,
	// WorkType workType,
	// WorkInformationOfDaily workInformationOfDaily) {
	//
	// //äºå®ã¨å®ç¸¾ãåãå¤åãã©ã?ç¢ºèª?
	// if(workInformationOfDaily.isMatchWorkInfomation()/*äºå®æéå¸¯ã«å¤ãå?ã£ã¦ã?ãã?ãã§ã?¯ãè¿½å?ããå¿?¦ãã?*/)
	// {
	// //äºå®æéå¸¯ãåå¾ãã?
	// ScheduleTimeSheet scheduleTimeSheet =
	// workInformationOfDaily.getScheduleTimeSheet(workNo);
	// //æ?å®æéå¸¯è¨­å®ã?æéå¸¯ãå?ã¦åå¾ãã?
	// List<TimeSheetWithUseAtr> timeSheetList =
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets();
	// //å¤æ´å¯¾è±¡ã®æéå¸¯ãåå¾?
	// List<TimeSheetWithUseAtr> list = timeSheetList.stream().filter(ts ->
	// ts.getCount()==workNo).collect(Collectors.toList());
	// TimeSheetWithUseAtr timeSheet = list.get(0);
	// //äºå®æéå¸¯ã¨å¤æ´å¯¾è±¡ã®æéå¸¯ãåºã«æéå¸¯ãä½æ?
	// TimeSheetWithUseAtr targetTimeSheet = new TimeSheetWithUseAtr(
	// timeSheet.getUseAtr(),
	// scheduleTimeSheet.getAttendance(),
	// scheduleTimeSheet.getLeaveWork(),
	// workNo);
	// //å¤æ´å¯¾è±¡ä»¥å¤ã?æéå¸¯ãåå¾?
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
	// //ååå¤åã?åå¾å¤åã?å ´åã«æéå¸¯ãè£æ­£ããå¦ç?
	// this.predetermineTimeSetForCalc.getPredetermineTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());
	// }
	//
	//
	 /**
	 * æ§é¤æéå¸¯ã®ä»®ç¢ºå®?
	 */
	 public static DeductionTimeSheet provisionalDeterminationOfDeductionTimeSheet(OutingTimeOfDailyPerformance outingTimeSheetofDaily,
				TimeSpanForCalc oneDayTimeSpan,TimeLeavingOfDailyPerformance attendanceLeaveWork,WorkTimeDivision workTimeDivision
				,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,FlowWorkRestTimezone flowRestTimezone,FlowWorkRestSetting flowRestSetting) {
		 //æ§é¤ç¨
		 val dedTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Deduction, outingTimeSheetofDaily,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting);
	 	 //è¨ä¸ç¨
	 	 val recordTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Appropriate, outingTimeSheetofDaily,
	 			 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting);
	 
	 	return new DeductionTimeSheet(dedTimeSheet,recordTimeSheet); 
	 }
	 
	 /**
	  * å¤§å¡ã¢ã¼ãä½¿ç¨æå°ç¨ã®é?»ãæ©é?åé¤å¦ç?
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
