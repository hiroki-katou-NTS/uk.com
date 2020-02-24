package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import lombok.Value;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class RankCommand {
	
	/**
	 * ランクコード
	 */
	private String rankCd;
	
	/**
	 * ランク記号
	 */
	private String rankSymbol;
	
	/**
	 * 優先順
	 */
	private int priority;

}
