package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistory;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixWeekdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 1日の計算範囲
 * @author keisuke_hoshina
 *
 */
@Value
public class CalculationRangeOfOneDay {
	
	private FixWeekdayWorkTime fixWeekDayWorkTime;
	
	private List<AttendanceLeavingWorkOfDaily> dailyOfAttendanceLeavingWork;
	
	private final WorkTime workTime;
	
	private final WorkType workType;
	
	private WithinWorkTimeSheet withinWorkingHoursTimeSheet;
	
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
		for(int workNumber = 1; workNumber <= dailyOfAttendanceLeavingWork.size(); workNumber++ ) {
			createWithinWorkTimeTimeSheet();
			
			/*勤務時間帯の計算*/
			
		}
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
	public void createOutOfWorkTimeSheet(WorkType workType,FixOffdayWorkTime fixOff, AttendanceLeavingWork attendanceLeave) {
		if(workType.isWeekDayAttendance()) {
			/*就業時間外時間帯の平日出勤の処理*/
		}
		else {
			/*休日出勤*/
			return HolidayWorkTimeOfDaily.getHolidayWorkTimeOfDaily(fixOff.getWorkingTimes(), attendanceLeave);
			
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
}
