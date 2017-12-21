package nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace;

import lombok.Data;

/**
 * The Class WorkplaceMonthDaySettingRemoveCommand.
 */
@Data
public class WorkplaceMonthDaySettingRemoveCommand {
	/** The year. */
	private int year;
	
	/** The workplace id. */
	private String workplaceId;
}
