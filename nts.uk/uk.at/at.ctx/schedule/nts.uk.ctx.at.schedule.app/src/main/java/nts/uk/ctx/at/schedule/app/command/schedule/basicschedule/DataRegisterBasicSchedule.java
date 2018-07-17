package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class DataRegisterBasicSchedule {
	private int modeDisplay;
	private List<RegisterBasicScheduleCommand> listRegisterBasicSchedule;
}
