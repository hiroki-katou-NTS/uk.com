package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@Value
public class RegisterBasicScheduleCommand {
	private String employeeId;
	private GeneralDate date;
	private String workTypeCd;
	private String workTimeCd;
}
