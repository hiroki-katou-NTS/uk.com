/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.AccidentInsuranceRateFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;
import nts.uk.shr.com.context.AppContexts;

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
		AccidentInsuranceRateFindOutDto accidentInsuranceRateFindOutDto = new AccidentInsuranceRateFindOutDto();
		AccidentInsuranceRate accidentInsuranceRate = accidentInsuranceRateRepository
				.findById(new CompanyCode(AppContexts.user().companyCode()), historyId);
		accidentInsuranceRate.saveToMemento(accidentInsuranceRateFindOutDto);
		return accidentInsuranceRateFindOutDto;
	}

}
