/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;

/**
 * The Class DeforLaborSettlementPeriod.
 */
@Getter
@Setter
@Builder
public class DeforLaborSettlementPeriodDto {

	/** The start month. */
	private Integer startMonth;

	/** The period. */
	private Integer period;

	/** The repeat atr. */
	private Boolean repeatAtr;
	
	public static DeforLaborSettlementPeriodDto from(DeforLaborSettlementPeriod domain) {
		
		return DeforLaborSettlementPeriodDto.builder()
				.startMonth(domain.getStartMonth().v())
				.period(domain.getPeriod().v())
				.repeatAtr(domain.isRepeat())
				.build();
	}
}
