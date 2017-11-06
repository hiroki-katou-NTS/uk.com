package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.BonusPayTime;
import nts.uk.ctx.at.record.dom.daily.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistory;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.CalculationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.HolidayAdditionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodOfHoliday;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetWithUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixWeekdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 1日の計算範囲
 * @author keisuke_hoshina
 *
 */
@Getter
@RequiredArgsConstructor
public class CalculationRangeOfOneDay {
	
	private FixWeekdayWorkTime fixWeekDayWorkTime;
	
	private final WorkTime workTime;
	
	private final WorkType workType;
	
	private WithinWorkTimeSheet withinWorkingTimeSheet;
	
	private OutsideWorkTimeSheet outsideWorkTimeSheet;
	
	private WorkingSystem workingSystem;
	
	private TimeSpanForCalc oneDayOfRange;
	
	private AttendanceLeavingWorkOfDaily attendanceLeavingWork;
	
	private PredetermineTimeSetForCalc predetermineTimeSetForCalc;
	
	private TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime;//時間休暇加算残時間
	
	
	/**
	 * 就業時間帯の作成
	 */
	public void createWithinWorkTimeSheet() {
		/*固定控除時間帯の作成*/
//		DedcutionTimeSheet collectDeductionTimes = new DeductionTimeSheet();
		DeductionTimeSheet deductionTimeSheet = DeductionTimeSheet.createDedctionTimeSheet(acqAtr, setMethod, clockManage, dailyGoOutSheet, oneDayRange, CommonSet, attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fixedSet, breakTimeSheet)
		
		
		if(workingSystem.isExcludedWorkingCalculate()) {
			theDayOfWorkTimesLoop();
		}else{
			/*計算対象外の処理*/
			return;
		}
	}

	/**
	 * 勤務回数分のループ
	 * 就業時間内・外の処理
	 */
	public void theDayOfWorkTimesLoop() {
		for(int workNumber = 0; workNumber <= dailyOfAttendanceLeavingWork.size(); workNumber++ ) {
			createWithinWorkTimeTimeSheet();
			createOutOfWorkTimeSheet();
			/*勤務時間帯の計算*/
		}
	}
	
	/**
	 * 時間の計算結果をまとめて扱う
	 * @param withinTimeSheet 計算用就業時間帯
	 * @param overTimeWorkSheet　計算用残業時間帯
	 * @param holidayWorkTimeSheet　計算用休出時間帯
	 * @param deductionTimeSheet　計算用控除時間帯
	 * @param actualTimeAtr
	 */
	private void collectCalculationResult(WithinWorkTimeSheet  withinTimeSheet,
										  OverTimeWorkSheet    overTimeWorkSheet,
										  HolidayWorkTimeSheet holidayWorkTimeSheet,
										  DeductionTimeSheet deductionTimeSheet,
										  CalculationByActualTimeAtr actualTimeAtr) {
		int calcWithinWorkTime = withinTimeSheet.calcWorkTime(actualTimeAtr, dedTimeSheet);
		OverTimeWorkOfDaily overTimeWorkTime = overTimeWorkSheet.calcOverTimeWork()；
		int totalOverTimeWorkTime = overTimeWorkTime.getOverTimeWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*残業時間の計算*/
		HolidayWorkTimeOfDaily holidayWorkTime = holidayWorkTimeSheet.calcHolidayWorkTime();
		int totalHolidayWorkTime = holidayWorkTime.getHolidayWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*休日出勤の計算*/
		int deductionBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Deduction);
		int recordBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Appropriate);/*計上用の休憩時間の計算*/
		int deductionGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Deduction);/*控除用の外出時間の計算*/
		int recordGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Appropriate);/*計上用の外出時間の計算*/
		int totalWorkingTime = calcWithinWorkTime + overTimeWorkTime.calcTotalFrameTime() + holidayWorkTime.calcTotalFrameTime();
		int totalBonusPayTime = ;
		int totalMidNightTime = ;
		return /*法定労働時間*/ - calcWithinWorkTime;
	}

	/**
	 * 就業時間内時間帯の作成
	 * @param workType 勤務種類クラス
	 * @param predetermineTimeSet 所定時間の設定
	 * @param fixedWorkSetting 固定勤務の設定
	 */
	public void createWithinWorkTimeTimeSheet() {
		if(workType.isWeekDayAttendance()) {
			 WithinWorkTimeSheet.createAsFixedWork(workType, workTime.getPredetermineTimeSet(), workTime.getFixedWorkSetting());
		}
	}
	
	/**
	 * 就業時間外時間帯の作成
	 * 	 
	 */
	public void createOutOfWorkTimeSheet(List<OverTimeHourSet> overTimeHourSet ,WorkType workType,FixOffdayWorkTime fixOff, AttendanceLeavingWork attendanceLeave,int workNo) {
		if(workType.isWeekDayAttendance()) {
			/*就業時間外時間帯の平日出勤の処理*/
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(Optional.of(OverTimeWorkSheet.createOverWorkFrame(overTimeHourSet, workingSystem, attendanceLeave,workTime.getPredetermineTimeSet().getSpecifiedTimeSheet().getTimeSheets().get(1).getStartTime(), workNo)),Optional.empty());
		}
		else {
			/*休日出勤*/
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(Optional.empty(), Optional.of(HolidayWorkTimeOfDaily.getHolidayWorkTimeOfDaily(fixOff.getWorkingTimes(), attendanceLeave)));
		}
	}
	
	/**
	 * 各深夜時間の算出結果から深夜時間の合計を算出する
	 * @return 深夜時間
	 */
	public ExcessOfStatutoryTimeOfDaily calcMidNightTime(ExcessOfStatutoryTimeOfDaily excessOfDaily) {
		ExcessOverTimeWorkMidNightTime excessMidNight = excessOfDaily.getOverTimeWork().calcMidNightTimeIncludeHolidayWorkTime();
		int totalTime = /*残業深夜と休出深夜の合計算出*/;
		excessOfDaily.setExcessOfStatutoryMidNightTime(new ExcessOverTimeWorkMidNightTime(TimeWithCalculation.sameTime(new AttendanceTime(totalTime))));
		return excessOfDaily;
	}

	/**
	 * 加給時間の合計計算
	 */
	public void calcBonusPay(WithinWorkTimeSheet withinWorkSheet,OverTimeWorkOfDaily overTimeWorkOfDaily) {
		(/*区分 = 加給*/);
		(/*区分 = 特定日加給*/);
		
		List<BonusPayTime> bonusPayList = withinWorkSheet.calcBonusPayTimeInWithinWorkTime();
		bonusPayList.addAll(overTimeWorkOfDaily.calcBonusPay());
		bonusPayList.addAll(calcSpecifiedBonusPay());
	}
	
	/**
	 * 特定日加給時間の合計時間
	 */
	public void calcSpecifiedBonusPay(WithinWorkTimeSheet withinWorkSheet,OverTimeWorkOfDaily overTimeWorkOfDaily) {
		List<BonusPayTime> bonusPayList = withinWorkSheet.calcSpecifiedBonusPayTimeInWithinWorkTime(bonusPayAutoCalcSet, bonusPayAtr);
		bonusPayList.addAll(overTimeWorkOfDaily.calcSpecifiedBonusPay());
		bonusPayList.addAll(calcSpecifiedBonusPay());
	}
	
	
	/**
	 * 勤務形態、就業時間帯の設定を判定し時間帯を作成　
	 * @param workTimeDivision
	 */
	public void decisionWorkClassification(WorkTimeDivision workTimeDivision) {
		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
			/*フレックス勤務*/
		}
		else {
			switch(workTimeDivision.getWorkTimeMethodSet()) {
			case Enum_Fixed_Work:
				/*固定*/
				createWithinWorkTimeSheet();
			case Enum_Fluid_Work:
				/*流動勤務*/
			case Enum_Jogging_Time:
				/*時差勤務*/
			case Enum_Overtime_Work:
			default:
				throw new RuntimeException("unknown workTimeMethodSet" + workTimeDivision.getWorkTimeMethodSet());
		
			}

		}
		/*控除時間帯の作成*/
		//             //
	}

	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	/**
	 * 流動勤務の時間帯作成
	 */
	public void createFluidWork(
			int workNo,
			WorkTime workTime,
			AttendanceLeavingWorkOfDaily attendanceLeavingWork,
			DeductionTimeSheet deductionTimeSheet,
			PredetermineTimeSet predetermineTimeSet,
			WithinWorkTimeSheet withinWorkTimeSheet,
			WithinWorkTimeFrame withinWorkTimeFrame,
			HolidayWorkTimeSheet holidayWorkTimeSheet,
			WorkType worktype) {	
		//所定時間設定をコピーして計算用の所定時間設定を作成する
		this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
				predetermineTimeSet.getAdditionSet(),
				predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets(),
				predetermineTimeSet.getSpecifiedTimeSheet().getAMEndTime(),
				predetermineTimeSet.getSpecifiedTimeSheet().getPMStartTime());
		//出退勤分ループ
		for(AttendanceLeavingWork attendanceLeavingWork : attendanceLeavingWork.getAttendanceLeavingWork(workNo)) {
			//事前に遅刻早退、控除時間帯を取得する
			this.getForDeductionTimeSheetList(workNo, attendanceLeavingWork, predetermineTimeSet, deductionTimeSheet ,workInformationOfDaily, workType, withinWorkTimeFrame);
		}
		//「出勤系」か「休出系」か判断する
		boolean isWeekDayAttendance = worktype.isWeekDayAttendance();
		//時間休暇加算残時間未割当←時間休暇加算残時間
		
		if(isWeekDayAttendance) {//出勤系の場合
			//流動勤務（就内、平日）
			WithinWorkTimeSheet newWithinWorkTimeSheet = withinWorkTimeSheet.createAsFluidWork(predetermineTimeSetForCalc, worktype, workInformationOfDaily, fluidWorkSetting, deductionTimeSheet);
			//流動勤務（就外、平日）
			
		}else{//休出系の場合
			//流動勤務（休日出勤）
			HolidayWorkTimeSheet holidayWorkTimeSheet = holidayWorkTimeSheet.createholidayWorkTimeSheet(attendanceLeavingWork, workingTimes, deductionTimeSheet, worktype, holidayWorkTimeOfDaily, calcRange);
		}
			
		
	}
	
	/**
	 * 事前に遅刻早退、控除時間帯を取得する
	 * @param workNo
	 * @param attendanceLeavingWork 出退勤
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> getForDeductionTimeSheetList(
			int workNo,
			AttendanceLeavingWork attendanceLeavingWork,
			PredetermineTimeSet predetermineTimeSet,
			DeductionTimeSheet deductionTimeSheet,
			WorkInformationOfDaily workInformationOfDaily,
			WorkType workType,
			WithinWorkTimeFrame withinWorkTimeFrame){
		
		//所定時間帯を取得する(流動計算で使用する所定時間の作成)
		createPredetermineTimeSheetForFluid(workNo, predetermineTimeSet, workType, workInformationOfDaily);
		//計算範囲を判断する
		withinWorkTimeFrame.createWithinWorkTimeFrameForFluid(attendanceLeavingWork, dailyWork, predetermineTimeSetForCalc);
		//遅刻時間帯を控除
		withinWorkTimeFrame.getLateTimeSheet().lateTimeCalcForFluid(withinWorkTimeFrame, lateRangeForCalc, workTimeCommonSet, lateDecisionClock, deductionTimeSheet);
		//控除時間帯の仮確定
		this.provisionalDeterminationOfDeductionTimeSheet(deductionTimeSheet);
		//早退時間帯を控除
		
		//勤務間の休憩設定を取得
		
	}
	
	/**
	 * 計算用所定時間設定を作成する（流動用）
	 * @return
	 */
	public void createPredetermineTimeSheetForFluid(
			int workNo,
			PredetermineTimeSet predetermineTimeSet,
			WorkType workType,
			WorkInformationOfDaily workInformationOfDaily) {

		//予定と実績が同じ勤務かどうか確認
		if(workInformationOfDaily.isMatchWorkInfomation()/*予定時間帯に値が入っているかのチェックを追加する必要あり*/) {
			//予定時間帯を取得する
			ScheduleTimeSheet scheduleTimeSheet = workInformationOfDaily.getScheduleTimeSheet(workNo);
			//所定時間帯設定の時間帯を全て取得する
			List<TimeSheetWithUseAtr> timeSheetList = predetermineTimeSet.getSpecifiedTimeSheet().getTimeSheets();
			//変更対象の時間帯を取得
			List<TimeSheetWithUseAtr> list = timeSheetList.stream().filter(ts -> ts.getCount()==workNo).collect(Collectors.toList());
			TimeSheetWithUseAtr timeSheet = list.get(0);
			//予定時間帯と変更対象の時間帯を基に時間帯を作成
			TimeSheetWithUseAtr targetTimeSheet = new TimeSheetWithUseAtr(
					timeSheet.getUseAtr(),
					scheduleTimeSheet.getAttendance(),
					scheduleTimeSheet.getLeaveWork(),
					workNo);
			//変更対象以外の時間帯を取得
			List<TimeSheetWithUseAtr> list2 = timeSheetList.stream().filter(ts -> ts.getCount()!=workNo).collect(Collectors.toList());
			TimeSheetWithUseAtr timeSheet2 = list2.get(0);
			
			List<TimeSheetWithUseAtr> newTimeSheetList = Arrays.asList(targetTimeSheet,timeSheet2);
			
			this.predetermineTimeSetForCalc = new PredetermineTimeSetForCalc(
					this.predetermineTimeSetForCalc.getAdditionSet(),
					newTimeSheetList,
					this.predetermineTimeSetForCalc.getAMEndTime(),
					this.predetermineTimeSetForCalc.getPMStartTime());		
		}
		//午前勤務、午後勤務の場合に時間帯を補正する処理
		this.predetermineTimeSetForCalc.getPredetermineTimeSheet().correctPredetermineTimeSheet(workType.getDailyWork());			
	}
	
	
	/**
	 * 控除時間帯の仮確定
	 */
	public void provisionalDeterminationOfDeductionTimeSheet(DeductionTimeSheet deductionTimeSheet) {
		//控除用
		deductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(fluidWorkSetting);
		//計上用
		deductionTimeSheet.provisionalDecisionOfDeductionTimeSheet(fluidWorkSetting);
	}
		
	
	
}
