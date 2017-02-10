/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate;

import lombok.Data;
import nts.uk.ctx.pr.core.app.insurance.HistoryInsuranceDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;

/**
 * The Class HistoryAccidentInsuranceRateDto.
 */
public class HistoryAccidentInsuranceRateDto extends HistoryInsuranceDto {
	
	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the history accident insurance rate dto
	 */
	public static HistoryAccidentInsuranceRateDto fromDomain(AccidentInsuranceRate domain) {
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRateDto = new HistoryAccidentInsuranceRateDto();
		historyAccidentInsuranceRateDto.setEndMonthRage(convertMonth(domain.getApplyRange().getEndMonth()));
		historyAccidentInsuranceRateDto.setHistoryId(domain.getHistoryId());
		historyAccidentInsuranceRateDto.setStartMonthRage(convertMonth(domain.getApplyRange().getStartMonth()));
		historyAccidentInsuranceRateDto.setInforMonthRage(convertMonth(domain.getApplyRange().getStartMonth()) + " ~ "
				+ convertMonth(domain.getApplyRange().getEndMonth()));
		return historyAccidentInsuranceRateDto;
	}
}
