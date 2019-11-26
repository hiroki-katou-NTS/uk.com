package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 要素範囲設定
 */
@Getter
@AllArgsConstructor
public class ElementRangeSetting extends AggregateRoot {

	/**
	 * 第一要素範囲
	 */
	private ElementRange firstElementRange;

	/**
	 * 第二要素範囲
	 */
	private Optional<ElementRange> secondElementRange;

	/**
	 * 第三要素範囲
	 */
	private Optional<ElementRange> thirdElementRange;

	/**
	 * 履歴ID
	 */
	private String historyID;

	public ElementRangeSetting(BigDecimal firstStepIncrement, BigDecimal firstRangeLowerLimit,
			BigDecimal firstRangeUpperLimit, BigDecimal secondStepIncrement, BigDecimal secondRangeLowerLimit,
			BigDecimal secondRangeUpperLimit, BigDecimal thirdStepIncrement, BigDecimal thirdRangeLowerLimit,
			BigDecimal thirdRangeUpperLimit, String historyID) {
		this.firstElementRange = new ElementRange(firstStepIncrement, firstRangeLowerLimit, firstRangeUpperLimit);
		this.secondElementRange = Optional
				.of(new ElementRange(secondStepIncrement, secondRangeLowerLimit, secondRangeUpperLimit));
		this.thirdElementRange = Optional
				.of(new ElementRange(thirdStepIncrement, thirdRangeLowerLimit, thirdRangeUpperLimit));
		this.historyID = historyID;
	}

	public ElementRangeSetting(ElementRange firstElementRange, ElementRange secondElementRange,
			ElementRange thirdElementRange, String historyID) {
		this.firstElementRange = firstElementRange;
		this.thirdElementRange = Optional.ofNullable(thirdElementRange);
		this.secondElementRange = Optional.ofNullable(secondElementRange);
		this.historyID = historyID;
	}

}
