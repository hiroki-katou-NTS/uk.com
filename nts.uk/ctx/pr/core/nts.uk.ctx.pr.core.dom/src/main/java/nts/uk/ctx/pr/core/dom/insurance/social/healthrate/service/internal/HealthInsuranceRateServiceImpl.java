/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.ListUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;

/**
 * The Class HealthInsuranceRateServiceImpl.
 */
@Stateless
public class HealthInsuranceRateServiceImpl implements HealthInsuranceRateService {

	/** The Constant INSURANCE_RATE_ITEM_COUNT. */
	private static final int INSURANCE_RATE_ITEM_COUNT = 8;

	/** The Constant HEALTH_INSURANCE_ROUNDING_COUNT. */
	private static final int HEALTH_INSURANCE_ROUNDING_COUNT = 2;

	/** The health insurance rate repo. */
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.
	 * HealthInsuranceRateService#validateRequiredItem(nts.uk.ctx.pr.core.dom.
	 * insurance.social.healthrate.HealthInsuranceRate)
	 */
	@Override
	public void validateRequiredItem(HealthInsuranceRate rate) {
		if (StringUtil.isNullOrEmpty(rate.getOfficeCode().v(), true) || rate.getApplyRange() == null
				|| rate.getMaxAmount() == null || ListUtil.isEmpty(rate.getRateItems())
				|| rate.getRateItems().size() != INSURANCE_RATE_ITEM_COUNT
				|| ListUtil.isEmpty(rate.getRoundingMethods())
				|| rate.getRoundingMethods().size() != HEALTH_INSURANCE_ROUNDING_COUNT) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.
	 * HealthInsuranceRateService#validateDateRange(nts.uk.ctx.pr.core.dom.
	 * insurance.social.healthrate.HealthInsuranceRate)
	 */
	@Override
	public void validateDateRange(HealthInsuranceRate rate) {
		if (healthInsuranceRateRepo.isInvalidDateRange(rate.getApplyRange())) {
			// History after start date and time exists
			// TODO throw new BusinessException("ER011"); ER0123!?
			throw new BusinessException("ER011");
		}
	}

}
