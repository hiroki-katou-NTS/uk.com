package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.daily.WorkInfomation;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;
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
	
	private List<AttendanceLeavingWorkOfDaily> dailyOfAttendanceLeavingWork;
	
	private final WorkTime workTime;
	
	private final WorkType workType;
	
	private WithinWorkTimeSheet withinWorkingTimeSheet;
	
	private OutsideWorkTimeSheet outsideWorkTimeSheet;
	
	private WorkingSystem workingSystem;
	
	private TimeSpanForCalc oneDayOfRange;
	
	private AttendanceLeavingWorkOfDaily attendanceLeavingWork;
	
	
	/**
	 * 就業時間帯の作成
	 */
	public void createWithinWorkTimeSheet() {
		/*固定控除時間帯の作成*/
//		DedcutionTimeSheet collectDeductionTimes = new DeductionTimeSheet();
		collectDeductionTimes.createDedctionTimeSheet();
		
		
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
			/*就業外*/
			/*勤務時間帯の計算*/
		}
	}
	/**
	 * 
	 */
	private void collectCalculationResult(WithinWorkTimeSheet withinTimeSheet, CalculationByActualTimeAtr actualTimeAtr,) {
		int calcWithinWorkTime = withinTimeSheet.calcWorkTime(actualTimeAtr, dedTimeSheet);
		/*法定労働時間取得*/
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
	//		 WithinWorkTimeSheet.createAsFixedWork(workType, workTime.getPredetermineTimeSet(), workTime.getFixedWorkSetting());
		}
	}
	
	/**
	 * 就業時間外時間帯の作成
	 * 	 
	 */
	public void createOutOfWorkTimeSheet(List<OverTimeHourSet> overTimeHourSet ,WorkType workType,FixOffdayWorkTime fixOff, AttendanceLeavingWork attendanceLeave,int workNo) {
		if(workType.isWeekDayAttendance()) {
			/*就業時間外時間帯の平日出勤の処理*/
			
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(Optional.of(OverTimeWorkSheet.createOverWorkFrame(overTimeHourSet, workingSystem, attendanceLeave,workTime.getPredetermineTimeSet().getSpecifiedTimeSheet().getTimeSheets().get(1).getStartTime(), workNo)),null);
			
			//こっちのreturn は OverTimeWorkSheet型
		}
		else {
			/*休日出勤*/
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(null, Optional.of(HolidayWorkTimeOfDaily.getHolidayWorkTimeOfDaily(fixOff.getWorkingTimes(), attendanceLeave)));
			//こっちのreturn は　HolidayWorkTimeOfDaily型
			
		}
	}

	/**
	 * 勤務　時間帯を判定し時間帯を作　
	 * @param workTimeDivision
	 */
	public void decisionWorkClassification(WorkTimeDivision workTimeDivision) {
		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
			/*フレックス勤務*/
		}
		else {
			switch(workTimeDivision.getWorkTimeMethodSet()) {
			case Enum_Fixed_Work:
				createWithinWorkTimeSheet();
			case Enum_Fluid_Work:
				/*流動勤務*/
			case Enum_Jogging_Time:
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
			WorkTime workTime,
			List<AttendanceLeavingWorkOfDaily> dailyOfAttendanceLeavingWork,
			PredetermineTimeSet predetermineTimeSet) {
		
		//出退勤を取得する
		
		
		//出退勤分ループ
		
		
		//「出勤系」か「休出系」か判断する
		
		//
		
			
	}
	
	/**
	 * 事前に遅刻早退、控除時間帯を取得する
	 * @param workNo
	 * @param attendanceLeavingWork 出退勤
	 * @return
	 */
	public static List<TimeSheetOfDeductionItem> getForDeductionTimeSheetList(
			int workNo,
			AttendanceLeavingWork attendanceLeavingWork,
			PredetermineTimeSet predetermineTimeSet){
		
		//所定時間帯を取得する
		
		
		//計算範囲を判断する
		
		
	}
	
	/**
	 * 所定時間帯を取得する(流動勤務用)
	 * @return
	 */
	public static void predetermineTimeSheetSetting(
			WorkTime workTime,
			int workNo,
			AttendanceLeavingWorkOfDaily attendanceLeavingWork,
			PredetermineTimeSet predetermineTimeSet,
			WorkType workType) {
		
		
		//予定と実績が同じ勤務かどうか確認　・・・日別実績の勤務情報クラスの処理　予定と実績が同じか返してくれる　　　　　　　　　　　　　　　　　　　　　　　　　
		boolean sameJudge;//仮作成　　同じ場合：true 違う場合：false
		
		
		if(sameJudge) {//予定時間帯から取得
			
			//予定時間帯を取得する
			
			predetermineTimeSet.getSpecifiedTimeSheet().getMatchWorkNoTimeSheet(workNo).newSpanWith(/*予定時間帯の開始*/, /*予定時間帯の終了*/);
			
		}
		//勤務状態を判断する為に1日の勤務を取得
		DailyWork dailyWork = workType.getDailyWork();
		//午前勤務、午後勤務の場合に時間帯を補正する処理
		predetermineTimeSet.getSpecifiedTimeSheet().correctPredetermineTimeSheet(dailyWork);

		
		
		
		
	}
	
	
	
}
