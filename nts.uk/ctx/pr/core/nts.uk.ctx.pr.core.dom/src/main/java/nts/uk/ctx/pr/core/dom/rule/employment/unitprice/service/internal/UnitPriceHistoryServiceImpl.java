/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.UnitPriceHistoryService;

/**
 * The Class UnitPriceHistoryServiceImpl.
 */
@Stateless
public class UnitPriceHistoryServiceImpl implements UnitPriceHistoryService {

	/** The unit price history repo. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * UnitPriceHistoryService#validateRequiredItem(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.UnitPriceHistory)
	 */
	@Override
	public void validateRequiredItem(UnitPriceHistory history) {
		if (history.getUnitPriceCode() == null || StringUtil.isNullOrEmpty(history.getUnitPriceCode().v(), true)
				|| history.getUnitPriceName() == null || StringUtil.isNullOrEmpty(history.getUnitPriceName().v(), true)
				|| history.getApplyRange() == null || history.getBudget() == null) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * UnitPriceHistoryService#validateDateRange(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.UnitPriceHistory)
	 */
	@Override
	public void validateDateRange(UnitPriceHistory unitPriceHistory) {
		if (unitPriceHistoryRepo.isInvalidDateRange(unitPriceHistory.getApplyRange().getStartMonth())) {
			// History after start date and time exists
			throw new BusinessException("ER010");
		}
	}

}
