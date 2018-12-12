package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 数値要素範囲
 */
@Getter
public class NumericElementRange extends DomainObject {
	/**
	 * きざみ単位
	 */
	private StepIncrement stepIncrement;

	/**
	 * 範囲下限
	 */
	private RangeLowerLimit rangeLowerLimit;

	/**
	 * 範囲上限
	 */
	private RangeUpperLimit rangeUpperLimit;

	public NumericElementRange(Integer stepIncrement, Integer rangeLowerLimit, Integer rangeUpperLimit) {
		this.stepIncrement = new StepIncrement(stepIncrement);
		this.rangeLowerLimit = new RangeLowerLimit(rangeLowerLimit);
		this.rangeUpperLimit = new RangeUpperLimit(rangeUpperLimit);
	}
}
