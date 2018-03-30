/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ExcessOutsideTimeSetReg.
 */
@Getter
@Setter
@Builder
public class ExcessOutsideTimeSetRegDto {

	/** The legal over time work. */
	private Boolean legalOverTimeWork;

	/** The legal holiday. */
	private Boolean legalHoliday;

	/** The surcharge week month. */
	private Boolean surchargeWeekMonth;

}
