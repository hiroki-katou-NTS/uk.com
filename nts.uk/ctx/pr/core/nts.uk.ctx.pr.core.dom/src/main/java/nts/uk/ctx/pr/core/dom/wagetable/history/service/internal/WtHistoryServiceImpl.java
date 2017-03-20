/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;

/**
 * The Class WageTableHistoryServiceImpl.
 */
@Stateless
public class WtHistoryServiceImpl extends WtHistoryService {

	/** The wage table head repo. */
	@Inject
	private WtHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WtHistoryRepository wageTableHistoryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * WageTableHistoryService#validateRequiredItem(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.WageTableHistory)
	 */
	public void validateRequiredItem(WtHistory history) {
		// TODO: recode
		if (history.getWageTableCode() == null
				|| StringUtil.isNullOrEmpty(history.getWageTableCode().v(), true)
				|| history.getApplyRange() == null) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * WageTableHistoryService#validateDateRange(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.WageTableHistory)
	 */
	public void validateDateRange(WtHistory history) {
		if (!wageTableHistoryRepo.isValidDateRange(history.getCompanyCode().v(),
				history.getWageTableCode().v(), history.getApplyRange().getStartMonth().v())) {
			// History after start date and time exists
			throw new BusinessException("ER010");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * WageTableHistoryService#checkDuplicateCode(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.WageTableHistory)
	 */
	public void checkDuplicateCode(WtHistory unitPriceHistory) {
		if (wageTableHeadRepo.isExistCode(unitPriceHistory.getCompanyCode().v(),
				unitPriceHistory.getWageTableCode().v())) {
			throw new BusinessException("ER005");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService#
	 * getRepository()
	 */
	@Override
	public SimpleHistoryRepository<WtHistory> getRepository() {
		return this.wageTableHistoryRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService#
	 * createInitalHistory(java.lang.String, java.lang.String)
	 */
	@Override
	public WtHistory createInitalHistory(String companyCode, String masterCode,
			YearMonth startYearMonth) {
		return WtHistory.createWithIntial(new CompanyCode(companyCode),
				new WtCode(masterCode), startYearMonth);
	}
}
