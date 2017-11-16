/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.personal.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;

@Getter
@Setter
public class PersonalEstablishmentDto {
	
	/** The employee id. */
	private String employeeId;
	
	/** The estimate time. */
	private EstablishmentTimeDto estimateTime;
	
	/** The estimate price. */
	private EstablishmentPriceDto estimatePrice;
	
	
	/** The estimate number of day. */
	private EstablishmentNumberOfDayDto estimateNumberOfDay;

}
