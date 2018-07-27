/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤務実績の権限
 * @author danpv
 *
 */
@Getter
public class DailyPerformanceAuthority extends AggregateRoot {
	
	private String companyId;
	
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

	public DailyPerformanceAuthority(String companyId, String roleId, BigDecimal functionNo, boolean availability) {
		this.companyId = companyId;
		this.roleID = roleId;
		this.functionNo = new DailyPerformanceFunctionNo(functionNo);
		this.availability = availability;
	}

}
