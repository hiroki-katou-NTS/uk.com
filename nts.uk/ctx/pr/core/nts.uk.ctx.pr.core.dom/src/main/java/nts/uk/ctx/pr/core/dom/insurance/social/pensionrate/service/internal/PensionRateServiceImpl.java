/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;

/**
 * The Class PensionRateServiceImpl.
 */
@Stateless
public class PensionRateServiceImpl extends PensionRateService {

	/** The fund rate item count. */
	private final int FUND_RATE_ITEM_COUNT = 6;

	/** The rounding method count. */
	private final int ROUNDING_METHOD_COUNT = 2;

	/** The pension rate repo. */
	@Inject
	private PensionRateRepository pensionRateRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.
	 * PensionRateService#validateRequiredItem(nts.uk.ctx.pr.core.dom.insurance.
	 * social.pensionrate.PensionRate)
	 */
	@Override
	public void validateRequiredItem(PensionRate rate) {
		if (rate.getOfficeCode() == null || StringUtil.isNullOrEmpty(rate.getOfficeCode().v(), true)
				|| rate.getApplyRange() == null || rate.getMaxAmount() == null
				|| rate.getChildContributionRate() == null || ListUtil.isEmpty(rate.getFundRateItems())
				|| rate.getFundRateItems().size() != FUND_RATE_ITEM_COUNT || ListUtil.isEmpty(rate.getRoundingMethods())
				|| rate.getRoundingMethods().size() != ROUNDING_METHOD_COUNT) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.
	 * PensionRateService#validateDateRange(nts.uk.ctx.pr.core.dom.insurance.
	 * social.pensionrate.PensionRate)
	 */
	@Override
	public void validateDateRange(PensionRate rate) {
		if (pensionRateRepo.isInvalidDateRange(rate.getApplyRange())) {
			// History after start date and time exists
			// TODO throw new BusinessException("ER011"); ER0123!?
			throw new BusinessException("ER011");
		}
	}

	@Override
	public SimpleHistoryRepository<PensionRate> getRepository() {
		return this.pensionRateRepo;
	}

	@Override
	public PensionRate createInitalHistory(String companyCode, String officeCode, YearMonth startTime) {
		return PensionRate.createWithIntial(new CompanyCode(companyCode), new OfficeCode(officeCode),
				startTime);
	}

}
