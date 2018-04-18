package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ConditionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionOffSetTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateLeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LateTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.PremiumAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfFlexWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfIrregularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.AddSettingOfRegularWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.VacationAddTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 就業時間内時間枠
 * @author keisuke_hoshina
 *
 */
public class WithinWorkTimeFrame extends CalculationTimeSheet{// implements LateLeaveEarlyManagementTimeFrame {

	@Getter
	private final EmTimeFrameNo workingHoursTimeNo;
	
	private final Optional<TimeSpanForCalc> premiumTimeSheetInPredetermined;
	
	@Getter
	//遅刻時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Optional<LateTimeSheet> lateTimeSheet;
	@Getter
	//早退時間帯・・・deductByLateLeaveEarlyを呼ぶまでは値が無い
	private Optional<LeaveEarlyTimeSheet> leaveEarlyTimeSheet;
	
	public TimeSpanForCalc getPremiumTimeSheetInPredetermined() {
		return this.premiumTimeSheetInPredetermined.get();
	}
	
	/**
	 * constructor
	 * @param workingHoursTimeNo
	 * @param timeSheet
	 * @param calculationTimeSheet
	 */
	public WithinWorkTimeFrame(
			EmTimeFrameNo workingHoursTimeNo,
			TimeZoneRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			Optional<MidNightTimeSheetForCalc> midNighttimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			Optional<LateTimeSheet> lateTimeSheet,
			Optional<LeaveEarlyTimeSheet> leaveEarlyTimeSheet) {
		
		super(timeSheet, calculationTimeSheet,recorddeductionTimeSheets,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
		this.workingHoursTimeNo = workingHoursTimeNo;
		this.premiumTimeSheetInPredetermined = Optional.empty();
		this.lateTimeSheet = lateTimeSheet;
		this.leaveEarlyTimeSheet = leaveEarlyTimeSheet;
	}
	

	
	public TimeZoneRounding getTimeSheet() {
		return this.timeSheet;
	}
	
	
	
	/**
	 * 遅刻時間帯の作成
	 * @param workTime
	 * @param goWorkTime
	 * @param workNo
	 * @param workTimeCommonSet
	 * @param withinWorkTimeSheet
	 * @param deductionTimeSheet
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	public static LateTimeSheet createLateTimeSheet(
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday
			,TimeWithDayAttr attendance
			,OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet
			,LateDecisionClock lateDesClock
			,EmTimeZoneSet duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet
			,int workNo
			,TimezoneUse predetermineTimeSet
			,Optional<CoreTimeSetting> coreTimeSetting) {

		
		//遅刻時間帯の作成
		LateTimeSheet latetimesheet = LateTimeSheet.createLateTimeSheet(
				lateDesClock,
				attendance,
				otherEmTimezoneLateEarlySet.getGraceTimeSet(),
				duplicateTimeSheet,
				deductionTimeSheet,
				coreTimeSetting,
				predetermineTimeSet,
				workNo);
	
		return latetimesheet;
		
	}
	
	/**
	 * 早退時間帯の作成
	 * @param workTime
	 * @param goWorkTime
	 * @param workNo
	 * @param workTimeCommonSet
	 * @param withinWorkTimeSheet
	 * @param deductionTimeSheet
	 * @param graceTimeSetting
	 * @param workTimeCalcMethodDetailOfHoliday
	 */
	public static LeaveEarlyTimeSheet createLeaveEarlyTimeSheet(
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday
			,TimeWithDayAttr leave
			,OtherEmTimezoneLateEarlySet otherEmTimezoneLateEarlySet
			,LeaveEarlyDecisionClock leaveEarlyDesClock
			,EmTimeZoneSet duplicateTimeSheet
			,DeductionTimeSheet deductionTimeSheet
			,int workNo
			,TimezoneUse predetermineTimeSet
			,Optional<CoreTimeSetting> coreTimeSetting) {

		
		//早退時間帯の作成
		LeaveEarlyTimeSheet leaveEarlytimesheet = LeaveEarlyTimeSheet.createLeaveEarlyTimeSheet(
				leaveEarlyDesClock,
				leave,
				otherEmTimezoneLateEarlySet.getGraceTimeSet(),
				duplicateTimeSheet,
				deductionTimeSheet,
				coreTimeSetting,
				predetermineTimeSet,
				workNo);
	
		return leaveEarlytimesheet;
		
	}


//
//	@Override
//	public LateTimeSheet getLateTimeSheet() {
//		return this.lateTimeSheet.get();
//	}
//
//	@Override
//	public LeaveEarlyTimeSheet getLeaveEarlyTimeSheet() {
//		return this.leaveEarlyTimeSheet.get();
//	}
	
	/**
	 * 実働時間を求め、就業時間を計算する
	 * @param holidayAdditionAtr
	 * @param dedTimeSheet
	 * @return
	 */
	public AttendanceTime calcActualWorkTimeAndWorkTime(HolidayAdditionAtr holidayAdditionAtr,
														TimevacationUseTimeOfDaily timevacationUseTimeOfDaily,
														WorkingSystem workingSystem,
														AddSettingOfRegularWork addSettingOfRegularWork,
														AddSettingOfIrregularWork addSettingOfIrregularWork, 
														AddSettingOfFlexWork addSettingOfFlexWork,
//														LateTimeSheet lateTimeSheet,
//														LeaveEarlyTimeSheet leaveEarlyTimeSheet,
//														LateTimeOfDaily lateTimeOfDaily,
//														LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
														VacationAddTimeSet vacationAddTimeSet,
														boolean late,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
														boolean leaveEarly,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
														HolidayCalcMethodSet holidayCalcMethodSet,
														PremiumAtr premiumAtr
														) {
		AttendanceTime actualTime = calcActualTime();
		AttendanceTime dedAllTime = new AttendanceTime(0);
		val dedTimeSheets = this.deductionTimeSheet;
		if(!dedTimeSheets.isEmpty()) {
			dedAllTime = new AttendanceTime(dedTimeSheets.stream()
									  					 .map(tc -> tc.calcTotalTime().valueAsMinutes())
									  					 .collect(Collectors.summingInt(tc -> tc)));
		}
		if(dedAllTime.greaterThan(0)) {
			actualTime = actualTime.minusMinutes(dedAllTime.valueAsMinutes());
		}
		AttendanceTime workTime = calcWorkTime(actualTime);
		/*就業時間算出ロジックをここに*/
		
		//控除時間の内、時間休暇で相殺した時間を計算
		DeductionOffSetTime timeVacationOffSetTime = //(dedTimeSheet.isPresent())
													 // ?dedTimeSheet.get().calcTotalDeductionOffSetTime(lateTimeOfDaily,lateTimeSheet,leaveEarlyTimeOfDaily,leaveEarlyTimeSheet)
													  //控除時間帯が存在する前提で動いていたため、控除時間帯が無かったらオールゼロで修正
												     // :new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
													 new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));

		//遅刻、早退時間を就業時間から控除
		//就業時間帯の設定を取得
		NotUseAtr deductLateLeaveEarly = premiumAtr.isRegularWork()?holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly()
																   :holidayCalcMethodSet.getPremiumCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly();
//		if(jugmentDeductLateEarly(premiumAtr,deductLateLeaveEarly)) {
//			//遅刻控除時間を計算
//			int lateDeductTime = this.lateTimeSheet.get().calcDedctionTime(late, holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly()).getTime().valueAsMinutes();
//			//早退控除時間を計算
//			int leaveEarlyDeductTime = this.leaveEarlyTimeSheet.get().calcDedctionTime(leaveEarly,holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday().getDetailSet().getDeductLateLeaveEarly()).getTime().valueAsMinutes();
//			int lateLeaveEarlySubtraction = lateDeductTime + leaveEarlyDeductTime;
//			workTime = new AttendanceTime(workTime.valueAsMinutes() - lateLeaveEarlySubtraction);
//		}
		
		//時間休暇使用の残時間を計算 
		//timevacationUseTimeOfDaily.subtractionDeductionOffSetTime(timeVacationOffSetTime);
		//就業時間に加算する時間休暇を就業時間へ加算     
		workTime = new AttendanceTime(workTime.valueAsMinutes() + calcTimeVacationAddTime(vacationAddTimeSet,
																						  getCalculationByActualTimeAtr(workingSystem,
																													  	addSettingOfRegularWork,
																													  	addSettingOfIrregularWork,
																													  	addSettingOfFlexWork),
																  						  timeVacationOffSetTime).valueAsMinutes());
		return workTime;
	}
	
	/**
	 * 就業時間を計算する
	 * @param actualTime
	 * @return　就業時間
	 */
	public AttendanceTime calcWorkTime(AttendanceTime actualTime) {
		return actualTime;
	}
	
	/**
	 * 実働時間を計算する
	 * @return　実働時間
	 */
	public AttendanceTime calcActualTime() {
		return new AttendanceTime(((CalculationTimeSheet)this).getTimeSheet().timeSpan().lengthAsMinutes());
	}

	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊↓高須
	
//	/**
//	 * 計算範囲を判断（流動）　　流動時の就業時間内時間枠
//	 */
//	public void createWithinWorkTimeFrameForFluid(
//			AttendanceLeavingWork attendanceLeavingWork,
//			DailyWork dailyWork,
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
//		TimeSpanForCalc timeSheet = new TimeSpanForCalc(
//				attendanceLeavingWork.getAttendance().getEngrave().getTimesOfDay(),
//				attendanceLeavingWork.getLeaveWork().getEngrave().getTimesOfDay());
//		this.correctTimeSheet(dailyWork, timeSheet, predetermineTimeSetForCalc);
//	}
//	
//	/**
//	 * 勤務の単位を基に時間帯の開始、終了を補正
//	 * @param dailyWork 1日の勤務
//	 */
//	public WithinWorkTimeFrame correctTimeSheet(
//			DailyWork dailyWork,
//			TimeSpanForCalc timeSheet,
//			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
//		
//		//丸め設定を作成
//		Finally<TimeRoundingSetting> rounding = Finally.of(new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN,Rounding.ROUNDING_DOWN));
//		
//		if (dailyWork.getAttendanceHolidayAttr().isHalfDayWorking()) {
//			TimeSpanForCalc timeSheetForRounding = this.getHalfDayWorkingTimeSheetOf(dailyWork.getAttendanceHolidayAttr(),timeSheet,predetermineTimeSetForCalc);
//			TimeSpanWithRounding calcRange = new TimeSpanWithRounding(timeSheetForRounding.getStart(),timeSheetForRounding.getEnd(),rounding);
//			CalculationTimeSheet t = new CalculationTimeSheet(calcRange,timeSheetForRounding,Optional.empty());
//			return new WithinWorkTimeFrame(workingHoursTimeNo,timeSheet,t);
//		}
//	}

	/**
	 * 午前出勤、午後出勤の判定
	 * @param attr
	 * @return
	 */
	private TimeSpanForCalc getHalfDayWorkingTimeSheetOf(
			AttendanceHolidayAttr attr,
			TimeSpanForCalc timeSheet,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		switch (attr) {
		case MORNING:
			return new TimeSpanForCalc(timeSheet.getStart(), predetermineTimeSetForCalc.getAMEndTime());
		case AFTERNOON:
			return new TimeSpanForCalc(predetermineTimeSetForCalc.getPMStartTime(),timeSheet.getEnd());
		default:
			throw new RuntimeException("半日専用のメソッドです: " + attr);
		}
	}
	
	/**
	 *　指定条件の控除項目だけの控除時間
	 * @param forcsList
	 * @param atr
	 * @return
	 */
	public AttendanceTime forcs(ConditionAtr atr,DeductionAtr dedAtr){
		AttendanceTime dedTotalTime = new AttendanceTime(0);
		val loopList = (dedAtr.isAppropriate())?this.getRecordedTimeSheet():this.deductionTimeSheet;
		for(TimeSheetOfDeductionItem deduTimeSheet: loopList) {
			if(deduTimeSheet.checkIncludeCalculation(atr)) {
				val addTime = deduTimeSheet.calcTotalTime().valueAsMinutes();
				dedTotalTime = dedTotalTime.addMinutes(addTime);
			}
		}
		return dedTotalTime;
	}
	
//	
//	/**
//	 * 控除時間中の時間休暇相殺時間の計算
//	 * @return
//	 */
//	public DeductionOffSetTime calcTotalDeductionOffSetTime() {
//		
//		//2017/10/13はここからスタート
//		
//		
//	}
	
	/**
	 * 時間休暇からの加算時間を取得
	 * @author ken_takasu
	 * 
	 * @return
	 */
	public AttendanceTime calcTimeVacationAddTime(VacationAddTimeSet vacationAddTimeSet,
												  CalculationByActualTimeAtr calculationByActualTimeAtr,
												  DeductionOffSetTime timeVacationOffSetTime
												  ) {
		AttendanceTime addTime = new AttendanceTime(0);
		//実働のみで計算するかチェックする
		if(calculationByActualTimeAtr.isCalclationByActualTime()) {
			//年休分を加算するかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getAnnualLeave().isUse()) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getAnnualLeave().valueAsMinutes());
			}
			//積立年休分を含めて求めるかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getＲetentionYearly().isUse()) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getRetentionYearly().valueAsMinutes());
			}
			//特別休暇分を含めて求めるかチェックする
			if(vacationAddTimeSet.getAddVacationSet().getSpecialHoliday().isUse()) {
				addTime = new AttendanceTime(addTime.valueAsMinutes() + timeVacationOffSetTime.getSpecialHoliday().valueAsMinutes());
			}
		}
		return addTime;
	}
	
	/**
	 * 時間休暇相殺時間を修行時間に含めるか判断する
	 * @author ken_takasu
	 * @param workingSystem
	 * @param addSettingOfRegularWork
	 * @param addSettingOfIrregularWork
	 * @param addSettingOfFlexWork
	 * @return
	 */
	public CalculationByActualTimeAtr getCalculationByActualTimeAtr(WorkingSystem workingSystem,
																	AddSettingOfRegularWork addSettingOfRegularWork,
																	AddSettingOfIrregularWork addSettingOfIrregularWork, 
																	AddSettingOfFlexWork addSettingOfFlexWork) {
		switch (workingSystem) {
		case REGULAR_WORK:
			return addSettingOfRegularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime();

		case FLEX_TIME_WORK:
			return addSettingOfFlexWork.getHolidayCalcMethodSet().getPremiumCalcMethodOfHoliday().getCalculationByActualTime();

		case VARIABLE_WORKING_TIME_WORK:
			return addSettingOfIrregularWork.getHolidayCalcMethodSet().getWorkTimeCalcMethodOfHoliday().getCalculationByActualTime();

		case EXCLUDED_WORKING_CALCULATE:
			return CalculationByActualTimeAtr.CalculationByActualTime;
		default:
			throw new RuntimeException("不正な労働制です");
		}
	}

	/**
	 * 就内時間枠を作る
	 * @param duplicateTimeSheet
	 * @param deductionTimeSheet
	 * @param bonusPaySetting
	 * @param midNightTimeSheet
	 * @return
	 */
	public static WithinWorkTimeFrame createWithinWorkTimeFrame(EmTimeZoneSet duplicateTimeSheet,
																DeductionTimeSheet deductionTimeSheet,
																BonusPaySetting bonusPaySetting,
																MidNightTimeSheet midNightTimeSheet,
																LateDecisionClock lateDesClock,
																LeaveEarlyDecisionClock leaveEarlyDecisionClock,
																TimeLeavingWork timeLeavingWork,
																WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday,
																int workNo,
																WorkTimezoneLateEarlySet workTimezoneLateEarlySet,
																TimezoneUse predetermineTimeSet,
																Optional<CoreTimeSetting> coreTimeSetting
																) {
		
		
//		//遅刻時間帯の作成
//		LateTimeSheet lateTimeSheet = createLateTimeSheet(workTimeCalcMethodDetailOfHoliday,
//												   		  timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay()==null?new TimeWithDayAttr(0):
//												   			 timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay(),
//												   		  workTimezoneLateEarlySet.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE),
//												   		  lateDesClock,
//												   		  duplicateTimeSheet,
//												   		  deductionTimeSheet,
//												   		  workNo,
//												   		  predetermineTimeSet,
//												   		  coreTimeSetting);
//		//遅刻時間を計算する
//		AttendanceTime lateDeductTime = lateTimeSheet.getForDeducationTimeSheet().isPresent()?lateTimeSheet.getForDeducationTimeSheet().get().calcTotalTime():new AttendanceTime(0);		
//		//就業時間内時間帯から控除するか判断し控除する				
//		if(workTimeCalcMethodDetailOfHoliday.decisionLateDeductSetting(lateDeductTime, workTimezoneLateEarlySet.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.LATE).getGraceTimeSet())) {
//			//遅刻時間帯の終了時刻を開始時刻にする
//			duplicateTimeSheet = new EmTimeZoneSet(new EmTimeFrameNo(workNo), 
//												   new TimeZoneRounding(duplicateTimeSheet.getTimezone().getStart().forwardByMinutes(lateDeductTime.minute()),duplicateTimeSheet.getTimezone().getEnd(),duplicateTimeSheet.getTimezone().getRounding()));
//		}
//		
//		//早退時間帯の作成
//		LeaveEarlyTimeSheet LeaveEarlyTimeSheet = createLeaveEarlyTimeSheet(workTimeCalcMethodDetailOfHoliday,
//														  					timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay()==null?new TimeWithDayAttr(0):
//																	   			 timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay(),
//														  					workTimezoneLateEarlySet.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY),
//														  					leaveEarlyDecisionClock,
//														  					duplicateTimeSheet,
//														  					deductionTimeSheet,
//														  					workNo,
//														  					predetermineTimeSet,
//														  					coreTimeSetting);		
//		//早退時間を計算する
//		AttendanceTime LeaveEarlyDeductTime = LeaveEarlyTimeSheet.getForDeducationTimeSheet().isPresent()?LeaveEarlyTimeSheet.getForDeducationTimeSheet().get().calcTotalTime():new AttendanceTime(0);
//		//就業時間内時間帯から控除するか判断し控除する
//		if(workTimeCalcMethodDetailOfHoliday.decisionLateDeductSetting(LeaveEarlyDeductTime, workTimezoneLateEarlySet.getOtherEmTimezoneLateEarlySet(LateEarlyAtr.EARLY).getGraceTimeSet())) {
//			//早退時間帯の開始時刻を終了時刻にする
//			duplicateTimeSheet = new EmTimeZoneSet(new EmTimeFrameNo(workNo), 
//												   new TimeZoneRounding(duplicateTimeSheet.getTimezone().getStart(),duplicateTimeSheet.getTimezone().getEnd().backByMinutes(LeaveEarlyDeductTime.minute()),duplicateTimeSheet.getTimezone().getRounding()));
//		}
			
		//控除時間帯
		List<TimeSheetOfDeductionItem> dedTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(duplicateTimeSheet.getTimezone().timeSpan(), DeductionAtr.Deduction);
		List<TimeSheetOfDeductionItem> recordTimeSheet = deductionTimeSheet.getDupliRangeTimeSheet(duplicateTimeSheet.getTimezone().timeSpan(), DeductionAtr.Appropriate);
		
		/*加給*/
		List<BonusPayTimeSheetForCalc> bonusPayTimeSheet = getBonusPayTimeSheetIncludeDedTimeSheet(bonusPaySetting, duplicateTimeSheet.getTimezone().getTimeSpan(), recordTimeSheet, recordTimeSheet);
		/*特定日*/
		List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet = getSpecBonusPayTimeSheetIncludeDedTimeSheet(bonusPaySetting, duplicateTimeSheet.getTimezone().getTimeSpan(), recordTimeSheet, recordTimeSheet);

		/*深夜*/
		Optional<MidNightTimeSheetForCalc> duplicatemidNightTimeSheet = getMidNightTimeSheetIncludeDedTimeSheet(midNightTimeSheet, duplicateTimeSheet.getTimezone().getTimeSpan(), recordTimeSheet, recordTimeSheet);
		return new WithinWorkTimeFrame(duplicateTimeSheet.getEmploymentTimeFrameNo(),
									   duplicateTimeSheet.getTimezone(),
									   duplicateTimeSheet.getTimezone().timeSpan(),
									   recordTimeSheet,dedTimeSheet,bonusPayTimeSheet,duplicatemidNightTimeSheet,specifiedBonusPayTimeSheet,
									   Optional.empty(),
									   Optional.empty());
	}
	
	/**
	 * 遅刻早退を控除するかどうか判断
	 * @return
	 */
	public boolean jugmentDeductLateEarly(PremiumAtr premiumAtr,NotUseAtr deductLateLeaveEarly) {
		boolean result = false;
		if(premiumAtr.isRegularWork()) {
			return true;
		}else if(premiumAtr.isPremium()&&deductLateLeaveEarly.isDonot()){
			return true;
		}
		return result;
	}

}








