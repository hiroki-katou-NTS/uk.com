/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.history.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.AccidentInsuranceRateHistoryFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceHistoryF‌inder.
 */
@Stateless
public class AccidentInsuranceHistoryF‌inder {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AccidentInsuranceRateHistoryFindOutDto> findAll() {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call repository find
		List<AccidentInsuranceRateHistoryFindOutDto> lstHistoryAccidentInsuranceRateFindOutDto;
		lstHistoryAccidentInsuranceRateFindOutDto = accidentInsuranceRateRepository.findAll(companyCode)
			.stream().map(accidentInsuranceRate -> {
				AccidentInsuranceRateHistoryFindOutDto historyAccidentInsuranceRateFindOutDto;
				historyAccidentInsuranceRateFindOutDto = new AccidentInsuranceRateHistoryFindOutDto();
				accidentInsuranceRate.saveToMemento(historyAccidentInsuranceRateFindOutDto);
				return historyAccidentInsuranceRateFindOutDto;
			}).collect(Collectors.toList());
		return lstHistoryAccidentInsuranceRateFindOutDto;
	}

	/**
	 * Find.
	 *
	 * @param historyId
	 *            the history id
	 * @return the accident insurance rate history find out dto
	 */
	public AccidentInsuranceRateHistoryFindOutDto find(String historyId) {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call repository find
		AccidentInsuranceRateHistoryFindOutDto historyAccidentInsuranceRateFindOutDto;
		historyAccidentInsuranceRateFindOutDto = new AccidentInsuranceRateHistoryFindOutDto();
		Optional<AccidentInsuranceRate> optionalAccidentInsuranceRate = accidentInsuranceRateRepository
			.findById(companyCode, historyId);
		if (optionalAccidentInsuranceRate.isPresent()) {
			optionalAccidentInsuranceRate.get().saveToMemento(historyAccidentInsuranceRateFindOutDto);
		}
		return historyAccidentInsuranceRateFindOutDto;
	}

}
