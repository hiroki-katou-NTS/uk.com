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
public class DailyPerformanceAuthority extends AggregateRoot{

	/**
	 * ロールID
	 */
	// タイプID	
	private String roleID;
	
	/**
	 * 利用区分
	 */
	private boolean availability;
	
	/**
	 * 日別実績の機能NO
	 */
	private DailyPerformanceFunctionNo functionNo;
	
	public DailyPerformanceAuthority(String roleId, boolean availability, BigDecimal functionNo) {
		this.roleID = roleId;
		this.availability = availability;
		this.functionNo = new DailyPerformanceFunctionNo(functionNo);
	}
	
}
