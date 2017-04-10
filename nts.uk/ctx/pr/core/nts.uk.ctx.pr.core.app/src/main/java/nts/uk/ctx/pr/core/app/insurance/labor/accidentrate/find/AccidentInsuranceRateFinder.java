/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.AccidentInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class HistoryAccidentInsuranceRateF‌inder.
 */
@Stateless
public class AccidentInsuranceRateFinder {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

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
		// find by id
		Optional<AccidentInsuranceRate> optionalAccidentInsuranceRate = accidentInsuranceRateRepo
				.findById(companyCode, historyId);

		//exist data finder
		if (optionalAccidentInsuranceRate.isPresent()) {
			optionalAccidentInsuranceRate.get().saveToMemento(accidentInsuranceRateFindOutDto);
		}
		// respone => .....
		return accidentInsuranceRateFindOutDto;
	}

}
