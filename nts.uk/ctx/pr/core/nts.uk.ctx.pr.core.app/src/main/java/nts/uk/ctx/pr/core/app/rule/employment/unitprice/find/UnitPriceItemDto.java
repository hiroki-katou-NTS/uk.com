/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.find;

import java.util.List;

import lombok.Builder;

/**
 * The Class UnitPriceItemDto.
 */
@Builder
public class UnitPriceItemDto {

	/** The unit price code. */
	public String unitPriceCode;

	/** The unit price name. */
	public String unitPriceName;

	/** The histories. */
	public List<UnitPriceHistoryItemDto> histories;
}
