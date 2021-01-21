/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * The Class ExcessOutsideTimeSetReg.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcessOutsideTimeSetRegDto {

	/** The legal over time work. */
	private boolean legalOverTimeWork;

	/** The legal holiday. */
	private boolean legalHoliday;

	/** The surcharge week month. */
	private boolean surchargeWeekMonth;

	/** The except legal holidaywork. */
	private boolean exceptLegalHdwk;
	
	public ExcessOutsideTimeSetReg domain() {
		
		return new ExcessOutsideTimeSetReg(legalOverTimeWork, legalHoliday, surchargeWeekMonth, exceptLegalHdwk);
	}
}
