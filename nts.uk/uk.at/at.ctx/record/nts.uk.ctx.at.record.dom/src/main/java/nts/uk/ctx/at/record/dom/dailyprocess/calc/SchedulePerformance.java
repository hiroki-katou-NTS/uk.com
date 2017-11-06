package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;

/**
 * 予定実績
 * @author keisuke_hoshina
 *
 */
@Getter
public class SchedulePerformance {
	private WorkInformation workInformation;
	private WorkScheduleTimeOfDaily actualTime;
	//private  出退勤
	
}
