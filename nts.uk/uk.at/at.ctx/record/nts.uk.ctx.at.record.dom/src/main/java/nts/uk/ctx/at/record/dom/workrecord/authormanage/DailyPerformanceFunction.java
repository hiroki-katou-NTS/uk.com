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
public class DailyPerformanceFunction extends AggregateRoot {

	/**
	 * 日別実績の機能NO
	 */
	private DailyPerformanceFunctionNo functionNo;

	/**
	 * 日別実績の機能名
	 */
	private FeatureNameOfDailyPerformance displayName;

	/**
	 * 日別実績の機能説明文
	 */
	private FeatureDescriptionOfDailyPerformance description;

	public DailyPerformanceFunction(BigDecimal functionNo, String displayName, String description) {
		this.functionNo = new DailyPerformanceFunctionNo(functionNo);
		this.displayName = new FeatureNameOfDailyPerformance(displayName);
		this.description = new FeatureDescriptionOfDailyPerformance(description);
	}

}
