package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

/**
 * 日別実績(Work)
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class IntegrationOfDaily {

	private WorkInfoOfDailyPerformance workInformation;
	private TimeLeavingOfDailyPerformance attendanceLeave;
	@Setter
	private AttendanceTimeOfDailyPerformance attendanceTimeOfDailyPerformance; 
	

}
