/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.HistoryAccidentInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class HistoryAccidentInsuranceRateF‌inder.
 */
@Stateless
public class HistoryAccidentInsuranceRateF‌inder {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<HistoryAccidentInsuranceRateFindOutDto> findAll() {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call repository find
		List<HistoryAccidentInsuranceRateFindOutDto> lstHistoryAccidentInsuranceRateFindOutDto = accidentInsuranceRateRepository
				.findAll(companyCode).stream().map(accidentInsuranceRate -> {
					HistoryAccidentInsuranceRateFindOutDto historyAccidentInsuranceRateFindOutDto;
					historyAccidentInsuranceRateFindOutDto = new HistoryAccidentInsuranceRateFindOutDto();
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
	 * @return the history accident insurance rate find out dto
	 */
	public HistoryAccidentInsuranceRateFindOutDto find(String historyId) {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call repository find
		HistoryAccidentInsuranceRateFindOutDto historyAccidentInsuranceRateFindOutDto;
		historyAccidentInsuranceRateFindOutDto = new HistoryAccidentInsuranceRateFindOutDto();
		Optional<AccidentInsuranceRate> optionalAccidentInsuranceRate = accidentInsuranceRateRepository
				.findById(companyCode, historyId);
		if (optionalAccidentInsuranceRate.isPresent()) {
			optionalAccidentInsuranceRate.get().saveToMemento(historyAccidentInsuranceRateFindOutDto);
		}
		return historyAccidentInsuranceRateFindOutDto;
	}

}
