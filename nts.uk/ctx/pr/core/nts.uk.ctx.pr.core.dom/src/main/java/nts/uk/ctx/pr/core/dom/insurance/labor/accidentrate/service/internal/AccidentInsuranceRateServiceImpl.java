/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.AccidentInsuranceRateService;

/**
 * The Class AccidentInsuranceRateServiceImpl.
 */
@Stateless
public class AccidentInsuranceRateServiceImpl implements AccidentInsuranceRateService {

	/** The rate item count. */
	private final int RATE_ITEM_COUNT = 10;

	/** The accident insurance rate repo. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.service.
	 * LaborInsuranceOfficeService#validateRequiredItem(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void validateRequiredItem(AccidentInsuranceRate rate) {
		if (rate.getApplyRange() == null || ListUtil.isEmpty(rate.getRateItems())
				|| rate.getRateItems().size() != RATE_ITEM_COUNT) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.
	 * AccidentInsuranceRateService#validateDateRange(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.accidentrate.AccidentInsuranceRate)
	 */
	@Override
	public void validateDateRange(AccidentInsuranceRate rate) {
		// Check consistency date range.
		// History after start date and time exists
		if (accidentInsuranceRateRepo.isInvalidDateRange(rate.getCompanyCode(), rate.getApplyRange())) {
			throw new BusinessException("ER010");
		}
	}

}
