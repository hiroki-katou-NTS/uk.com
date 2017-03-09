/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.AccidentInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class HistoryAccidentInsuranceRateF‌inder.
 */
@Stateless
public class AccidentInsuranceRateF‌inder {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	/** The insurance business type repository. */
	@Inject
	private InsuranceBusinessTypeRepository insuranceBusinessTypeRepository;

	/**
	 * Find.
	 *
	 * @param historyAccidentInsuranceRateFindInDto
	 *            the history accident insurance rate find in dto
	 * @return the history accident insurance rate dto
	 */
	public AccidentInsuranceRateFindOutDto find(String historyId) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call repository finder
		AccidentInsuranceRateFindOutDto accidentInsuranceRateFindOutDto;
		accidentInsuranceRateFindOutDto = new AccidentInsuranceRateFindOutDto();
		Optional<AccidentInsuranceRate> optionalAccidentInsuranceRate = accidentInsuranceRateRepository
				.findById(new CompanyCode(companyCode), historyId);
		if (optionalAccidentInsuranceRate.isPresent()) {
			optionalAccidentInsuranceRate.get().saveToMemento(accidentInsuranceRateFindOutDto);
		}
		return accidentInsuranceRateFindOutDto;
	}

}
