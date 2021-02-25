/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * The Class ExcessOutsideTimeSetReg.
 */
@Getter
@Setter
@Builder
public class ExcessOutsideTimeSetRegDto {

	private boolean legalOverTimeWork;

	private boolean legalHoliday;

	private boolean surchargeWeekMonth;

	private boolean exceptLegalHdwk;

	public static ExcessOutsideTimeSetRegDto from(ExcessOutsideTimeSetReg domain){
		
		return ExcessOutsideTimeSetRegDto.builder()
				.legalHoliday(domain.isLegalHoliday())
				.legalOverTimeWork(domain.isLegalOverTimeWork())
				.surchargeWeekMonth(domain.isSurchargeWeekMonth())
				.exceptLegalHdwk(domain.isExceptLegalHdwk())
				.build();
	}
}
