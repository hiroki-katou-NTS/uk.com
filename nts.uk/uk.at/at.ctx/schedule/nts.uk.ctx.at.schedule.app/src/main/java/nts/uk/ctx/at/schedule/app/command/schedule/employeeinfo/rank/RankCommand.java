package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class RankCommand {
	
	private String rankCd;
	
	private String rankSymbol;
	
	private int priority;

}
