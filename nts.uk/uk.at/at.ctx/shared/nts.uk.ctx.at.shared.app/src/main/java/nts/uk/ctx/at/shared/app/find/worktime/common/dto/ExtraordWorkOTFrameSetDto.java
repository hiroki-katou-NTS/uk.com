/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ExtraordWorkOTFrameSetDto.
 */
@Getter
@Setter
public class ExtraordWorkOTFrameSetDto {

	/** The OT frame no. */
	private BigDecimal OTFrameNo;

	/** The in legal work frame no. */
	private BigDecimal inLegalWorkFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;
}
