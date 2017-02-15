/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate;

import java.util.List;

import lombok.Data;

/**
 * Instantiates a new accident insurance rate dto.
 */
@Data
public class AccidentInsuranceRateDto {

	/** The history insurance. */
	// historyId
	private HistoryAccidentInsuranceRateDto historyInsurance;

	/** The rate items. */
	private List<InsuBizRateItemDto> rateItems;

}
