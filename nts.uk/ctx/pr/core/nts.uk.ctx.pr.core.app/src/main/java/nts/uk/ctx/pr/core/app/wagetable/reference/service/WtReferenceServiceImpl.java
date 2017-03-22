/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.reference.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;

/**
 * The Class WtReferenceServiceImpl.
 */
@Stateless
public class WtReferenceServiceImpl implements WtReferenceService {

	/** The certify group repository. */
	// @Inject
	// private CertifyGroupRepository certifyGroupRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.app.wagetable.reference.service.WtReferenceService#
	 * generateCodeItems(nts.uk.ctx.pr.core.dom.wagetable.ElementType,
	 * nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo)
	 */
	@Override
	public List<CodeItem> generateCodeItems(ElementType type, WtElementRefNo refNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.app.wagetable.reference.service.WtReferenceService#
	 * generateRangeItems(java.math.BigDecimal, java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public List<RangeItem> generateRangeItems(BigDecimal lowerLimit, BigDecimal upperLimit,
			BigDecimal interval) {
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

	/**
	 * Gets the min step.
	 *
	 * @param nums
	 *            the nums
	 * @return the min step
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
	 * Gets the min step.
	 *
	 * @param num
	 *            the num
	 * @return the min step
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
