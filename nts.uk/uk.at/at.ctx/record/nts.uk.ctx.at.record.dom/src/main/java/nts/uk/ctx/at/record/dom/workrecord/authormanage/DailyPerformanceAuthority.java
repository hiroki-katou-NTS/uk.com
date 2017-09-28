/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author danpv
 *
 */
@Getter
public class DailyPerformanceAuthority extends AggregateRoot {

	/**
	 * ロールID
	 */
	// タイプID
	private String roleID;

	/**
	 * 日別実績の機能NO
	 */
	private DailyPerformanceFunctionNo functionNo;

	/**
	 * 利用区分
	 */
	private boolean availability;

	public DailyPerformanceAuthority(String roleId, BigDecimal functionNo, boolean availability) {
		this.roleID = roleId;
		this.functionNo = new DailyPerformanceFunctionNo(functionNo);
		this.availability = availability;
	}

}
