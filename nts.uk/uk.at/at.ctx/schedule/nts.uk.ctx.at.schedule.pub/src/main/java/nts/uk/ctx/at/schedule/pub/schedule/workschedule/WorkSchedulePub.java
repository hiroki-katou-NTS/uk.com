package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public interface WorkSchedulePub {
	public Optional<WorkScheduleExport> get(String employeeID , GeneralDate ymd);
}
