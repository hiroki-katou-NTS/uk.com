package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
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
 * 1日の計算�?��
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
	 * 就業�?��間帯の作�?
	 */
	public void createWithinWorkTimeSheet() {
		/*固定控除時間帯の作�?*/
		DedcutionTimeSheet collectDeductionTimes = new DeductionTimeSheet();
		collectDeductionTimes.createDedctionTimeSheet();
		
		
		if(workingSystem.isExcludedWorkingCalculate()) {
			theDayOfWorkTimesLoop();
		}else{
			/*計算対象外�?処�?*/
			return;
		}
	}

	/**
	 * 勤務回数�?��ー�?
	 * 就�??外時間帯作�?と計�?
	 */
	public void theDayOfWorkTimesLoop() {
		for(int workNumber = 1; workNumber <= dailyOfAttendanceLeavingWork.size(); workNumber++ ) {
			createWithinWorkTimeTimeSheet();
			/*就�?*/
			/*勤務時間帯の計�?*/
			
		}
	}
	
	/**
	 * 就業時間�?��間帯の作�?
	 * @param workType�?勤務種類コー�?
	 * @param predetermineTimeSet �?定時間帯の設�?
	 * @param fixedWorkSetting 固定勤務�?設�?
	 */
	public void createWithinWorkTimeTimeSheet() {
		if(workType.isWeekDayAttendance()) {
			 WithinWorkTimeSheet.createAsFixedWork(workType, workTime.getPredetermineTimeSet(), workTime.getFixedWorkSetting());
		}
	}
	
	/**
	 * 就業時間外時間帯の作�?
	 */
	public void createOutOfWorkTimeSheet(WorkType workType) {
		if(workType.isWeekDayAttendance()) {
			/*就業時間外�??平日出勤の処�?*/
		}
		else {
			/*休日出勤*/
		}
	}

	/**
	 * 勤務�?時間帯を判定し時間帯を作�?
	 * @param workTimeDivision
	 */
	public void decisionWorkClassification(WorkTimeDivision workTimeDivision) {
		/*就業区�??取�?*/
		//           //
		if(workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
			/*フレ�?��ス勤務�?処�?*/
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
		/*控除時間帯の作�?*/
		//             //
	}
}
