/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.core.app.insurance.labor.unemployeerate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnemployeeInsuranceRateDto {
	
	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	/** The company code. */
	private String companyCode;

	// private MonthRange applyRange;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;
}
