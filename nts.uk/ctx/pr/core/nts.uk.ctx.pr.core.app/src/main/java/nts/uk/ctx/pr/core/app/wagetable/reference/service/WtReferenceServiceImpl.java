/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.reference.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.reference.service.dto.EleHistItemDto;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefItem;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefRepository;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRefRepository;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository;

/**
 * The Class WtReferenceServiceImpl.
 */
@Stateless
public class WtReferenceServiceImpl implements WtReferenceService {

	/** The wt reference repo. */
	@Inject
	private WtReferenceRepository wtReferenceRepo;

	/** The wt code ref repo. */
	@Inject
	private WtCodeRefRepository wtCodeRefRepo;

	/** The wt master ref repo. */
	@Inject
	private WtMasterRefRepository wtMasterRefRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.app.wagetable.reference.service.WtReferenceService#
	 * generateCodeItems(nts.uk.ctx.pr.core.dom.wagetable.ElementType,
	 * nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo)
	 */
	@Override
	public List<EleHistItemDto> generateCodeItems(CompanyCode companyCode, ElementType type,
			WtElementRefNo refNo, List<CodeItem> codeItems) {

		Map<String, ElementId> mapCodeItems = codeItems.stream()
				.collect(Collectors.toMap(CodeItem::getReferenceCode, CodeItem::getUuid));

		List<WtCodeRefItem> wtRefItems = null;

		switch (type) {
		case CODE_REF:
			Optional<WtCodeRef> optCodeRef = this.wtCodeRefRepo.findByCode(companyCode.v(),
					refNo.v());
			wtRefItems = this.wtReferenceRepo.getCodeRefItem(optCodeRef.get());
			break;

		case MASTER_REF:
			Optional<WtMasterRef> optMasterRef = this.wtMasterRefRepo.findByCode(companyCode.v(),
					refNo.v());
			wtRefItems = this.wtReferenceRepo.getMasterRefItem(optMasterRef.get());
			break;

		case CERTIFICATION:
			wtRefItems = this.wtReferenceRepo.getCertifyRefItem(companyCode.v());
			break;

		case LEVEL:

		default:
			return Collections.emptyList();
		}

		return wtRefItems
				.stream().map(
						item -> EleHistItemDto
								.builder().uuid(
										mapCodeItems
												.getOrDefault(item.getReferenceCode(),
														new ElementId(
																IdentifierUtil.randomUniqueId()))
												.v())
								.referenceCode(item.getReferenceCode())
								.displayName(item.getDisplayName()).build())
				.collect(Collectors.toList());

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
	public List<EleHistItemDto> generateRangeItems(BigDecimal lowerLimit, BigDecimal upperLimit,
			BigDecimal interval, List<RangeItem> rangeItems) {
		List<EleHistItemDto> items = new ArrayList<>();
		Map<RangeItem, ElementId> mapRangeItems = rangeItems.stream()
				.collect(Collectors.toMap(Function.identity(), RangeItem::getUuid));
		// Get min step
		BigDecimal minStep = this.getMinUnit(lowerLimit, upperLimit, interval);
		int index = 0;
		BigDecimal start = lowerLimit;
		while (start.compareTo(upperLimit) <= 0) {
			index++;
			BigDecimal end = start.add(interval).subtract(minStep);

			RangeItem rangeItem = new RangeItem(index, start.doubleValue(),
					((end.compareTo(upperLimit) <= 0) ? end : upperLimit).doubleValue(),
					new ElementId(IdentifierUtil.randomUniqueId()));

			items.add(EleHistItemDto.builder()
					.uuid(mapRangeItems.getOrDefault(rangeItem, rangeItem.getUuid()).v())
					.orderNumber(rangeItem.getOrderNumber()).startVal(rangeItem.getStartVal())
					.endVal(rangeItem.getEndVal()).build());

			start = start.add(interval);
		}

		return items;
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
