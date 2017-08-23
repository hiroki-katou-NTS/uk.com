/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyEstablishmentDto {
	
	/** The estimate time. */
	private CompanyEstimateTimeDto estimateTime;
	
	/** The estimate price. */
	private CompanyEstimatePriceDto estimatePrice;
	
	
	/** The estimate number of day. */
	private CompanyEstimateNumberOfDayDto estimateNumberOfDay;

}
