package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.bonuspay.autocalc.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.overtime.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.DailyCalculationPersonalInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 1日の計算範囲
 * 
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalculationRangeOfOneDay {

	private Finally<WithinWorkTimeSheet> withinWorkingTimeSheet = Finally.empty();

	private Finally<OutsideWorkTimeSheet> outsideWorkTimeSheet = Finally.empty();

	private TimeSpanForCalc oneDayOfRange;

	private WorkInfoOfDailyPerformance workInformationOfDaily;

	private TimeLeavingOfDailyPerformance attendanceLeavingWork;

	private PredetermineTimeSetForCalc predetermineTimeSetForCalc;

	private Finally<TimevacationUseTimeOfDaily> timeVacationAdditionRemainingTime = Finally.empty();// 時間休暇加算残時間

	private Optional<DeductionTimeSheet> temporaryDeductionTimeSheet = Optional.empty(); // 休憩時間の算出ロジック(概要設計)が終わるまで一時的措置のため設置
																							// 2017.11.15
																							// by
																							// hoshina

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
	 * @param bonusPaySetting
	 * @param overTimeHourSetList
	 * @param fixOff
	 * @param dayEndSet
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
	 * @param autoCalculationSet
	 * @param statutorySet
	 * @param prioritySet
	 */
	public void createWithinWorkTimeSheet(WorkingSystem workingSystem, WorkTimeMethodSet setMethod,
			RestClockManageAtr clockManage, OutingTimeOfDailyPerformance dailyGoOutSheet, CommonRestSetting commonSet,
			FixedRestCalculateMethod fixedCalc, WorkTimeDivision workTimeDivision, FlowRestCalcMethod fluidSet,
			BreakTimeManagement breakmanage, Optional<FlowWorkRestTimezone> fluRestTime,
			FlowFixedRestSet fluidPrefixBreakTimeSet, PredetemineTimeSetting predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting, WorkTimezoneCommonSet workTimeCommonSet, BonusPaySetting bonusPaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, FixOffdayWorkTimezone fixOff, OverDayEndCalcSet dayEndSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakdownTimeDay breakdownTimeDay, DailyTime dailyTime, AutoCalculationOfOverTimeWork autoCalculationSet,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet, WorkTimeSetting workTime) {
		/* 固定控除時間帯の作成 */
		//DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createDedctionTimeSheet(AcquisitionConditionsAtr.All,
		//		setMethod, clockManage, dailyGoOutSheet, this.oneDayOfRange, commonSet, attendanceLeavingWork,
		//		fixedCalc, workTimeDivision, fluidPrefixBreakTimeSet, fluidSet, breakmanage, setMethod, fluRestTime);
		//this.temporaryDeductionTimeSheet = Optional.of(deductionTimeSheet);
		
		DeductionTimeSheet deductionTimeSheet = new DeductionTimeSheet(Collections.emptyList(), Collections.emptyList(),new BreakTimeManagement(new BreakTimeOfDaily(DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																																															TimeWithCalculation.sameTime(new AttendanceTime(0)),
																																															TimeWithCalculation.sameTime(new AttendanceTime(0))),
																																									 DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(0)),
																																											 				TimeWithCalculation.sameTime(new AttendanceTime(0)),
																																											 				TimeWithCalculation.sameTime(new AttendanceTime(0))),
																																									 new BreakTimeGoOutTimes(0),
																																									 new AttendanceTime(0),
																																									 Collections.emptyList()
																																									 ),
																																				Collections.emptyList()));
		this.temporaryDeductionTimeSheet = Optional.empty();
		
		theDayOfWorkTimesLoop(workingSystem, predetermineTimeSet, fixedWorkSetting, workTimeCommonSet, bonusPaySetting,
				overTimeHourSetList, fixOff, dayEndSet, holidayTimeWorkItem, beforeDay, toDay, afterDay,
				breakdownTimeDay, dailyTime, autoCalculationSet, statutorySet, prioritySet, deductionTimeSheet,
				workTime);
	}

	/**
	 * 時間帯作成(勤務回数分のループ) 就業時間内・外の処理
	 * 
	 * @param workingSystem
	 *            労働制クラス
	 * @param predetermineTimeSet
	 *            所定時間設定クラス
	 * @param fixedWorkSetting
	 *            固定勤務設定クラス
	 * @param workTimeCommonSet
	 *            就業時間帯の共通設定クラス
	 * @param bonusPaySetting
	 *            加給設定クラス
	 * @param overTimeHourSetList
	 *            残業時間の時間帯設定クラス
	 * @param fixOff
	 *            固定勤務の休日出勤用勤務時間帯クラス
	 * @param dayEndSet
	 *            0時跨ぎ計算設定クラス
	 * @param overDayEndSet
	 *            就業時間帯の共通設定クラス
	 * @param holidayTimeWorkItem
	 *            休出枠時間帯
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
	 * @param autoCalculationSet
	 *            残業時間の自動計算設定クラス
	 * @param statutorySet
	 *            法定内残業設定
	 * @param prioritySet
	 *            法定内優先設定
	 * @param deductionTimeSheet
	 *            控除時間帯
	 */
	public void theDayOfWorkTimesLoop(WorkingSystem workingSystem, PredetemineTimeSetting predetermineTimeSet,
			FixedWorkSetting fixedWorkSetting, WorkTimezoneCommonSet workTimeCommonSet, BonusPaySetting bonusPaySetting,
			List<OverTimeOfTimeZoneSet> overTimeHourSetList, FixOffdayWorkTimezone fixOff, OverDayEndCalcSet dayEndSet,
			List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay, WorkType toDay, WorkType afterDay,
			BreakdownTimeDay breakdownTimeDay, DailyTime dailyTime, AutoCalculationOfOverTimeWork autoCalculationSet,
			LegalOTSetting statutorySet, StatutoryPrioritySet prioritySet,
			DeductionTimeSheet deductionTimeSheet, WorkTimeSetting workTime) {
		if (!workingSystem.isExcludedWorkingCalculate()) {
			/* 計算対象外の処理 */
			return;
		}
		for (int workNumber = 1; workNumber <= attendanceLeavingWork.getTimeLeavingWorks().size(); workNumber++) {
			/* 就業内の時間帯作成 */
			withinWorkingTimeSheet.set(WithinWorkTimeSheet.createAsFixed(toDay, predetermineTimeSet, fixedWorkSetting,
					workTimeCommonSet, deductionTimeSheet, bonusPaySetting));
			/* 就業外の時間帯作成 */
//			outsideWorkTimeSheet.set(OutsideWorkTimeSheet.createOutsideWorkTimeSheet(overTimeHourSetList, fixOff,
//					attendanceLeavingWork.getAttendanceLeavingWork(new WorkNo(workNumber)),
//					workNumber, dayEndSet, workTimeCommonSet, holidayTimeWorkItem, beforeDay, toDay, afterDay, workTime,
//					workingSystem, breakdownTimeDay, dailyTime, autoCalculationSet, statutorySet, prioritySet));
		}

	}

	/**
	 * 各深夜時間の算出結果から深夜時間の合計を算出する
	 * 
	 * @return 深夜時間
	 */
	public ExcessOfStatutoryTimeOfDaily calcMidNightTime(ExcessOfStatutoryTimeOfDaily excessOfDaily,
			AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		// ExcessOverTimeWorkMidNightTime excessHolidayWorkMidNight =
		// excessOfDaily.getOverTimeWork().get().calcMidNightTimeIncludeOverTimeWork();
		// HolidayMidnightWork excessMidNight =
		// excessOfDaily.getWorkHolidayTime().get().calcMidNightTimeIncludeHolidayWorkTime(autoCalcSet);
		int beforeTime = 0;
		int totalTime = 0/* 残業深夜と休出深夜の合計算出 */;
		excessOfDaily.setExcessOfStatutoryMidNightTime(
				new ExcessOfStatutoryMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(totalTime)), new AttendanceTime(beforeTime)));
		return excessOfDaily;
	}

	/**
	 * 加給時間の合計計算
	 */
	public RaiseSalaryTimeOfDailyPerfor calcTotalBonusPay(WithinWorkTimeSheet withinWorkSheet,
			OverTimeOfDaily overTimeWorkOfDaily, BonusPayAutoCalcSet bonusPayAutoCalcSet, BonusPayAtr bonusPayAtr,
			CalAttrOfDailyPerformance calcAtrOfDaily) {
		return new RaiseSalaryTimeOfDailyPerfor(
				withinWorkSheet.calcBonusPayTimeInWithinWorkTime(bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily),
				calcTotalSpecifiedBonusPay(withinWorkSheet, overTimeWorkOfDaily, bonusPayAutoCalcSet, bonusPayAtr,
						calcAtrOfDaily));
	}

	/**
	 * 特定日加給時間の合計時間
	 */
	public List<BonusPayTime> calcTotalSpecifiedBonusPay(WithinWorkTimeSheet withinWorkSheet,
			OverTimeOfDaily overTimeWorkOfDaily, BonusPayAutoCalcSet bonusPayAutoCalcSet, BonusPayAtr bonusPayAtr,
			CalAttrOfDailyPerformance calcAtrOfDaily) {
		List<BonusPayTime> bonusPayList = withinWorkSheet.calcSpecifiedBonusPayTimeInWithinWorkTime(bonusPayAutoCalcSet,
				bonusPayAtr, calcAtrOfDaily);
		bonusPayList
				.addAll(overTimeWorkOfDaily.calcSpecifiedBonusPay(bonusPayAutoCalcSet, bonusPayAtr, calcAtrOfDaily));
		return bonusPayList;
	}

	/**
	 * 勤務形態、就業時間帯の設定を判定し時間帯を作成(
	 * 
	 * @param workTimeDivision
	 * @param personalInfo
	 * @param setMethod
	 * @param clockManage
	 * @param dailyGoOutSheet
	 * @param commonSet
	 * @param fixedCalc
	 * @param fluidSet
	 * @param breakmanage
	 * @param fluRestTime
	 * @param fluidprefixBreakTimeSet
	 * @param predetermineTimeSet
	 * @param fixedWorkSetting
	 * @param workTimeCommonSet
	 * @param bonusPaySetting
	 * @param overTimeHourSetList
	 * @param fixOff
	 * @param dayEndSet
	 * @param holidayTimeWorkItem
	 * @param beforeDay
	 * @param toDay
	 * @param afterDay
	 * @param breakdownTimeDay
	 * @param dailyTime
	 * @param autoCalculationSet
	 * @param statutorySet
	 * @param prioritySet
	 */
	public void decisionWorkClassification(WorkTimeDivision workTimeDivision,
			DailyCalculationPersonalInformation personalInfo, WorkTimeMethodSet setMethod,
			RestClockManageAtr clockManage, OutingTimeOfDailyPerformance dailyGoOutSheet, CommonRestSetting commonSet,
			FixedRestCalculateMethod fixedCalc, FlowRestCalcMethod fluidSet, BreakTimeManagement breakmanage,
			Optional<FlowWorkRestTimezone> fluRestTime, FlowFixedRestSet fluidprefixBreakTimeSet,
			PredetemineTimeSetting predetermineTimeSet, FixedWorkSetting fixedWorkSetting, WorkTimezoneCommonSet workTimeCommonSet,
			BonusPaySetting bonusPaySetting, List<OverTimeOfTimeZoneSet> overTimeHourSetList, FixOffdayWorkTimezone fixOff,
			OverDayEndCalcSet dayEndSet, List<HolidayWorkFrameTimeSheet> holidayTimeWorkItem, WorkType beforeDay,
			WorkType toDay, WorkType afterDay, BreakdownTimeDay breakdownTimeDay, DailyTime dailyTime,
			AutoCalculationOfOverTimeWork autoCalculationSet, LegalOTSetting statutorySet,
			StatutoryPrioritySet prioritySet, WorkTimeSetting workTime) {
		if (workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
			/* フレックス勤務 */
		} else {
			switch (workTimeDivision.getWorkTimeMethodSet()) {
			case FIXED_WORK:
				/* 固定 */
				createWithinWorkTimeSheet(personalInfo.getWorkingSystem(), setMethod, clockManage, dailyGoOutSheet,
						commonSet, fixedCalc, workTimeDivision, fluidSet, breakmanage, fluRestTime,
						fluidprefixBreakTimeSet, predetermineTimeSet, fixedWorkSetting, workTimeCommonSet,
						bonusPaySetting, overTimeHourSetList, fixOff, dayEndSet, holidayTimeWorkItem, beforeDay, toDay,
						afterDay, breakdownTimeDay, dailyTime, autoCalculationSet, statutorySet, prioritySet, workTime);
			case FLOW_WORK:
				/* 流動勤務 */
			case DIFFTIME_WORK:
				/* 時差勤務 */
//			case Enum_Overtime_Work:
			default:
				throw new RuntimeException("unknown workTimeMethodSet" + workTimeDivision.getWorkTimeMethodSet());

			}

		}
		/* 控除時間帯の作成 */
		// //
	}

	// /**
	// * フレックスの時間帯作成
	// */
	// public WithinWorkTimeSheet createTimeSheetAsFlex(FluRestTime
	// flexTimeSet,CoreTimeSetting coreTimeSetting,WorkType workType,
	// PredetermineTimeSet predetermineTimeSet,FixedWorkSetting fixedWorkSetting
	// ,DailyWork dailyWork){
	// if(!flexTimeSet.getUseFixedRestTime()){
	// predetermineTimeSetForCalc.correctPredetermineTimeSheet(dailyWork);
	// /*遅刻早退処理*/
	// for() {
	// WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSet,fixedWorkSetting);
	// /*遅刻時間の計算*/
	// /*早退時間の計算*/
	// }
	// WithinWorkTimeSheet.createWorkingHourSet(workType,predetermineTimeSet,fixedWorkSetting);
	// }
	// provisionalDeterminationOfDeductionTimeSheet();
	// /*固定勤務の時間帯作成*/
	// theDayOfWorkTimesLoop();
	// /*コアタイムのセット*/
	// return withinWorkingTimeSheet.createWithinFlexTimeSheet(coreTimeSetting);
	// }
	//
	// /**
	// * 流動休憩用の控除時間帯作成
	// */
	// public void createFluidBreakTime(DeductionAtr deductionAtr) {
	// DeductionTimeSheet.createDedctionTimeSheet(acqAtr, setMethod,
	// clockManage, dailyGoOutSheet, oneDayRange, CommonSet,
	// attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fixedSet,
	// breakTimeSheet);
	//
	// }

	// ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	//
	// /**
	// * 流動勤務の時間帯作成
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
	// //所定時間設定をコピーして計算用の所定時間設定を作成する
	// this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
	// predetermineTimeSet.getAdditionSet(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getAMEndTime(),
	// predetermineTimeSet.getSpecifiedTimeSheet().getPMStartTime());
	// //出退勤分ループ
	// for(AttendanceLeavingWork attendanceLeavingWork :
	// attendanceLeavingWork.getAttendanceLeavingWork(workNo)) {
	// //事前に遅刻早退、控除時間帯を取得する
	// this.getForDeductionTimeSheetList(workNo, attendanceLeavingWork,
	// predetermineTimeSet, deductionTimeSheet ,workInformationOfDaily,
	// workType, withinWorkTimeFrame);
	// }
	// //「出勤系」か「休出系」か判断する
	// boolean isWeekDayAttendance = worktype.isWeekDayAttendance();
	// //時間休暇加算残時間未割当←時間休暇加算残時間
	//
	// if(isWeekDayAttendance) {//出勤系の場合
	// //流動勤務（就内、平日）
	// WithinWorkTimeSheet newWithinWorkTimeSheet =
	// withinWorkTimeSheet.createAsFluidWork(predetermineTimeSetForCalc,
	// worktype, workInformationOfDaily, fluidWorkSetting, deductionTimeSheet);
	// //流動勤務（就外、平日）
	//
	// }else{//休出系の場合
	// //流動勤務（休日出勤）
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
	// * 事前に遅刻早退、控除時間帯を取得する
	// * @param workNo
	// * @param attendanceLeavingWork 出退勤
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
	// //所定時間帯を取得する(流動計算で使用する所定時間の作成)
	// createPredetermineTimeSheetForFluid(workNo, predetermineTimeSet,
	// workType, workInformationOfDaily);
	// //計算範囲を判断する
	// withinWorkTimeFrame.createWithinWorkTimeFrameForFluid(attendanceLeavingWork,
	// dailyWork, predetermineTimeSetForCalc);
	// //遅刻時間帯を控除
	// withinWorkTimeFrame.getLateTimeSheet().lateTimeCalcForFluid(withinWorkTimeFrame,
	// lateRangeForCalc, workTimeCommonSet, lateDecisionClock,
	// deductionTimeSheet);
	// //控除時間帯の仮確定
	// this.provisionalDeterminationOfDeductionTimeSheet(deductionTimeSheet);
	// //早退時間帯を控除
	//
	// //勤務間の休憩設定を取得
	//
	// }
	//
	// /**
	// * 計算用所定時間設定を作成する（流動用）
	// * @return
	// */
	// public void createPredetermineTimeSheetForFluid(
	// int workNo,
	// PredetermineTimeSet predetermineTimeSet,
	// WorkType workType,
	// WorkInformationOfDaily workInformationOfDaily) {
	//
	// //予定と実績が同じ勤務かどうか確認
	// if(workInformationOfDaily.isMatchWorkInfomation()/*予定時間帯に値が入っているかのチェックを追加する必要あり*/)
	// {
	// //予定時間帯を取得する
	// ScheduleTimeSheet scheduleTimeSheet =
	// workInformationOfDaily.getScheduleTimeSheet(workNo);
	// //所定時間帯設定の時間帯を全て取得する
	// List<TimeSheetWithUseAtr> timeSheetList =
	// predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets();
	// //変更対象の時間帯を取得
	// List<TimeSheetWithUseAtr> list = timeSheetList.stream().filter(ts ->
	// ts.getCount()==workNo).collect(Collectors.toList());
	// TimeSheetWithUseAtr timeSheet = list.get(0);
	// //予定時間帯と変更対象の時間帯を基に時間帯を作成
	// TimeSheetWithUseAtr targetTimeSheet = new TimeSheetWithUseAtr(
	// timeSheet.getUseAtr(),
	// scheduleTimeSheet.getAttendance(),
	// scheduleTimeSheet.getLeaveWork(),
	// workNo);
	// //変更対象以外の時間帯を取得
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
	// //午前勤務、午後勤務の場合に時間帯を補正する処理
	// this.predetermineTimeSetForCalc.getPredetermineTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());
	// }
	//
	//
	// /**
	// * 控除時間帯の仮確定
	// */
	// public void
	// provisionalDeterminationOfDeductionTimeSheet(DeductionTimeSheet
	// deductionTimeSheet) {
	// //控除用
	// deductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(fluidWorkSetting);
	// //計上用
	// deductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(fluidWorkSetting);
	// }
	//

}
