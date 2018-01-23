package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class InsertPatternCommand {
	private String workplaceId;
	private int groupNo;
	private String groupName;
	private int groupUsageAtr;
	private String note;
	private List<InsertPatternItemCommand> listInsertPatternItemCommand;
}
