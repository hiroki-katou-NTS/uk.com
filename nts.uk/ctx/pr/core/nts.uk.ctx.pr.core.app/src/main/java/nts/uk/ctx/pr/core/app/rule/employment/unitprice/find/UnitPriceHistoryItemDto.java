/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.find;

import lombok.Builder;

/**
 * The Class UnitPriceHistoryItemDto.
 */
@Builder
public class UnitPriceHistoryItemDto {

	/** The id. */
	public String id;

	/** The start month. */
	public Integer startMonth;

	/** The end month. */
	public Integer endMonth;

}
