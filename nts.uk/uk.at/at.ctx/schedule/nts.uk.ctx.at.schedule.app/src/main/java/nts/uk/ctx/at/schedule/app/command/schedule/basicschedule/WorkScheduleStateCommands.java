package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class WorkScheduleStateCommands {
	private GeneralDate date;
	private int scheduleItemId;
	private int scheduleEditState;
}
