/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
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
		if (StringUtil.isNullOrEmpty(rate.getHistoryId(), true) || rate.getApplyRange() == null
			|| CollectionUtil.isEmpty(rate.getRateItems()) || rate.getRateItems().size() != RATE_ITEM_COUNT) {
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
	private boolean getValidateRange(AccidentInsuranceRate rate) {
		// validate Add
		// ? (start <= end)
		if (rate.getApplyRange().getStartMonth().v() > rate.getApplyRange().getEndMonth().v()) {
			return true;
		}

		// ? start > start first (order by desc)
		Optional<AccidentInsuranceRate> optionalFirst = this.accidentInsuranceRateRepo
			.findFirstData(rate.getCompanyCode());

		if (optionalFirst.isPresent()) {
			if (optionalFirst.get().getApplyRange().getStartMonth().nextMonth().v() > rate.getApplyRange()
				.getStartMonth().v()) {
				return true;
			}
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.
	 * AccidentInsuranceRateService#validateDateRangeUpdate(nts.uk.ctx.pr.core.
	 * dom.insurance.labor.accidentrate.AccidentInsuranceRate)
	 */
	@Override
	public void validateDateRangeUpdate(AccidentInsuranceRate rate) {
		if (getValidateRangeUpdate(rate)) {
			throw new BusinessException("ER023");
		}
	}

	/**
	 * Gets the validate range update.
	 *
	 * @param rate
	 *            the rate
	 * @return the validate range update
	 */
	private boolean getValidateRangeUpdate(AccidentInsuranceRate rate) {
		// start<=end
		if (rate.getApplyRange().getStartMonth().v() > rate.getApplyRange().getEndMonth().v()) {
			return true;
		}
		// data is begin update
		Optional<AccidentInsuranceRate> optionalAccidentInsuranceRate;
		optionalAccidentInsuranceRate = this.accidentInsuranceRateRepo.findById(rate.getCompanyCode(),
			rate.getHistoryId());

		if (!optionalAccidentInsuranceRate.isPresent()) {
			return true;
		}

		Optional<AccidentInsuranceRate> optionalBetweenUpdate = this.accidentInsuranceRateRepo
			.findBetweenUpdate(rate.getCompanyCode(),
				optionalAccidentInsuranceRate.get().getApplyRange().getStartMonth(),
				optionalAccidentInsuranceRate.get().getHistoryId());

		if (!optionalBetweenUpdate.isPresent()) {
			return false;
		}

		if (optionalBetweenUpdate.get().getApplyRange().getStartMonth().v() >= rate.getApplyRange()
			.getStartMonth().v()) {
			return true;
		}

		return false;
	}

}
