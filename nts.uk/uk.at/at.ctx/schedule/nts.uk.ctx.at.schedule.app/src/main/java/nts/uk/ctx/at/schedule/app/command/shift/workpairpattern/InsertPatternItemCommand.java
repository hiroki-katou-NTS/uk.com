package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class InsertPatternItemCommand {
	private int patternNo;
	private String patternName;
	private List<InsertWorkPairSetCommand> listInsertWorkPairSetCommand;
}
