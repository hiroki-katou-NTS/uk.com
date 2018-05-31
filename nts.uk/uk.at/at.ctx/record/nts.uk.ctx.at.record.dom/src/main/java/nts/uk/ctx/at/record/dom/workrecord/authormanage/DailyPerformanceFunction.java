/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤務実績の機能
 * @author danpv
 *
 */
@Getter
public class DailyPerformanceFunction extends AggregateRoot {

	/**
	 * 利用可否権限の機能NO
	 */
	private DailyPerformanceFunctionNo functionNo;

	/**
	 * 利用可否権限の機能名
	 */
	private FeatureNameOfDailyPerformance displayName;

	/**
	 * 利用できる／できない権限の機能説明文
	 */
	private FeatureDescriptionOfDailyPerformance description;
	/**
	 * 表示順
	 */
	private int displayOrder;
	/**
	 * 初期値
	 */
	private boolean initValue;

	public DailyPerformanceFunction(BigDecimal functionNo, String displayName, String description, int displayOrder, boolean initValue) {
		this.functionNo = new DailyPerformanceFunctionNo(functionNo);
		this.displayName = new FeatureNameOfDailyPerformance(displayName);
		this.description = new FeatureDescriptionOfDailyPerformance(description);
		this.displayOrder = displayOrder;
		this.initValue = initValue;
	}

}
