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
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHistoryService;

/**
 * The Class WageTableHistoryServiceImpl.
 */
@Stateless
public class WageTableHistoryServiceImpl extends WageTableHistoryService {

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WageTableHistoryRepository wageTableHistoryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.
	 * WageTableHistoryService#validateRequiredItem(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.WageTableHistory)
	 */
	public void validateRequiredItem(WageTableHistory history) {
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
	public void validateDateRange(WageTableHistory history) {
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
	public void checkDuplicateCode(WageTableHistory unitPriceHistory) {
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
	public SimpleHistoryRepository<WageTableHistory> getRepository() {
		return this.wageTableHistoryRepo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService#
	 * createInitalHistory(java.lang.String, java.lang.String)
	 */
	@Override
	public WageTableHistory createInitalHistory(String companyCode, String masterCode,
			YearMonth startYearMonth) {
		return WageTableHistory.createWithIntial(new CompanyCode(companyCode),
				new WageTableCode(masterCode), startYearMonth);
	}
}
