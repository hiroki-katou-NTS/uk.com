package nts.uk.ctx.at.schedule.app.command.shift.workpairpattern;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class InsertWorkPairSetCommand {
	private int pairNo;
	private String workTypeCode;
	private String workTimeCode;
}
