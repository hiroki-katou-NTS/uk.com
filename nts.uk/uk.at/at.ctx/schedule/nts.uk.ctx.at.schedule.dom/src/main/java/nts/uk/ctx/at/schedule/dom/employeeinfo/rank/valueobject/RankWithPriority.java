package nts.uk.ctx.at.schedule.dom.employeeinfo.rank.valueobject;

import lombok.Value;

/**
 * ランク
 * 
 * @author hieult
 *
 */
@Value
public class RankWithPriority {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * コード
	 */
	private String rankCode;
	/**
	 * 記号
	 */
	private String rankSymbol;
	
	/** Priority */
	private int priority;
	
}
