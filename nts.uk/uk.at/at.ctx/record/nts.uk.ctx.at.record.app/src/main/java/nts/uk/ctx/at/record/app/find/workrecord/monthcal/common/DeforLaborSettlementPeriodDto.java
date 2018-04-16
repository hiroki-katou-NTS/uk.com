/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

}
