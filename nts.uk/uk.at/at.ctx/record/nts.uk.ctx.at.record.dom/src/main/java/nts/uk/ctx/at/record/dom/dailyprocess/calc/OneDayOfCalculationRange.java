package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.DailyOfAttendanceLeavingWork;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentContractHistory;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.PredetermineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixWeekdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 1日の計算範囲
 * @author keisuke_hoshina
 *
 */
@Value
public class OneDayOfCalculationRange {
	
	private FixWeekdayWorkTime fixWeekDayWorkTime;
	
	private List<DailyOfAttendanceLeavingWork> dailyOfAttendanceLeavingWork;
	
	private final WorkTime workTime;
	
	private final WorkType workType;
	
	private WithinWorkTimeSheet withinWorkingHoursTimeSheet;
	
	private WorkingSystem workingSystem;
	/**
	 * 就業内時間帯の作成
	 */
	public void createWithinWorkTimeSheet() {
		/*固定控除時間帯の作成*/
		
		if(workingSystem.isExcludedWorkingCalculate()) {
			theDayOfWorkTimesLoop();
		}else{
			
			return;
		}
	}

	/**
	 * 勤務回数分ループ
	 * 就内・外時間帯作成と計算
	 */
	public void theDayOfWorkTimesLoop() {
		for(int workNumber = 0; workNumber < dailyOfAttendanceLeavingWork.size(); workNumber++ ) {
			createWithinWorkTimeTimeSheet();
			/*就外*/
			/**/
			
		}
	}
	
	/**
	 * 就業時間内時間帯の作成
	 * @param workType　勤務種類コード
	 * @param predetermineTimeSet 所定時間帯の設定
	 * @param fixedWorkSetting 固定勤務の設定
	 */
	public void createWithinWorkTimeTimeSheet() {
		if(workType.isWeekDayAttendance()) {
			 WithinWorkTimeSheet.createAsFixedWork(workType, workTime.getPredetermineTimeSet(), workTime.getFixedWorkSetting());
		}
	}
	
	/**
	 * 就業時間外時間帯の作成
	 */
	public void createOutOfWorkTimeSheet(WorkType workType) {
		if(workType.isWeekDayAttendance()) {
			/*就業時間外　平日出勤の処理*/
		}
		else {
			/*休日出勤*/
		}
	}

	/**
	 * 勤務の時間帯を判定し時間帯を作成
	 * @param workTimeDivision
	 */
	public void decisionWorkClassification(WorkTimeDivision workTimeDivision) {
		/*就業区分の取得*/
		//           //
		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
			/*フレックス勤務の処理*/
		}
		else {
			switch(workTimeDivision.getWorkTimeMethodSet()) {
			case Enum_Fixed_Work:
				createWithinWorkTimeSheet();
			case Enum_Fluid_Work:
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
