package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.WorkInformationOfDaily;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

/**
 * 日別実績(Work)
 * @author keisuke_hoshina
 *
 */
@Getter
public class IntegrationOfDaily {

	private WorkInformationOfDaily workInformation;
	private TimeLeavingOfDailyPerformance attendanceLeave;
	@Setter
	private AttendanceTimeOfDailyPerformance attendanceTimeOfDailyPerformance; 

}
