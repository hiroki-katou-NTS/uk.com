/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.rule.employment.unitprice.dto;

import lombok.Builder;

/**
 * The Class UnitPriceModel.
 */
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
public class UnitPriceModel {

	/** The unit price code. */
	public String unitPriceCode;

	/** The unit price name. */
	public String unitPriceName;
}
