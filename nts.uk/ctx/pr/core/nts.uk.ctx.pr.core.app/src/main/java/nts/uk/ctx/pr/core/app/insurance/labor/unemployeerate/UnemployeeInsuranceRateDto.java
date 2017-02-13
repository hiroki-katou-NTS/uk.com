/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate;

import java.util.List;

import lombok.Data;

/**
 * The Class UnemployeeInsuranceRateDto.
 */
@Data
public class UnemployeeInsuranceRateDto {

	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;
	
	/** The version. */
	private long version;
	
	
}
