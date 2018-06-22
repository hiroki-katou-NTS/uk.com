package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import lombok.Data;
import lombok.Setter;

/**
 * The Class AttendanceTobeDisplayCommand.
 */
@Data
@Setter
public class AttendanceTobeDisplayCommand {

	/** The sort by. */
	private int sortBy;

	/** The item to display. */
	private int itemToDisplay;
}
