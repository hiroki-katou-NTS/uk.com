/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto.UnemployeeInsuranceHistoryFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class HistoryUnemployeeInsuranceFinder.
 */
@Stateless
public class UnemployeeInsuranceHistoryFinder {

	/** The unemployee insurance rate repository. */
	@Inject
	private UnemployeeInsuranceRateRepository find;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<UnemployeeInsuranceHistoryFindOutDto> findAll() {

		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();

		// call finder
		List<UnemployeeInsuranceHistoryFindOutDto> lstHistoryUnemployeeInsurance = find.findAll(companyCode)
			.stream().map(unemployeeInsuranceRate -> {
				UnemployeeInsuranceHistoryFindOutDto historyUnemployeeInsuranceFindOutDto;
				historyUnemployeeInsuranceFindOutDto = new UnemployeeInsuranceHistoryFindOutDto();
				unemployeeInsuranceRate.saveToMemento(historyUnemployeeInsuranceFindOutDto);
				return historyUnemployeeInsuranceFindOutDto;
			}).collect(Collectors.toList());

		return lstHistoryUnemployeeInsurance;
	}

	/**
	 * Find.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history unemployee insurance find out dto
	 */
	public UnemployeeInsuranceHistoryFindOutDto find(String historyId) {
		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// call finder
		UnemployeeInsuranceHistoryFindOutDto historyUnemployeeInsuranceFindOutDto;
		historyUnemployeeInsuranceFindOutDto = new UnemployeeInsuranceHistoryFindOutDto();
		Optional<UnemployeeInsuranceRate> optionalUnemployeeInsuranceRate = find.findById(companyCode,
			historyId);
		// exist value
		if (optionalUnemployeeInsuranceRate.isPresent()) {
			optionalUnemployeeInsuranceRate.get().saveToMemento(historyUnemployeeInsuranceFindOutDto);
			return historyUnemployeeInsuranceFindOutDto;
		}
		return null;

	}
}
