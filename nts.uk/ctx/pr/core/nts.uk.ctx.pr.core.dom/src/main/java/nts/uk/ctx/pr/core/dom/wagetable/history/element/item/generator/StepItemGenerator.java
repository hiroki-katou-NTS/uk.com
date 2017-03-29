/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.item.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.StepElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.Item;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;

/**
 * The Class StepItemGenerator.
 */
@Stateless
public class StepItemGenerator implements ItemGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.element.item.generator.
	 * ItemGenerator#generate(nts.uk.ctx.pr.core.dom.wagetable.history.element.
	 * ElementSetting)
	 */
	@Override
	public List<? extends Item> generate(String companyCode, String historyId,
			ElementSetting elementSetting) {
		StepElementSetting stepElementSetting = (StepElementSetting) elementSetting;
		BigDecimal lowerLimit = stepElementSetting.getLowerLimit().v();
		BigDecimal upperLimit = stepElementSetting.getUpperLimit().v();
		BigDecimal interval = stepElementSetting.getInterval().v();

		// Get min step
		BigDecimal minStep = this.getMinUnit(lowerLimit, upperLimit, interval);

		// Lower limit is always less than upper limit.
		if (upperLimit.compareTo(lowerLimit) < 0) {
			// TODO: need msg id.
			throw new BusinessException("Lower limit must be always less than upper limit.");
		}

		// Interval is greater than zero.
		if (interval.compareTo(BigDecimal.ZERO) <= 0) {
			// TODO: need msg id.
			throw new BusinessException("Interval must be greater than zero.");
		}

		// Interval is invalid
		if (upperLimit.subtract(lowerLimit).add(minStep).doubleValue() < interval.doubleValue()) {
			// TODO: need msg id.
			throw new BusinessException(
					"The range " + lowerLimit + " - " + upperLimit + " is not enough for 1 step");
		}

		@SuppressWarnings("unchecked")
		List<RangeItem> rangeItems = (List<RangeItem>) elementSetting.getItemList();
		Map<String, ElementId> mapRangeItems = rangeItems.stream()
				.collect(Collectors.toMap(item -> this.getUniqueCode(item), RangeItem::getUuid));

		List<RangeItem> items = new ArrayList<>();

		int index = 0;
		BigDecimal start = lowerLimit;
		while (start.compareTo(upperLimit) <= 0) {
			index++;
			BigDecimal end = start.add(interval).subtract(minStep);

			RangeItem rangeItem = new RangeItem(index, start,
					((end.compareTo(upperLimit) <= 0) ? end : upperLimit),
					new ElementId(IdentifierUtil.randomUniqueId()));

			// Replace exist element id.
			rangeItem = new RangeItem(index, start,
					((end.compareTo(upperLimit) <= 0) ? end : upperLimit),
					mapRangeItems.getOrDefault(this.getUniqueCode(rangeItem), rangeItem.getUuid()));
			rangeItem.setDisplayName(rangeItem.getStartVal() + "～" + rangeItem.getEndVal());

			items.add(rangeItem);

			start = start.add(interval);
		}

		return items;
	}

	/**
	 * Gets the unique code.
	 *
	 * @param rangeItem
	 *            the range item
	 * @return the unique code
	 */
	private String getUniqueCode(RangeItem rangeItem) {
		return String.format("%s:%s:%s", rangeItem.getOrderNumber().toString(),
				rangeItem.getStartVal().toString(), rangeItem.getEndVal().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.element.item.generator.
	 * ItemGenerator#canHandle(nts.uk.ctx.pr.core.dom.wagetable.ElementType)
	 */
	@Override
	public boolean canHandle(ElementType type) {
		return type.isRangeMode;
	}

	/**
	 * Gets the min unit.
	 *
	 * @param nums
	 *            the nums
	 * @return the min unit
	 */
	private BigDecimal getMinUnit(BigDecimal... nums) {
		BigDecimal minStep = BigDecimal.valueOf(1d);
		for (BigDecimal num : nums) {
			BigDecimal step = this.getUnit(num);
			if (step.compareTo(minStep) == -1) {
				minStep = step;
			}
		}
		return minStep;
	}

	/**
	 * Gets the unit.
	 *
	 * @param num
	 *            the num
	 * @return the unit
	 */
	private BigDecimal getUnit(BigDecimal num) {
		num = num.multiply(BigDecimal.valueOf(100d));

		if (BigDecimal.ZERO.intValue() == num.remainder(BigDecimal.valueOf(100d)).intValue()) {
			return BigDecimal.ONE;
		}

		if (BigDecimal.ZERO.intValue() == num.remainder(BigDecimal.valueOf(10d)).intValue()) {
			return BigDecimal.valueOf(0.1d);
		}

		return BigDecimal.valueOf(0.01d);
	}
}
