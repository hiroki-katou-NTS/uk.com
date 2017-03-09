/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import lombok.Builder;

/**
 * The Class UnitPriceHistoryItemDto.
 */
@Builder
public class WageTableHistoryItemDto {

	/** The id. */
	public String id;

	/** The start month. */
	public Integer startMonth;

	/** The end month. */
	public Integer endMonth;

}
