/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.internal;

import java.util.Optional;

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
		if (getValidateRange(rate)) {
			throw new BusinessException("ER010");
		}
	}

	/**
	 * Gets the validate range.
	 *
	 * @param rate
	 *            the rate
	 * @return the validate range
	 */
	private boolean getValidateRange(UnemployeeInsuranceRate rate) {
		boolean resvalue = false;
		if (rate.getApplyRange().getStartMonth().v() > rate.getApplyRange().getEndMonth().v()) {
			resvalue = true;
		}

		Optional<UnemployeeInsuranceRate> optionalFirst = this.unemployeeInsuranceRateRepo
			.findFirstData(rate.getCompanyCode().v());

		if (optionalFirst.isPresent() && optionalFirst.get().getApplyRange().getStartMonth().nextMonth()
			.v() > rate.getApplyRange().getStartMonth().v()) {
			resvalue = true;
		}
		return resvalue;

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
		if (getValidateRangeUpdate(rate)) {
			throw new BusinessException("ER010");
		}
	}

	/**
	 * Gets the validate range update.
	 *
	 * @param rate
	 *            the rate
	 * @return the validate range update
	 */
	private boolean getValidateRangeUpdate(UnemployeeInsuranceRate rate) {

		boolean resvalue = false;
		// start<=end
		if (rate.getApplyRange().getStartMonth().v() > rate.getApplyRange().getEndMonth().v()) {
			resvalue = true;
		}
		if (!resvalue) {
			// data is begin update
			Optional<UnemployeeInsuranceRate> optionalUnemployeeInsuranceRate;
			optionalUnemployeeInsuranceRate = this.unemployeeInsuranceRateRepo
				.findById(rate.getCompanyCode().v(), rate.getHistoryId());
			if (!optionalUnemployeeInsuranceRate.isPresent()) {
				resvalue = true;
			}
			if (!resvalue) {
				// get first data update
				Optional<UnemployeeInsuranceRate> optionalBetweenUpdate = this.unemployeeInsuranceRateRepo
					.findBetweenUpdate(rate.getCompanyCode().v(),
						optionalUnemployeeInsuranceRate.get().getApplyRange().getStartMonth(),
						optionalUnemployeeInsuranceRate.get().getHistoryId());
				if (optionalBetweenUpdate.isPresent()) {
					if (optionalBetweenUpdate.get().getApplyRange().getStartMonth().v() >= rate
						.getApplyRange().getStartMonth().v()) {
						resvalue = true;
					}
				}
			}
		}

		return resvalue;
	}

}
