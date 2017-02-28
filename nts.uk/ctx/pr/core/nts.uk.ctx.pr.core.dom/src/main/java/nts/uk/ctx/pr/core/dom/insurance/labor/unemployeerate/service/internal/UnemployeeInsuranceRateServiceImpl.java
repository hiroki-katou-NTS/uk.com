/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.UnemployeeInsuranceRateService;

/**
 * The Class UnemployeeInsuranceRateServiceImpl.
 */
@Stateless
public class UnemployeeInsuranceRateServiceImpl implements UnemployeeInsuranceRateService {

	/** The unemployee insurance rate repo. */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.
	 * UnemployeeInsuranceRateService#validateRequiredItem(nts.uk.ctx.pr.core.
	 * dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void validateRequiredItem(UnemployeeInsuranceRate rate) {
		if (rate.getApplyRange() == null) {
			throw new BusinessException("ER001");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.
	 * UnemployeeInsuranceRateService#validateDateRange(nts.uk.ctx.pr.core.dom.
	 * insurance.labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void validateDateRange(UnemployeeInsuranceRate rate) {
		if (unemployeeInsuranceRateRepo.isInvalidDateRange(rate.getCompanyCode(),
				rate.getApplyRange())) {
			// History after start date and time exists
			throw new BusinessException("ER010");

			// TODO: Check duplicate start date. !? in EAP file.
			// throw new BusinessException("ER005");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.
	 * UnemployeeInsuranceRateService#validateDateRangeUpdate(nts.uk.ctx.pr.core
	 * .dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate)
	 */
	@Override
	public void validateDateRangeUpdate(UnemployeeInsuranceRate rate) {
		if (unemployeeInsuranceRateRepo.isInvalidDateRangeUpdate(rate.getCompanyCode(),
				rate.getApplyRange(), rate.getHistoryId())) {
			// History after start date and time exists
			throw new BusinessException("ER010");

			// TODO: Check duplicate start date. !? in EAP file.
			// throw new BusinessException("ER005");
		}

	}

}
