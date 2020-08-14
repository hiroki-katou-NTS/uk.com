/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class DeforWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
public class DeforWorkTimeAggrSetDto {

	/** The aggregate time set. */
	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	/** The excess outside time set. */
	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	/** The is ot trans criteria. */
	private boolean isOtTransCriteria;

	/** The settlement period. */
	private DeforLaborSettlementPeriodDto settlementPeriod;

}
