package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
