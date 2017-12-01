/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.TimeZoneRoundingDto;

/**
 * The Class OverTimeOfTimeZoneSetDto.
 */
@Getter
@Setter
public class OverTimeOfTimeZoneSetDto {
	
	/** The work timezone no. */
	private Integer workTimezoneNo;

	/** The restraint time use. */
	private boolean restraintTimeUse;

	/** The early OT use. */
	private boolean earlyOTUse;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/** The OT frame no. */
	private BigDecimal OTFrameNo;

	/** The legal O tframe no. */
	private BigDecimal legalOTframeNo;

	/** The settlement order. */
	private Integer settlementOrder;

}
