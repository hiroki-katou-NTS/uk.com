/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.service.internal;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;
import nts.uk.shr.com.context.AppContexts;

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
		if (!wageTableHistoryRepo.isValidDateRange(history.getCompanyCode(),
				history.getWageTableCode().v(), history.getApplyRange().getStartMonth().v())) {
			// History after start date and time exists
			throw new BusinessException("ER010");
		}
	}

	/**
	 * Delete history.
	 *
	 * @param uuid
	 *            the uuid
	 */
	@Override
	public void deleteHistory(String uuid) {
		WtHistory history = this.wageTableHistoryRepo.findHistoryByUuid(uuid).get();
		List<WtHistory> unitPriceHistoryList = this.wageTableHistoryRepo.findAllHistoryByMasterCode(
				AppContexts.user().companyCode(), history.getMasterCode().v());

		super.deleteHistory(uuid);

		// Remove unit price.
		if (!CollectionUtil.isEmpty(unitPriceHistoryList) && unitPriceHistoryList.size() == 1) {
			this.wageTableHeadRepo.remove(history.getCompanyCode(), history.getMasterCode().v());
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
		if (wageTableHeadRepo.isExistCode(unitPriceHistory.getCompanyCode(),
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
		return WtHistory.createWithIntial(companyCode, new WtCode(masterCode), startYearMonth);
	}
}
