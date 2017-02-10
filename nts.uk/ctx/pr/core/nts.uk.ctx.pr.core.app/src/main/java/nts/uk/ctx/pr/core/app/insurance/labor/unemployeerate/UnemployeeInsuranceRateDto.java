/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class UnemployeeInsuranceRateDto.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnemployeeInsuranceRateDto {

	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;
	
	/** The version. */
	private long version;
	
	
}
