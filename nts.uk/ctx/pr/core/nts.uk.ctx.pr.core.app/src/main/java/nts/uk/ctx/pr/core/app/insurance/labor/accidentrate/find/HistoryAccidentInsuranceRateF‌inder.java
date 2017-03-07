/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.HistoryAccidentInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;

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
		List<HistoryAccidentInsuranceRateFindOutDto> lstHistoryAccidentInsuranceRateFindOutDto = new ArrayList<>();
		for (AccidentInsuranceRate accidentInsuranceRate : accidentInsuranceRateRepository
				.findAll(new CompanyCode(AppContexts.user().companyCode()))) {
			HistoryAccidentInsuranceRateFindOutDto historyAccidentInsuranceRateFindOutDto = new HistoryAccidentInsuranceRateFindOutDto();
			accidentInsuranceRate.saveToMemento(historyAccidentInsuranceRateFindOutDto);
			lstHistoryAccidentInsuranceRateFindOutDto.add(historyAccidentInsuranceRateFindOutDto);
		}
		return lstHistoryAccidentInsuranceRateFindOutDto;
	}

	/**
	 * Find.
	 *
	 * @param historyAccidentInsuranceRateFindInDto
	 *            the history accident insurance rate find in dto
	 * @return the history accident insurance rate dto
	 */
	public HistoryAccidentInsuranceRateFindOutDto find(String historyId) {
		HistoryAccidentInsuranceRateFindOutDto historyAccidentInsuranceRateFindOutDto = new HistoryAccidentInsuranceRateFindOutDto();
		AccidentInsuranceRate accidentInsuranceRate = accidentInsuranceRateRepository
				.findById(new CompanyCode(AppContexts.user().companyCode()), historyId);
		accidentInsuranceRate.saveToMemento(historyAccidentInsuranceRateFindOutDto);
		return historyAccidentInsuranceRateFindOutDto;
	}

}
