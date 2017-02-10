/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.HistoryAccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;

/**
 * The Class HistoryAccidentInsuranceRateF‌inder.
 */
@Stateless
@Transactional
public class HistoryAccidentInsuranceRateF‌inder {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	public List<HistoryAccidentInsuranceRateDto> findAll(String companyCode) {
		List<HistoryAccidentInsuranceRateDto> lstHistoryAccidentInsuranceRateDto = new ArrayList<>();
		for (AccidentInsuranceRate accidentInsuranceRate : accidentInsuranceRateRepository.findAll(companyCode)) {
			lstHistoryAccidentInsuranceRateDto.add(HistoryAccidentInsuranceRateDto.fromDomain(accidentInsuranceRate));
		}
		return lstHistoryAccidentInsuranceRateDto;
	}

	/**
	 * Find.
	 *
	 * @param historyAccidentInsuranceRateFindInDto the history accident insurance rate find in dto
	 * @return the history accident insurance rate dto
	 */
	public HistoryAccidentInsuranceRateDto find(
			HistoryAccidentInsuranceRateFindInDto historyAccidentInsuranceRateFindInDto) {
		return HistoryAccidentInsuranceRateDto.fromDomain(
				accidentInsuranceRateRepository.findById(historyAccidentInsuranceRateFindInDto.getCompanyCode(),
						historyAccidentInsuranceRateFindInDto.getHistoryId()));
	}

}
