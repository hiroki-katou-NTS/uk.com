/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto.UnemployeeInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UnemployeeInsuranceFinder.
 */
@Stateless
@Transactional
public class UnemployeeInsuranceFinder {

	/** The find. */
	@Inject
	private UnemployeeInsuranceRateRepository find;

	/**
	 * Find by id.
	 *
	 * @param companyCode
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @return the unemployee insurance rate dto
	 */
	public UnemployeeInsuranceRateFindOutDto findById(String historyId) {
		UnemployeeInsuranceRateFindOutDto unemployeeInsuranceRateFindOutDto = new UnemployeeInsuranceRateFindOutDto();
		Optional<UnemployeeInsuranceRate> optionalUnemployeeInsuranceRate = find
				.findById(new CompanyCode(AppContexts.user().companyCode()), historyId);
		if (optionalUnemployeeInsuranceRate.isPresent()) {
			optionalUnemployeeInsuranceRate.get().saveToMemento(unemployeeInsuranceRateFindOutDto);
			return unemployeeInsuranceRateFindOutDto;
		}
		return null;
	}

}
