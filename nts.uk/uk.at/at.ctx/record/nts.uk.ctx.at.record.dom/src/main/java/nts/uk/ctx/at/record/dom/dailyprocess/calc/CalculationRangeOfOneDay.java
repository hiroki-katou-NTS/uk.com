package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.SubHolOccurrenceInfo;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.calcset.CalcMethodOfNoWorkingDay;
import nts.uk.ctx.at.record.dom.daily.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethod;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfEachPremiumHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.FlexCalcMethodOfHalfWork;
import nts.uk.ctx.at.record.dom.raborstandardact.flex.SettingOfFlexWork;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaisingSalaryTime;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 1日の計算範囲
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalculationRangeOfOneDay {

	//1日の範囲
	private TimeSpanForDailyCalc oneDayOfRange;

	//勤務情報
	private WorkInfoOfDailyPerformance workInformationOfDaily;
	
	@Setter
	//出退勤
	private TimeLeavingOfDailyPerformance attendanceLeavingWork;

	//所定時間設定
	private PredetermineTimeSetForCalc predetermineTimeSetForCalc;
	
	/*----------------Optional-----------------------*/
	//インターバル制度管理
	
	//非勤務時間帯
	private Optional<NonWorkingTimeSheet> nonWorkingTimeSheet;
	/*----------------------Finally------------------------*/
	//加給時間
	
	//就業時間内時間帯
	private Finally<WithinWorkTimeSheet> withinWorkingTimeSheet = Finally.empty();

	@Setter
	//就業時間外時間帯
	private Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet = Finally.empty();

	//出勤前時間
	private Finally<AttendanceTime> beforeAttendance = Finally.of(new AttendanceTime(0)); 
	
	//退勤後時間
	private Finally<AttendanceTime> afterLeaving = Finally.of(new AttendanceTime(0));



	public CalculationRangeOfOneDay(Finally<WithinWorkTimeSheet> withinWorkingTimeSheet,
			Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet, TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyPerformance attendanceLeavingWork, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			WorkInfoOfDailyPerformance workInformationofDaily,
			Optional<NonWorkingTimeSheet> nonWorkingTimeSheet) {
		this.withinWorkingTimeSheet = withinWorkingTimeSheet;
		this.outsideWorkTimeSheet = outsideWorkTimeSheet;
		this.oneDayOfRange = oneDayOfRange;
		this.attendanceLeavingWork = attendanceLeavingWork;
		this.predetermineTimeSetForCalc = predetermineTimeSetForCalc;
		this.workInformationOfDaily = workInformationofDaily;
		this.nonWorkingTimeSheet = nonWorkingTimeSheet;
	}

	/**
	 * 就業時間帯の作成
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
	 * @param bonuspaySetting
	 * @param overTimeHourSetList
	 * @param fixOff
	 * @param overDayEndCalcSet
	 * @param overDayEndSet
	 * @param holidayTimeWorkItem
	 * @param beforeDay
	 *            前日の勤務種類
	 * @param toDay
	 *            当日の勤務種類
	 * @param afterDay
	 *            翌日の勤務種類
	 * @param breakdownTimeDay
	 * @param dailyTime
	 *            法定労働時間
	 * @param calcSetinIntegre
	 * @param statutorySet
	 * @param prioritySet
	 * @param fixWoSetting 
	 * @param ootsukaIWFlag 
	 * @param integrationOfDaily 
	 */
	public void createWithinWorkTimeSheet(WorkingSystem workingSystem, WorkTimeMethodSet setMethod,
			RestClockManageAtr clockManage,  Optional<OutingTimeOfDailyPerformance> dailyGoOutSheet, CommonRestSetting commonSet,
			Optional<FixedRestCalculateMethod> fixedCalc, WorkTimeDivision workTimeDivision, 
			PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			FixedWorkSetting fixedWorkSetting, Optional<BonusPaySetting> bonuspaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, List<HDWorkTimeSheetSetting> fixOff, Optional<ZeroTime> overDayEndCalcSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet, WorkTimeSetting workTime,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList
			,MidNightTimeSheet midNightTimeSheet,DailyCalculationPersonalInformation personalInfo,Optional<CoreTimeSetting> coreTimeSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
    		VacationClass vacationClass, AttendanceTime timevacationUseTimeOfDaily,
    		Optional<WorkTimeCode> siftCode, 
    		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
    		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet,Optional<WorkTimezoneCommonSet> commonSetting,
    		WorkingConditionItem conditionItem,Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,
    		List<ShortWorkingTimeSheet> shortTimeSheets,WorkTimezoneShortTimeWorkSet workTimeShortTimeSet,
    		Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo, List<EmTimeZoneSet> fixWoSetting,
    		Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrSheets) {
		/* 固定控除時間帯の作成 */
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createTimeSheetForFixBreakTime(
				setMethod, clockManage, dailyGoOutSheet, this.oneDayOfRange, commonSet, attendanceLeavingWork,
				fixedCalc, workTimeDivision, breakTimeOfDailyList, shortTimeSheets,workTimeShortTimeSet,commonSetting,holidayCalcMethodSet
				,predetermineTimeSetForCalc,toDay,fixWoSetting);
		
		
		val fixedWorkTImeZoneSet = new CommonFixedWorkTimezoneSet();
		fixedWorkTImeZoneSet.forFixed(fixedWorkSetting.getLstHalfDayWorkTimezone());
		
		 Optional<DeductLeaveEarly> leaveLateSet = regularAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()
					 ?Optional.of(regularAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly())
					 :Optional.empty();
					 
		theDayOfWorkTimesLoop(workingSystem, predetermineTimeSetForCalc, fixedWorkTImeZoneSet,fixedWorkSetting.getCommonSetting(), bonuspaySetting,
				overTimeHourSetList, fixOff, overDayEndCalcSet, holidayTimeWorkItem, beforeDay, toDay, afterDay,
				breakdownTimeDay, dailyTime, calcSetinIntegre, statutorySet, prioritySet, deductionTimeSheet,
				workTime,midNightTimeSheet,personalInfo,holidayCalcMethodSet,coreTimeSetting,dailyUnit,breakTimeList, 
				vacationClass, timevacationUseTimeOfDaily,  
				siftCode, leaveEarly, leaveEarly, illegularAddSetting, 
				flexAddSetting, regularAddSetting, holidayAddtionSet, commonSetting,conditionItem,predetermineTimeSetByPersonInfo,
				beforeInfo,afterInfo,leaveLateSet,specificDateAttrSheets,workTimeDivision);
	}

	/**
	 * 時間帯作成(勤務回数分ループ) 就業時間内・外作成処理
	 * 
	 * @param workingSystem
	 *            労働制クラス
	 * @param predetermineTimeSet
	 *            所定時間設定クラス
	 * @param fixedWorkSetting
	 *            固定勤務設定クラス
	 * @param workTimeCommonSet
	 *            就業時間帯の共通設定クラス
	 * @param bonuspaySetting
	 *            加給設定クラス
	 * @param overTimeHourSetList
	 *            残業時間の時間帯設定クラス
	 * @param fixOff
	 *            固定勤務の休日出勤用勤務時間帯クラス
	 * @param overDayEndCalcSet
	 *            0時跨ぎ計算設定クラス
	 * @param overDayEndSet
	 *            就業時間帯の共通設定クラス
	 * @param holidayTimeWorkItem
	 *            休出時間帯
	 * @param beforeDay
	 *            勤務種類クラス
	 * @param toDay
	 *            勤務種類クラス
	 * @param afterDay
	 *            勤務種類クラス
	 * @param breakdownTimeDay
	 *            1日の時間内訳クラス
	 * @param dailyTime
	 *            法定労働時間
	 * @param calcSetinIntegre
	 *            残業時間の自動計算設定クラス
	 * @param statutorySet
	 *            法定内残業設定
	 * @param prioritySet
	 *            法定内優先設定
	 * @param deductionTimeSheet
	 *            控除時間帯
	 * @param workTimeDivision 
	 * @param ootsukaIWFlag 
	 * @param integrationOfDaily 
	 */
	public void theDayOfWorkTimesLoop(WorkingSystem workingSystem, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
			CommonFixedWorkTimezoneSet lstHalfDayWorkTimezone,
			WorkTimezoneCommonSet workTimeCommonSet, Optional<BonusPaySetting> bonuspaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, List<HDWorkTimeSheetSetting> fixOff, Optional<ZeroTime> overDayEndCalcSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet,
			DeductionTimeSheet deductionTimeSheet, WorkTimeSetting workTime,MidNightTimeSheet midNightTimeSheet,
			DailyCalculationPersonalInformation personalInfo,HolidayCalcMethodSet holidayCalcMethodSet,
			Optional<CoreTimeSetting> coreTimeSetting,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
    		VacationClass vacationClass, AttendanceTime timevacationUseTimeOfDaily, 
    		Optional<WorkTimeCode> siftCode, 
    		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
    		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet,Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
    		Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo,
    		Optional<DeductLeaveEarly> deductLeaveEarly,Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrSheets, WorkTimeDivision workTimeDivision) {
		if (workingSystem.isExcludedWorkingCalculate()) {
			/* 計算対象外 */
			return;
		}
		for (int workNumber = 1; workNumber <= attendanceLeavingWork.getTimeLeavingWorks().size(); workNumber++) {
			
			/* 就業内時間帯作成 */
			//打刻はある前提で動く
			val createWithinWorkTimeSheet = WithinWorkTimeSheet.createAsFixed(attendanceLeavingWork.getAttendanceLeavingWork(new WorkNo(workNumber)).get(),
																			  toDay,
																			  predetermineTimeSetForCalc, 
																			  lstHalfDayWorkTimezone,
																			  workTimeCommonSet,
																			  deductionTimeSheet,
																			  bonuspaySetting,
																			  midNightTimeSheet,
																			  workNumber,
																			  coreTimeSetting,
																			  holidayCalcMethodSet,
																			  workTimeCommonSet.getLateEarlySet(),
																			  dailyUnit,breakTimeList,
																			  vacationClass, 
																			  late, 
																				leaveEarly, 
																				workingSystem, 
																				illegularAddSetting, 
																				flexAddSetting, 
																				regularAddSetting, 
																				holidayAddtionSet, 
																				CalcMethodOfNoWorkingDay.isCalculateFlexTime, 
																				Optional.of(new SettingOfFlexWork(new FlexCalcMethodOfHalfWork(new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay),
																						                                                       new FlexCalcMethodOfEachPremiumHalfWork(FlexCalcMethod.OneDay, FlexCalcMethod.OneDay)))), 
																				Optional.of(workTime.getWorkTimeDivision().getWorkTimeDailyAtr()), 
																				siftCode, 
																				new AttendanceTime(1440), 
																				Finally.of(timevacationUseTimeOfDaily),
																				commonSetting,
																				conditionItem,
																				predetermineTimeSetByPersonInfo,
																				deductLeaveEarly,
																				specificDateAttrSheets);
			if(withinWorkingTimeSheet.isPresent()) {
				withinWorkingTimeSheet.get().getWithinWorkTimeFrame().addAll(createWithinWorkTimeSheet.getWithinWorkTimeFrame());
			}
			else {
				withinWorkingTimeSheet.set(createWithinWorkTimeSheet);
			}
			/* 就業外�?時間帯作�? */
			//打刻はある前提で動く
			val createOutSideWorkTimeSheet = OutsideWorkTimeSheet.createOutsideWorkTimeSheet(overTimeHourSetList, fixOff,
					attendanceLeavingWork.getAttendanceLeavingWork(new WorkNo(workNumber)).get(),
					workNumber, overDayEndCalcSet, workTimeCommonSet, holidayTimeWorkItem, beforeDay, toDay, afterDay, workTime,
					workingSystem, breakdownTimeDay, dailyTime, calcSetinIntegre.getOvertimeSetting(), statutorySet, prioritySet
					,bonuspaySetting,midNightTimeSheet,personalInfo,deductionTimeSheet,dailyUnit,holidayCalcMethodSet,createWithinWorkTimeSheet, 
					vacationClass, timevacationUseTimeOfDaily, predetermineTimeSetForCalc, 
					siftCode,  leaveEarly, leaveEarly, illegularAddSetting, flexAddSetting, regularAddSetting, holidayAddtionSet,commonSetting,
					conditionItem,predetermineTimeSetByPersonInfo,coreTimeSetting, beforeInfo, afterInfo, specificDateAttrSheets,workTimeDivision.getWorkTimeDailyAtr()
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
		if(!this.withinWorkingTimeSheet.isPresent()) {
			this.withinWorkingTimeSheet = Finally.of(new WithinWorkTimeSheet(Arrays.asList(new WithinWorkTimeFrame(new EmTimeFrameNo(5), 
																									 new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0)), 
																									 new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN), 
																									 Collections.emptyList(), 
																									 Collections.emptyList(), 
																									 Collections.emptyList(), 
																									 Optional.empty(), 
																									 Collections.emptyList(), 
																									 Optional.empty(), 
																									 Optional.empty())),
																			 Collections.emptyList(),
																			 Optional.empty(),
																			 Optional.empty()));
		}
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
							 leaveEarly,
							 workingSystem,
							 holidayAddtionSet,
							 regularAddSetting,
							 flexAddSetting,
							 illegularAddSetting,
							 leaveEarly,commonSetting,
							 conditionItem,
							 predetermineTimeSetByPersonInfo,coreTimeSetting,workTimeDivision);
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
	 * 大塚　固定勤務の流動残業対応(所定内割増を残業時間帯へ移す)
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
	 * @param workTimeDivision 
	 * @return
	 */
	private List<OverTimeFrameTimeSheetForCalc> forOOtsukaPartMethod(LegalOTSetting statutorySet, DailyTime dailyTime, List<OverTimeFrameTimeSheetForCalc> overTimeWorkFrameTimeSheetList, 
									  AutoCalOvertimeSetting autoCalculationSet, BreakDownTimeDay breakdownTimeDay, List<OverTimeOfTimeZoneSet> overTimeHourSetList, 
									  DailyUnit dailyUnit, HolidayCalcMethodSet holidayCalcMethodSet,
									  VacationClass vacationClass, AttendanceTime timevacationUseTimeOfDaily, WorkType workType, 
									  PredetermineTimeSetForCalc predetermineTimeSet, Optional<WorkTimeCode> siftCode, 
									  boolean late, WorkingSystem workingSystem, HolidayAddtionSet holidayAddtionSet, WorkRegularAdditionSet regularAddSetting, 
									  WorkFlexAdditionSet flexAddSetting, WorkDeformedLaborAdditionSet illegularAddSetting, boolean leaveEarly, Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
									  Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,Optional<CoreTimeSetting> coreTimeSetting, WorkTimeDivision workTimeDivision) {
		
		if(!this.withinWorkingTimeSheet.isPresent())
			return Collections.emptyList();
		List<WithinWorkTimeFrame> renewWithinFrame = new ArrayList<>();
		List<OverTimeFrameTimeSheetForCalc> returnList = new ArrayList<>();
		//所定内就業時間枠のループ
		for(WithinWorkTimeFrame timeSheet : this.withinWorkingTimeSheet.get().getWithinWorkTimeFrame()) {
			//割増時間帯が作成されているか確認
			if(timeSheet.getPremiumTimeSheetInPredetermined().isPresent()) {
				
					val newTimeSpanList = timeSheet.timeSheet.getNotDuplicationWith(timeSheet.getPremiumTimeSheetInPredetermined().get().getWithinPremiumtimeSheet());
					//就業時間枠時間帯と割増時間帯の重なっていない部分で、
					//就業時間枠時間帯を作り直す
					for(TimeSpanForDailyCalc newTimeSpan : newTimeSpanList) {
						renewWithinFrame.add(new WithinWorkTimeFrame(timeSheet.getWorkingHoursTimeNo(),
																	 newTimeSpan,
																	 timeSheet.getRounding(),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan),
																	 timeSheet.duplicateNewTimeSpan(newTimeSpan),
																	 timeSheet.getDuplicatedBonusPayNotStatic(timeSheet.getBonusPayTimeSheet(), newTimeSpan),//加給
																	 timeSheet.getMidNightTimeSheet().isPresent()
																	 	?timeSheet.getDuplicateMidNightNotStatic(timeSheet.getMidNightTimeSheet().get(),newTimeSpan)
																	 	:Optional.empty(),//深夜
																	 timeSheet.getDuplicatedSpecBonusPayzNotStatic(timeSheet.getSpecBonusPayTimesheet(), newTimeSpan),//特定日加給
																	 timeSheet.getLateTimeSheet(),
																	 timeSheet.getLeaveEarlyTimeSheet()
											 ));
						
					}
					
					returnList.add(new OverTimeFrameTimeSheetForCalc(new TimeSpanForDailyCalc(timeSheet.getPremiumTimeSheetInPredetermined().get().getWithinPremiumtimeSheet().getStart(),timeSheet.getPremiumTimeSheetInPredetermined().get().getWithinPremiumtimeSheet().getEnd()),
													  								 new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
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
		//所定内割増時間初期化
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
															late, 
															leaveEarly, 
															workingSystem, 
															illegularAddSetting, 
															flexAddSetting, 
															regularAddSetting, 
															holidayAddtionSet,commonSetting,conditionItem,
															predetermineTimeSetByPersonInfo,coreTimeSetting,workTimeDivision.getWorkTimeDailyAtr()
															);
	}

	/**
	 * 加給時間を計算する(就内・残業・休出時間帯の
	 */
	public List<BonusPayTime> calcBonusPayTime(AutoCalRaisingSalarySetting raisingAutoCalcSet,BonusPayAutoCalcSet bonusPayAutoCalcSet,
											   CalAttrOfDailyPerformance calcAtrOfDaily, BonusPayAtr bonusPayAtr) {
		
		List<BonusPayTime> overTimeBonusPay = new ArrayList<>();
		List<BonusPayTime> holidayWorkBonusPay = new ArrayList<>();
		List<BonusPayTime> withinBonusPay = new ArrayList<>();
		if(this.withinWorkingTimeSheet != null && withinWorkingTimeSheet.isPresent())
			withinBonusPay = withinWorkingTimeSheet.get().calcBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);
		
		if(this.outsideWorkTimeSheet != null && this.outsideWorkTimeSheet.isPresent())
		{
			if(this.outsideWorkTimeSheet.get().getOverTimeWorkSheet().isPresent()) { 
				overTimeBonusPay = outsideWorkTimeSheet.get().getOverTimeWorkSheet().get().calcBonusPayTimeInOverWorkTime(raisingAutoCalcSet, bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily);
			}
			
			if(this.outsideWorkTimeSheet.get().getHolidayWorkTimeSheet().isPresent()) {
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
			 withinBonusPay = withinWorkingTimeSheet.get().calcSpecifiedBonusPayTimeInWithinWorkTime(raisingAutoCalcSet,bonusPayAutoCalcSet, bonusPayAtr,calcAtrOfDaily);

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
	public TimeWithCalculation calcWithinTotalTime(ConditionAtr dedClassification, DeductionAtr dedAtr,StatutoryAtr statutoryAtr,TimeSheetRoundingAtr pertimesheet
			,PremiumAtr premiumAtr,HolidayCalcMethodSet holidayCalcMethodSet,Optional<WorkTimezoneCommonSet> commonSetting) {
		if(statutoryAtr.isStatutory()) {
			if(this.withinWorkingTimeSheet.isPresent()) {
				return TimeWithCalculation.sameTime(this.withinWorkingTimeSheet.get().calculationAllFrameDeductionTime(dedAtr, dedClassification,premiumAtr,holidayCalcMethodSet,commonSetting));
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
	 * @param ootsukaIWFlag 
	 * @param integrationOfDaily 
	 * @param integrationOfDaily 
	 */
	 public void createTimeSheetAsFlex(
			 		WorkingSystem workingSystem, PredetermineTimeSetForCalc predetermineTimeSetForCalc,
					Optional<BonusPaySetting> bonuspaySetting,
					List<HDWorkTimeSheetSetting> fixOff,List<OverTimeOfTimeZoneSet> overTimeHourSetList,List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem,  
					Optional<ZeroTime> overDayEndCalcSet,WorkType beforeDay, WorkType toDay, WorkType afterDay,
					BreakDownTimeDay breakdownTimeDay, DailyTime dailyTime, CalAttrOfDailyPerformance calcSetinIntegre,
					LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet,
					WorkTimeSetting workTime,
					FlexWorkSetting flexWorkSetting,Optional<OutingTimeOfDailyPerformance> goOutTimeSheetList,
					TimeSpanForDailyCalc oneDayTimeSpan,TimeLeavingOfDailyPerformance attendanceLeaveWork,WorkTimeDivision workTimeDivision
					,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,MidNightTimeSheet midNightTimeSheet,DailyCalculationPersonalInformation personalInfo,
					HolidayCalcMethodSet holidayCalcMethodSet,Optional<CoreTimeSetting> coreTimeSetting,DailyUnit dailyUnit,List<TimeSheetOfDeductionItem> breakTimeList,
            		VacationClass vacationClass, AttendanceTime timevacationUseTimeOfDaily,
            		Optional<WorkTimeCode> siftCode, 
            		boolean late, boolean leaveEarly, WorkDeformedLaborAdditionSet illegularAddSetting, WorkFlexAdditionSet flexAddSetting, 
            		WorkRegularAdditionSet regularAddSetting, HolidayAddtionSet holidayAddtionSet,Optional<WorkTimezoneCommonSet> commonSetting,WorkingConditionItem conditionItem,
            		Optional<PredetermineTimeSetForCalc> predetermineTimeSetByPersonInfo,List<ShortWorkingTimeSheet> shortTimeSheets,
            		WorkTimezoneShortTimeWorkSet workTimeShortTimeSet,Optional<WorkInformation> beforeInfo, Optional<WorkInformation> afterInfo,
            		List<EmTimeZoneSet> fixWoSetting,Optional<SpecificDateAttrOfDailyPerfor> specificDateAttrSheets){

		 //控除時間帯の作�?
		 val deductionTimeSheet = provisionalDeterminationOfDeductionTimeSheet(goOutTimeSheetList,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flexWorkSetting.getOffdayWorkTime().getRestTimezone(),flexWorkSetting.getRestSetting(), shortTimeSheets,
				 workTimeShortTimeSet,commonSetting,holidayCalcMethodSet,predetermineTimeSetForCalc,toDay,fixWoSetting);
		 /*固定勤務�?時間帯作�?*/
		 val fixedWorkTimeZoneSet = new CommonFixedWorkTimezoneSet();
		 fixedWorkTimeZoneSet.forFlex(flexWorkSetting.getLstHalfDayWorkTimezone());
		 Optional<DeductLeaveEarly> leaveLateSet = flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().isPresent()
				 								 ?Optional.of(flexAddSetting.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getAdvancedSet().get().getNotDeductLateLeaveEarly())
				 								 :Optional.empty();
		 theDayOfWorkTimesLoop( workingSystem,  predetermineTimeSetForCalc,
				 	fixedWorkTimeZoneSet,  flexWorkSetting.getCommonSetting(),  bonuspaySetting,
					overTimeHourSetList,  fixOff,  overDayEndCalcSet,
					holidayTimeWorkItem,  beforeDay,  toDay,  afterDay,
					 breakdownTimeDay,  dailyTime,  calcSetinIntegre,
					 statutorySet,  prioritySet,
					 deductionTimeSheet,  workTime,midNightTimeSheet,personalInfo,holidayCalcMethodSet,coreTimeSetting,dailyUnit,breakTimeList,
					 vacationClass, timevacationUseTimeOfDaily, siftCode, 
					  leaveEarly, leaveEarly, illegularAddSetting, flexAddSetting, regularAddSetting, holidayAddtionSet
					 ,commonSetting,conditionItem,predetermineTimeSetByPersonInfo,beforeInfo,afterInfo,leaveLateSet,specificDateAttrSheets,workTimeDivision);
		 /*コアタイ�?のセ�?��*/
		 //this.withinWorkingTimeSheet.set(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting()));
		 if(this.withinWorkingTimeSheet.isPresent())
			 this.withinWorkingTimeSheet = Finally.of(withinWorkingTimeSheet.get().createWithinFlexTimeSheet(flexWorkSetting.getCoreTimeSetting(),deductionTimeSheet));
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
	 public static DeductionTimeSheet provisionalDeterminationOfDeductionTimeSheet(Optional<OutingTimeOfDailyPerformance> goOutTimeSheetList,
				TimeSpanForDailyCalc oneDayTimeSpan,TimeLeavingOfDailyPerformance attendanceLeaveWork,WorkTimeDivision workTimeDivision
				,List<BreakTimeOfDailyPerformance> breakTimeOfDailyList,FlowWorkRestTimezone flowRestTimezone,FlowWorkRestSetting flowRestSetting,
				List<ShortWorkingTimeSheet> shortTimeSheets,WorkTimezoneShortTimeWorkSet workTimeShortTimeSet,Optional<WorkTimezoneCommonSet> commonSetting
				,HolidayCalcMethodSet holidayCalcMethodSet,PredetermineTimeSetForCalc predetermineTimeSetForCalc,WorkType worktype,List<EmTimeZoneSet> fixWoSetting) {
		 //控除用
		 val dedTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Deduction, goOutTimeSheetList,
				 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting, shortTimeSheets,
				 workTimeShortTimeSet,commonSetting,holidayCalcMethodSet,predetermineTimeSetForCalc,worktype,fixWoSetting);
	 	 //計上用
	 	 val recordTimeSheet = DeductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(DeductionAtr.Appropriate, goOutTimeSheetList,
	 			 oneDayTimeSpan, attendanceLeaveWork, workTimeDivision,breakTimeOfDailyList,flowRestTimezone,flowRestSetting, shortTimeSheets,
	 			 workTimeShortTimeSet,commonSetting,holidayCalcMethodSet,predetermineTimeSetForCalc,worktype,fixWoSetting);
	 
	 	return new DeductionTimeSheet(dedTimeSheet,recordTimeSheet,breakTimeOfDailyList,goOutTimeSheetList,shortTimeSheets); 
	 }
	 
	 /**
	  * 大塚モード使用時専用の��?��、早��?削除処��?
	  * 大塚モード使用時専用の�?��、早�?削除処�?
	  * 大塚モード使用時専用の遅刻、早退削除処理
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
	 
	 
	 /**
	 * 流動勤務の時間帯作成
	 * @param personalInfo 日別計算用の個人情報
	 * @param workTime 就業時間帯の設定
	 * @param yesterdayInfo 前日の勤務情報
	 * @param tommorowInfo 翌日の勤務情報
	 * @param todayWorkType 当日の勤務種類
	 * @param yesterdayWorkType 前日の勤務種類
	 * @param tommorowWorkType 翌日の勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param manageReGetClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 * @param holidayPriorityOrder 時間休暇相殺優先順位
	 * @param companyCommonSetting
	 * @param midNightTimeSheet 深夜時間帯
	 * @param addSetting 加算設定
	 * @param zeroTime 0時跨ぎ計算設定
	 * @param bonuspaySetting 加給設定
	 * @param vacation 休暇クラス
	 * @param illegularAddSetting 変形労働勤務の加算設定
	 * @param flexAddSetting フレックス勤務の加算設定
	 * @param regularAddSetting 通常勤務の加算設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param manageReGetClassRecord 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス
	 */
	public void createFlowWork(
			DailyCalculationPersonalInformation personalInfo,
			WorkTimeSetting workTime,
			Optional<WorkInformation> yesterdayInfo,
			Optional<WorkInformation> tommorowInfo,
			WorkType todayWorkType,
			WorkType yesterdayWorkType,
			WorkType tommorowWorkType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting,
			Optional<ManageReGetClass> manageReGetClass,
			CompanyHolidayPriorityOrder holidayPriorityOrder,
			ManagePerCompanySet companyCommonSetting,
			MidNightTimeSheet midNightTimeSheet,
			AddSetting addSetting,
			ZeroTime zeroTime,
			Optional<BonusPaySetting> bonuspaySetting,
			//共通処理呼ぶ用
			VacationClass vacation,
			WorkDeformedLaborAdditionSet illegularAddSetting,
			WorkFlexAdditionSet flexAddSetting,
			WorkRegularAdditionSet regularAddSetting,
			HolidayAddtionSet holidayAddtionSet) {

		//出退勤を取得
		List<TimeLeavingWork> timeLeavingWorks = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks();
		
		//空の就業時間内時間枠を作成。これを遅刻早退と就内の処理で編集していく
		WithinWorkTimeSheet creatingWithinWorkTimeSheet = new WithinWorkTimeSheet(new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty());
		
		//事前に遅刻早退、控除時間帯を取得する
		DeductionTimeSheet timeSheetOfDeductionItems = this.prePocessForFlow(//本来控除項目の時間帯を返すが、就内で呼ぶ共通処理で必要な為、控除時間帯を返している。
			timeLeavingWorks,
			todayWorkType,
			flowWorkSetting,
			holidayCalcMethodSet,
			workTime,
			integrationOfDaily,
			personCommonSetting,
			creatingWithinWorkTimeSheet);
		
		if(todayWorkType.isWeekDayAttendance()) {
			
			//所定時間設定をコピーして計算用の所定時間設定を作成する → 既に持っている
			
			//所定時間帯、残業開始を補正する
			this.changePredeterminedForFlow(
					manageReGetClass,
					integrationOfDaily,
					todayWorkType,
					personCommonSetting,
					flowWorkSetting);
			
			//流動勤務(就内、平日)
			creatingWithinWorkTimeSheet.createAsFlow(
					companyCommonSetting.getHolidayAdditionPerCompany(),
					integrationOfDaily,
					this.predetermineTimeSetForCalc,
					timeSheetOfDeductionItems.getForDeductionTimeZoneList(),
					todayWorkType,
					flowWorkSetting,
					holidayCalcMethodSet,
					holidayPriorityOrder,
					midNightTimeSheet,
					bonuspaySetting,
					//共通処理呼ぶ用
					personCommonSetting,
					vacation,
					illegularAddSetting,
					flexAddSetting,
					regularAddSetting,
					timeSheetOfDeductionItems);
			
			if(creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().isEmpty()) return;
			
			//流動勤務(平日・就外）
			this.outsideWorkTimeSheet.set(
					OutsideWorkTimeSheet.createOverTimeAsFlow(
							personalInfo,
							flowWorkSetting,
							predetermineTimeSetForCalc,
							timeSheetOfDeductionItems.getForDeductionTimeZoneList(),
							creatingWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().get(),
							bonuspaySetting,
							integrationOfDaily,
							midNightTimeSheet,
							addSetting,
							creatingWithinWorkTimeSheet.getTimeVacationAdditionRemainingTime().get(),
							zeroTime,
							todayWorkType,
							yesterdayWorkType,
							tommorowWorkType,
							//共通処理呼ぶ用
							yesterdayInfo,
							tommorowInfo,
							creatingWithinWorkTimeSheet,
							personCommonSetting,
							vacation,
							illegularAddSetting,
							flexAddSetting,
							regularAddSetting,
							holidayAddtionSet));
		} else {
			//流動勤務(休日出勤)
			 this.outsideWorkTimeSheet.set(
					OutsideWorkTimeSheet.createHolidayAsFlow(
							todayWorkType,
							flowWorkSetting,
							timeSheetOfDeductionItems.getForDeductionTimeZoneList(),
							creatingWithinWorkTimeSheet.getStartEndToWithinWorkTimeFrame().get(),
							bonuspaySetting,
							integrationOfDaily,
							midNightTimeSheet,
							zeroTime,
							yesterdayWorkType,
							tommorowWorkType,
							yesterdayInfo,
							tommorowInfo,
							oneDayOfRange));
		}
	}
	
	/**
	 * 事前処理
	 * @param timeLeavingWorks 出退勤（List)
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param workTime 就業時間帯の設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス
	 * @param creatingWithinWorkTimeSheet 遅刻早退を持たせる就業時間内時間帯
	 * @return 控除項目の時間帯
	 */
	public DeductionTimeSheet prePocessForFlow(
			List<TimeLeavingWork> timeLeavingWorks,
			WorkType workType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			WorkTimeSetting workTime,
			IntegrationOfDaily integrationOfDaily,
			ManagePerPersonDailySet personCommonSetting,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//1回目と2回目の間の時間帯を作成  遅刻丸めによる時刻補正を行うよりも前に時間帯を作成する必要がある為、ここで作成。
		Optional<TimeSheetOfDeductionItem> betweenWorkTimeSheets = createBetweenWork(timeLeavingWorks, flowWorkSetting);
		
		//間の休憩を非勤務時間帯へ
		if(betweenWorkTimeSheets.isPresent()) {
			if(betweenWorkTimeSheets.get().getWorkingBreakAtr().isWorking() && betweenWorkTimeSheets.get().getDeductionAtr().isBreak()) {
				List<TimeSpanForDailyCalc> whithinBreakTimeSheet = new ArrayList<TimeSpanForDailyCalc>();
				whithinBreakTimeSheet.add(betweenWorkTimeSheets.get().getTimeSheet());
				this.nonWorkingTimeSheet = Optional.of(new NonWorkingTimeSheet(whithinBreakTimeSheet, Collections.emptyList()));
			}
		};
		
		//控除時間帯の取得
		DeductionTimeSheet deductionTimeSheetCalcBefore = provisionalDeterminationOfDeductionTimeSheet(
				integrationOfDaily.getOutingTime(),
				this.oneDayOfRange,
				integrationOfDaily.getAttendanceLeave().get(),
				workTime.getWorkTimeDivision(),
				integrationOfDaily.getBreakTime(),
				flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone(),
				flowWorkSetting.getRestSetting(),
				integrationOfDaily.getShortTime().isPresent()?integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets():Collections.emptyList(),
				flowWorkSetting.getCommonSetting().getShortTimeWorkSet(),
				Optional.of(flowWorkSetting.getCommonSetting()),
				holidayCalcMethodSet,
				this.predetermineTimeSetForCalc,
				workType,
				Collections.emptyList());
		
		//遅刻時間帯の計算
		List<TimeLeavingWork> calcLateTimeLeavingWorksWorks = new ArrayList<>(timeLeavingWorks);
		timeLeavingWorks.clear();
		timeLeavingWorks.addAll(calcLateTimeLeavingWorksWorks.stream()
				.map(timeLeavingWork -> this.calcLateTimeSheet(
						timeLeavingWork,
						deductionTimeSheetCalcBefore.getForDeductionTimeZoneList(),
						workType,
						flowWorkSetting,
						holidayCalcMethodSet,
						integrationOfDaily,
						creatingWithinWorkTimeSheet))
				.collect(Collectors.toList()));
		
		//控除時間帯の取得
		DeductionTimeSheet deductionTimeSheetCalcAfter = provisionalDeterminationOfDeductionTimeSheet(
				integrationOfDaily.getOutingTime(),
				this.oneDayOfRange,
				new TimeLeavingOfDailyPerformance(
						personCommonSetting.getPersonInfo().get().getEmployeeId(), 
						new WorkTimes(timeLeavingWorks.size()), timeLeavingWorks, integrationOfDaily.getAttendanceLeave().get().getYmd()),
				workTime.getWorkTimeDivision(),
				integrationOfDaily.getBreakTime(),
				flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone(),
				flowWorkSetting.getRestSetting(),
				integrationOfDaily.getShortTime().isPresent()?integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets():new ArrayList<>(),
				flowWorkSetting.getCommonSetting().getShortTimeWorkSet(),
				Optional.of(flowWorkSetting.getCommonSetting()),
				holidayCalcMethodSet,
				this.predetermineTimeSetForCalc,
				workType,
				Collections.emptyList());
		
		if(betweenWorkTimeSheets.isPresent()) {
			deductionTimeSheetCalcAfter.getForDeductionTimeZoneList().add(betweenWorkTimeSheets.get());
		}
		
		//早退時間帯の計算
		List<TimeLeavingWork> calcLeaveEarlyTimeLeavingWorks = new ArrayList<>(timeLeavingWorks);
		timeLeavingWorks.clear();
		timeLeavingWorks.addAll(calcLeaveEarlyTimeLeavingWorks.stream()
				.map(timeLeavingWork -> this.calcLeaveEarlyTimeSheet(
						timeLeavingWork,
						deductionTimeSheetCalcAfter.getForDeductionTimeZoneList(),
						workType,
						flowWorkSetting,
						holidayCalcMethodSet,
						integrationOfDaily,
						creatingWithinWorkTimeSheet))
				.collect(Collectors.toList()));
		
		return deductionTimeSheetCalcAfter;
	}
	
	/**
	* 遅刻時間帯の計算
	 * @param timeLeavingWork 出退勤
	 * @param forDeductionTimeZones 控除項目の時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param integrationOfDaily 日別実績(Work)
	 * @param creatingWithinWorkTimeSheet 就業時間内時間帯
	 * @return 出退勤
	 */
	private TimeLeavingWork calcLateTimeSheet(
			TimeLeavingWork timeLeavingWork,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			WorkType workType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			IntegrationOfDaily integrationOfDaily,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//所定時間帯を取得する
		PredetermineTimeSetForCalc predetermineTimeSet = getPredetermineTimeSheetForFlow(timeLeavingWork.getWorkNo(), workType);
		
		//計算範囲を判断する
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().add(createWithinWorkTimeFrameIncludingCalculationRange(
				timeLeavingWork,
				predetermineTimeSet,
				workType,
				integrationOfDaily,
				flowWorkSetting));

		//遅刻時間帯を計算
		timeLeavingWork = creatingWithinWorkTimeSheet.calcLateTimeDeduction(
				timeLeavingWork,
				predetermineTimeSet,
				forDeductionTimeZones,
				workType,
				flowWorkSetting,
				holidayCalcMethodSet,
				workType.isWeekDayAttendance()?flowWorkSetting.getHalfDayWorkTimezone().getRestTimezone():flowWorkSetting.getOffdayWorkTimezone().getRestTimeZone());
				
		//時間帯.出勤←流動勤務用出退勤.出勤
		if(!timeLeavingWork.getAttendanceStamp().isPresent()) return timeLeavingWork;
		if(!timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) return timeLeavingWork;
		
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(timeLeavingWork.getWorkNo().v() - 1).changeStart(
				timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay());
		
		return timeLeavingWork;
	}
	
	/**
	 * 早退時間帯の計算
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param deductionTimeSheet 控除時間帯
	 * @param workType 勤務種類
	 * @param flowWorkSetting 流動勤務設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param flowRestTime 流動勤務の休憩時間帯
	 * @return  出退勤
	 */
	private TimeLeavingWork calcLeaveEarlyTimeSheet(
			TimeLeavingWork timeLeavingWork,
			List<TimeSheetOfDeductionItem> forDeductionTimeZones,
			WorkType workType,
			FlowWorkSetting flowWorkSetting,
			HolidayCalcMethodSet holidayCalcMethodSet,
			IntegrationOfDaily integrationOfDaily,
			WithinWorkTimeSheet creatingWithinWorkTimeSheet){
		
		//所定時間帯を取得する
		PredetermineTimeSetForCalc predetermineTimeSet = getPredetermineTimeSheetForFlow(timeLeavingWork.getWorkNo(), workType);
		
		//早退時間帯を計算
		timeLeavingWork = creatingWithinWorkTimeSheet.calcLeaveEarlyTimeDeduction(
				timeLeavingWork,
				predetermineTimeSet,
				forDeductionTimeZones,
				workType,
				flowWorkSetting,
				holidayCalcMethodSet);
		
		//時間帯.退勤←流動勤務用出退勤.退勤
		if(!timeLeavingWork.getLeaveStamp().isPresent()) return timeLeavingWork;
		if(!timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) return timeLeavingWork;
		
		creatingWithinWorkTimeSheet.getWithinWorkTimeFrame().get(timeLeavingWork.getWorkNo().v() - 1).changeEnd(
				timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay());
		
		return timeLeavingWork;
	}
	
	/**
	* 所定時間帯を取得する(流動)_自身の計算用所定時間設定を補正した新しいインスタンスを作成して返す
	* @param workNo 勤務NO
	* @param workType 勤務種類
	* @return PredetermineTimeSetForCalc 所定時間設定(計算用クラス)
	*/
	private PredetermineTimeSetForCalc getPredetermineTimeSheetForFlow(
			WorkNo workNo,
			WorkType workType){
		
		PredetermineTimeSetForCalc copiedPredetermineTimeSetForCalc = this.predetermineTimeSetForCalc.clone();
	
		//予定と実績が同じ勤務の場合
		if(this.workInformationOfDaily.isMatchWorkInfomation()){
			
			//予定勤務から参照
			Optional<ScheduleTimeSheet> scheduleTimeSheet = this.workInformationOfDaily.getScheduleTimeSheet(workNo);
			
			if(scheduleTimeSheet.isPresent()) {
				copiedPredetermineTimeSetForCalc.getTimeSheets().get(workNo.v()).updateStartTime(scheduleTimeSheet.get().getAttendance());
				copiedPredetermineTimeSetForCalc.getTimeSheets().get(workNo.v()).updateEndTime(scheduleTimeSheet.get().getLeaveWork());
			}
		}
		//午前勤務、午後勤務の場合に時間帯を補正する
		copiedPredetermineTimeSetForCalc.correctPredetermineTimeSheet(workType.getDailyWork(),workNo.v());
		return copiedPredetermineTimeSetForCalc;
	}
	
	/**
	 * 計算範囲を判断（流動）
	 * @param timeLeavingWork 出退勤
	 * @param predetermineTimeSet 所定時間設定(計算用クラス)
	 * @param workType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @return WithinWorkTimeFrame 就業時間内時間枠
	 */
	private WithinWorkTimeFrame createWithinWorkTimeFrameIncludingCalculationRange(
			TimeLeavingWork timeLeavingWork,
			PredetermineTimeSetForCalc predetermineTimeSet,
			WorkType workType,
			IntegrationOfDaily integrationOfDaily,
			FlowWorkSetting flowWorkSetting) {
		
		List<TimezoneUse> timezone = new ArrayList<TimezoneUse>();
		timezone.add(new TimezoneUse(timeLeavingWork.getTimeZone().getStart(), timeLeavingWork.getTimeZone().getEnd(),UseSetting.USE,timeLeavingWork.getWorkNo().v()));
		//時間帯を区切る為に所定時間を作成
		PredetermineTimeSetForCalc calcRenge = new PredetermineTimeSetForCalc(
				timezone,
				predetermineTimeSet.getAMEndTime(),
				predetermineTimeSet.getPMStartTime(),
				predetermineTimeSet.getAdditionSet(),
				predetermineTimeSet.getOneDayRange(),
				predetermineTimeSet.getStartOneDayTime());
		
		//午前勤務、午後勤務を見て時間帯を区切る
		Optional<TimezoneUse> correctTime = calcRenge.getTimeSheets(workType.getAttendanceHolidayAttr(),timeLeavingWork.getWorkNo().v());

		//予定開始時刻から計算する場合
		if(checkCalculateFromScheduleStartTime(correctTime.get().getStart(), integrationOfDaily, flowWorkSetting)) {
			correctTime.get().setStart(integrationOfDaily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).get().getAttendance());
		}
		
		//就業時間内時間枠作成
		WithinWorkTimeFrame frame = new WithinWorkTimeFrame(
				new EmTimeFrameNo(correctTime.get().getWorkNo()), 
				new TimeSpanForDailyCalc(
						correctTime.get().getStart(),
						correctTime.get().getEnd()), 
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				Collections.emptyList(),
				Optional.empty(),
				Optional.empty());

		return frame;
	}
	
	/**
	 * 予定開始時刻から計算するかチェック
	 * @param attendanceLeavingWork 出退勤
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @return true：計算する　false：計算しない
	 */
	private boolean checkCalculateFromScheduleStartTime(
			TimeWithDayAttr startTime,
			IntegrationOfDaily integrationOfDaily,
			FlowWorkSetting flowWorkSetting) {
		
		//出勤時刻から計算する
		if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet() == PrePlanWorkTimeCalcMethod.CALC_FROM_WORK_TIME) return false;
		
		//一致しない 
		if(!integrationOfDaily.getWorkInformation().isMatchWorkInfomation()) return false;
		
		//計算開始時刻>=予定勤務時間帯．出勤時刻
		if(startTime.greaterThanOrEqualTo(integrationOfDaily.getWorkInformation().getScheduleTimeSheet(new WorkNo(1)).get().getAttendance())) return false;
		
		//計算する
		return true;
	}
	
	/**
	 * 1回目と2回目の間の時間帯を作成
	 * @param timeLeavingWorks 出退勤
	 * @param workType 勤務種類
	 * @param integrationOfDaily 日別実績(Work)
	 * @param flowWorkSetting 流動勤務設定
	 * @return Optional<TimeSheetOfDeductionItem>  控除項目の時間帯
	 */
	private Optional<TimeSheetOfDeductionItem> createBetweenWork(
			List<TimeLeavingWork> timeLeavingWorks,
			FlowWorkSetting flowWorkSetting) {
		
		//1回目の退勤と2回目の出勤がない場合
		if(timeLeavingWorks.size() < 2) return Optional.empty();
		
		if(timeLeavingWorks.stream().filter(a -> a.getWorkNo().equals(new WorkNo(1))).findFirst().get().getLeaveStamp().isPresent()
				&& timeLeavingWorks.stream().filter(a -> a.getWorkNo().equals(new WorkNo(2))).findFirst().get().getAttendanceStamp().isPresent()) {
			return Optional.empty();
		}
		
		//時間帯を作成
		TimeSpanForDailyCalc betweenWorkTimeSheet = createBetweenWorkTimeSheet(timeLeavingWorks, flowWorkSetting);
		
		//控除時間帯を作成
		Optional<TimeSheetOfDeductionItem> deductionTimeBetweenWork = Optional.of(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
				betweenWorkTimeSheet, 
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Optional.empty(),
				WorkingBreakTimeAtr.WORKING,
				Finally.empty(),
				Finally.empty(),
				Optional.empty(),
				//休憩として扱う場合、就業時間から控除し休憩時間に計上する(控除種別.休憩)　休憩として扱わない場合、就業時間から控除するが休憩時間には計上しない(控除種別.計上なし)
				flowWorkSetting.getRestSetting().getFlowRestSetting().isUsePluralWorkRestTime() ? DeductionClassification.BREAK : DeductionClassification.NON_RECORD,
				Optional.empty()));
		
		return deductionTimeBetweenWork;
	}
	
	/**
	 * 時間帯を判断（流動_勤務間）
	 * @param timeLeavingWorks 出退勤
	 * @param flowWorkSetting 流動勤務設定
	 * @return betweenWorkTimeSheet 勤務間の時間帯
	 */
	private TimeSpanForDailyCalc createBetweenWorkTimeSheet(
			List<TimeLeavingWork> timeLeavingWorks,
			FlowWorkSetting flowWorkSetting) {
		
		TimeSpanForDailyCalc betweenWorkTimeSheet = new TimeSpanForDailyCalc(TimeWithDayAttr.THE_PRESENT_DAY_0000, TimeWithDayAttr.THE_PRESENT_DAY_0000);
		
		//1回目の勤務の退勤>=所定
		if(timeLeavingWorks.get(0).getTimespan().getEnd().greaterThanOrEqualTo(this.predetermineTimeSetForCalc.getTimeSheets().get(0).getEnd().v())) {
			betweenWorkTimeSheet.shiftOnlyStart(timeLeavingWorks.get(0).getTimespan().getEnd());
		}
		else {
			betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(0).getEnd());
		}
		
		//2回目の勤務の出勤>=所定
		if(timeLeavingWorks.get(1).getTimespan().getStart().greaterThanOrEqualTo(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart().v())) {
			 betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart());
		}
		else {
			//予定開始時刻から計算する
			if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet().equals(PrePlanWorkTimeCalcMethod.CALC_FROM_PLAN_START_TIME)) {
				betweenWorkTimeSheet.shiftOnlyStart(timeLeavingWorks.get(1).getTimespan().getStart());
			}
			//出勤時刻から計算する
			if(flowWorkSetting.getFlowSetting().getCalculateSetting().getCalcStartTimeSet().equals(PrePlanWorkTimeCalcMethod.CALC_FROM_WORK_TIME)) {
				betweenWorkTimeSheet.shiftOnlyStart(this.predetermineTimeSetForCalc.getTimeSheets().get(1).getStart());
			}
		}
		return betweenWorkTimeSheet;
	}

	/**
	 * 流動勤務所定変動
	 * @param manageReGetClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス(予定）
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param workType 勤務種類
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス（予定）
	 * @param flowWorkSetting 流動勤務設定
	 */
	private void changePredeterminedForFlow(
			Optional<ManageReGetClass> manageReGetClass,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			ManagePerPersonDailySet personCommonSetting,
			FlowWorkSetting flowWorkSetting) {
		
		if(!checkChangePredeterminedForFlow(manageReGetClass, integrationOfDaily, workType, flowWorkSetting.getFlowSetting().getOvertimeSetting())) {
			return;
		}
		
		//変動させる時間を求める
		AttendanceTimeOfExistMinus changeTime = getChangePredeterminedForFlow(
				manageReGetClass.get(),
				integrationOfDaily,
				workType,
				personCommonSetting,
				flowWorkSetting.getFlowSetting().getOvertimeSetting());
		
		if(!changeTime.equals(AttendanceTimeOfExistMinus.ZERO)) {
			//所定時間を変動させる
			this.predetermineTimeSetForCalc.changePredeterminedTimeSheetToSchedule(manageReGetClass.get().getCalculationRangeOfOneDay());
			
			//所定時間を変動させる
			this.predetermineTimeSetForCalc.getAdditionSet().changePredeterminedTimeForFlow(changeTime);
			
			//残業時間帯を変動させる
			flowWorkSetting.getHalfDayWorkTimezone().getWorkTimeZone().changeElapsedTimeInLstOTTimezone(changeTime);
		}
	}
	
	/**
	* 変動させる時間を求める
	 * @param manageReGetClass 時間帯作成、時間計算で再取得が必要になっているクラスたちの管理クラス(予定）
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param workType 勤務種類
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス（予定）
	 * @param FlowOTSet 流動残業設定
	 * @return changeTime 変動させる時間
	 */
	private AttendanceTimeOfExistMinus getChangePredeterminedForFlow(
			ManageReGetClass manageReGetClass,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			ManagePerPersonDailySet personCommonSetting,
			FlowOTSet flowOTSet) {
				
		//予定の所定時間を計算する
		AttendanceTime schedulePredetermineTime = WorkScheduleTimeOfDaily.calcPredeterminedFromTime(manageReGetClass, workType, personCommonSetting, flowOTSet);
		
		//実績の所定時間を計算する
		AttendanceTime recordPredetermineTime = this.predetermineTimeSetForCalc.getpredetermineTime(workType.getDailyWork());
		
		//予定の所定時間 - 実績の所定時間
		AttendanceTimeOfExistMinus changeTime = new AttendanceTimeOfExistMinus(schedulePredetermineTime.v()).minusMinutes(recordPredetermineTime.v());
		
		//所定変動区分が「後にズラす」
		if(flowOTSet.getFixedChangeAtr() == FixedChangeAtr.AFTER_SHIFT && changeTime.isNegative()) {
			changeTime = AttendanceTimeOfExistMinus.ZERO;
		}
		return changeTime;
	}
	
	/**
	 * 変動させるかチェックする
	 * @param integrationOfDaily 日別実績(Work)（実績）
	 * @param workType 勤務種類
	 * @param personCommonSetting 毎日変更の可能性のあるマスタ管理クラス（予定）
	 * @return true:変動させる false:変動させない
	 */
	private boolean checkChangePredeterminedForFlow(
			Optional<ManageReGetClass> manageReGetClass,
			IntegrationOfDaily integrationOfDaily,
			WorkType workType,
			FlowOTSet flowOTSet) {
		
		if(!manageReGetClass.isPresent() //予定を渡している場合（予定の計算時には変動させない為）
			|| !integrationOfDaily.getWorkInformation().isMatchWorkInfomation() //勤務実績と勤務予定の勤務情報が一致しない
			|| workType.chechAttendanceDay().equals(AttendanceDayAttr.HOLIDAY_WORK) //勤務実績の勤務種類が休出
			|| flowOTSet.getFixedChangeAtr().equals(FixedChangeAtr.NOT_CHANGE)) { //所定変動区分が「変動しない」
			return false;
		}
		return true;
	}
}
