package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet.OverTimeWorkSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
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
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetWithUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixWeekdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
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
		int overTimeWorkTime = overTimeWorkSheet.calcOverTimeWork()；/*残業時間の計算*/
		int holidayWorkTime = holidayWorkTimeSheet.calcHolidayWorkTime();/*休日出勤の計算*/
		int deductionBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Deduction);
		int recordBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Appropriate);/*計上用の休憩時間の計算*/
		int deductionGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Deduction);/*控除用の外出時間の計算*/
		int recordGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Appropriate);/*計上用の外出時間の計算*/
		
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
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(Optional.of(OverTimeWorkSheet.createOverWorkFrame(overTimeHourSet, workingSystem, attendanceLeave,workTime.getPredetermineTimeSet().getSpecifiedTimeSheet().getTimeSheets().get(1).getStartTime(), workNo)),Optional.empty());
		}
		else {
			/*休日出勤*/
			outsideWorkTimeSheet = new OutsideWorkTimeSheet(Optional.empty(), Optional.of(HolidayWorkTimeOfDaily.getHolidayWorkTimeOfDaily(fixOff.getWorkingTimes(), attendanceLeave)));
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
	
	/**
	 * 流動勤務の時間帯作成
	 */
	public void createFluidWork(WorkTime workTime) {
		
		//所定時間帯を取得する
		getPredetermineTimeSet(workTime);
		
		//計算範囲を判断する
		
		
		//遅刻時間を計算
		
	
		
		
		
		
		
		
		
	}
	
	public void getPredetermineTimeSet(WorkTime workTime) {
		
		//予定と実績が同じ勤務かどうか確認(仮作成)　　日別実績の勤務情報のクラスのメソッドを呼び出す予定
		//日別実績の勤務情報のクラスから予定時間帯と勤務実績の勤務情報をもらう必要がある
		boolean compareResult;//予定勤務と同じかどうか判断した結果
		//予定時間帯
		List<ScheduleTimeSheet> workScheduleTimeSheet;
		//就業時間帯の設定　
		WorkTime worktime;
		
		
		if(compareResult) {
			//予定勤務から参照
			List<TimeSheetWithUseAtr> timeSheet = new ArrayList<TimeSheetWithUseAtr>();
			for() {
				
			}
			
			
		}else {
			//就業時間帯から参照
			
		}
		
		//predetermineTimeSet.getSpecifiedTimeSheet().correctPredetermineTimeSheet
		
		
	}
	
	
}
