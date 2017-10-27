package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class RegisterBasicScheduleCommand {
	private String employeeId;
	private GeneralDate date;
	private String workTypeCode;
	private String workTimeCode;
}
