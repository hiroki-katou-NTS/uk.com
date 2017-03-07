/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto.HistoryUnemployeeInsuranceFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HistoryUnemployeeInsuranceFinder.
 */
@Stateless
public class HistoryUnemployeeInsuranceFinder {

	/** The unemployee insurance rate repository. */
	@Inject
	private UnemployeeInsuranceRateRepository find;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<HistoryUnemployeeInsuranceFindOutDto> findAll() {
		List<HistoryUnemployeeInsuranceFindOutDto> lstHistoryUnemployeeInsurance = new ArrayList<>();
		for (UnemployeeInsuranceRate unemployeeInsuranceRate : find
				.findAll(new CompanyCode(AppContexts.user().companyCode()))) {
			HistoryUnemployeeInsuranceFindOutDto historyUnemployeeInsuranceFindOutDto = new HistoryUnemployeeInsuranceFindOutDto();
			unemployeeInsuranceRate.saveToMemento(historyUnemployeeInsuranceFindOutDto);
			lstHistoryUnemployeeInsurance.add(historyUnemployeeInsuranceFindOutDto);
		}
		return lstHistoryUnemployeeInsurance;
	}

	/**
	 * Find.
	 *
	 * @param historyId the history id
	 * @return the history unemployee insurance find out dto
	 */
	public HistoryUnemployeeInsuranceFindOutDto find(String historyId) {
		HistoryUnemployeeInsuranceFindOutDto historyUnemployeeInsuranceFindOutDto = new HistoryUnemployeeInsuranceFindOutDto();
		Optional<UnemployeeInsuranceRate> optionalUnemployeeInsuranceRate = find
				.findById(new CompanyCode(AppContexts.user().companyCode()), historyId);
		if (optionalUnemployeeInsuranceRate.isPresent()) {
			optionalUnemployeeInsuranceRate.get().saveToMemento(historyUnemployeeInsuranceFindOutDto);
			return historyUnemployeeInsuranceFindOutDto;
		}
		return null;

	}
}
