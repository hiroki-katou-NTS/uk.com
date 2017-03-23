/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.item.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

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

		List<RangeItem> items = new ArrayList<>();

		// Get min step
		BigDecimal minStep = this.getMinUnit(lowerLimit, upperLimit, interval);

		int index = 0;
		BigDecimal start = lowerLimit;
		while (start.compareTo(upperLimit) <= 0) {
			index++;
			BigDecimal end = start.add(interval).subtract(minStep);

			items.add(new RangeItem(index, start.doubleValue(),
					((end.compareTo(upperLimit) <= 0) ? end : upperLimit).doubleValue(),
					new ElementId(IdentifierUtil.randomUniqueId())));

			start = start.add(interval);
		}

		return items;
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
