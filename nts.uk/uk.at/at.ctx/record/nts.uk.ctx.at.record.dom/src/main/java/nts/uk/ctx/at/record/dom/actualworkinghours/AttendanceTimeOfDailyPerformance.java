package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の勤怠時間 - root
 *
 */
@Getter
public class AttendanceTimeOfDailyPerformance extends AggregateRoot {

	private String employeeId;
	
	private GeneralDate ymd;
	
	//勤務予定時間
	private WorkScheduleTimeOfDaily workScheduleTimeOfDaily;
	
	//実働時間 
	private ActualWorkingTimeOfDaily actualWorkingTimeOfDaily;
	
	//滞在時間
	private AttendanceTime stayingTime;
	
	//不就労時間
	private AttendanceTime unEmployedTime;
	
	//日別実績の勤怠時間
	private AttendanceTimeOfDailyPerformance attendanceTimeOfDaily;
	
	/**
	 * Constructor
	 * @param actualWorkingTimeOfDaily
	 */
	private AttendanceTimeOfDailyPerformance(ActualWorkingTimeOfDaily actualWorkingTimeOfDaily) {
		this.actualWorkingTimeOfDaily = actualWorkingTimeOfDaily;
	}
	
	
	/**
	 * 日別実績の勤怠時間の計算
	 * @param oneDay 1日の範囲クラス
	 * @return 日別実績(Work)クラス
	 */
	public static IntegrationOfDaily calcTimeResult(CalculationRangeOfOneDay oneDay,IntegrationOfDaily integrationOfDaily) {
		integrationOfDaily.setAttendanceTimeOfDailyPerformance(collectCalculationResult(oneDay));
		return integrationOfDaily;
	}
	
	/**
	 * 時間の計算結果をまとめて扱う
	 * @param 1日の範囲クラス
	 */
	private static AttendanceTimeOfDailyPerformance collectCalculationResult(CalculationRangeOfOneDay oneDay) {
		/*所定時間の計算*/
		/*勤務予定時間の計算*/
		
		/*日別実績の実績時間の計算*/
		//actualWorkingTimeOfDaily = ActualWorkingTimeOfDaily.calcRecordTime(oneDay);
		return new AttendanceTimeOfDailyPerformance(ActualWorkingTimeOfDaily.calcRecordTime(oneDay));
		/*予定差異時間の計算*/
		/*滞在時間の計算*/

//      下書き(ここに持ってくる前に書いていたコード達)
//		OverTimeWorkOfDaily overTimeWorkTime = overTimeWorkSheet.calcOverTimeWork()；/*残業時間の計算*/
//		int totalOverTimeWorkTime = overTimeWorkTime.getOverTimeWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*残業時間の計算*/
//		HolidayWorkTimeOfDaily holidayWorkTime = holidayWorkTimeSheet.calcHolidayWorkTime();/*休日出勤の計算*/
//		int totalHolidayWorkTime = holidayWorkTime.getHolidayWorkFrameTime().stream().collect(Collectors.summarizingInt(tc -> tc.));/*休日出勤の計算*/
//		int deductionBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Deduction);/*休憩時間の計算*/
//		int recordBreakTime = deductionTimeSheet.getTotalBreakTime(DeductionAtr.Appropriate);/*計上用の休憩時間の計算*/
////		int deductionGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Deduction);/*控除用の外出時間の計算*/
////		int recordGoOutTime = deductionTimeSheet.getTotalGoOutTime(DeductionAtr.Appropriate);/*計上用の外出時間の計算*/
//		return /*法定労働時間*/ - calcWithinWorkTime;
	}
	
}
