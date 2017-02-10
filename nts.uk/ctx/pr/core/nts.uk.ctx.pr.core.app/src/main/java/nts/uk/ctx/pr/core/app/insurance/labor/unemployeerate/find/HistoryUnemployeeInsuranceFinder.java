/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.HistoryUnemployeeInsuranceDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;

@Stateless
@Transactional
public class HistoryUnemployeeInsuranceFinder {

	/** The unemployee insurance rate repository. */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	public List<HistoryUnemployeeInsuranceDto> findAll(String companyCode) {
		List<HistoryUnemployeeInsuranceDto> lstHistoryUnemployeeInsurance = new ArrayList<>();
		for (UnemployeeInsuranceRate unemployeeInsuranceRate : unemployeeInsuranceRateRepository.findAll(companyCode)) {
			lstHistoryUnemployeeInsurance.add(HistoryUnemployeeInsuranceDto.fromDomain(unemployeeInsuranceRate));
		}
		return lstHistoryUnemployeeInsurance;
	}

	public HistoryUnemployeeInsuranceDto find(String companyCode, String historyId) {
		return HistoryUnemployeeInsuranceDto
				.fromDomain(unemployeeInsuranceRateRepository.findById(historyId, companyCode));
	}
}
