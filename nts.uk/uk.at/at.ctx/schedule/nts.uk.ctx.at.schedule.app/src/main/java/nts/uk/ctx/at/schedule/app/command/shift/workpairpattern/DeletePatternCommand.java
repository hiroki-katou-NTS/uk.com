package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class DeletePatternCommand {
	private String workplaceId;
	private int groupNo;
}
