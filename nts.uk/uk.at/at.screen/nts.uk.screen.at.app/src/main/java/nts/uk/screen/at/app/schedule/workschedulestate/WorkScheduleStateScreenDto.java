package nts.uk.screen.at.app.schedule.workschedulestate;

import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author sonnh1
 *
 */
@Value
public class WorkScheduleStateScreenDto {
	private String employeeId;
	private GeneralDate date;
	private int scheduleItemId;
	private int scheduleEditState;
}
