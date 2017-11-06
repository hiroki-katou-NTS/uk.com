package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;

/**
 * 日別実績(Work)
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
@Getter
public class IntegrationOfDaily {

	private WorkInformationOfDaily workInformation;
	private AttendanceLeavingWorkOfDaily attendanceLeave;
	
	
	/**
	 * 日別実績の時間を計算させる
	 */
	public void calcDailyRecord(CalculationRangeOfOneDay calcRangeOfOneDay) {
		val calcResult = calcRangeOfOneDay.collectCalculationResult(calcRangeOfOneDay.getWithinWorkingTimeSheet()
												 , calcRangeOfOneDay.getOutsideWorkTimeSheet().getOverTimeWorkSheet()
												 , calcRangeOfOneDay.getOutsideWorkTimeSheet().getHolidayWorkTimeSheet()
												 , deductionTimeSheet
												 , actualTimeAtr)
		
	}
}
