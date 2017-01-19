/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import lombok.Data;

/**
 * The Class ChargeRateItem.
 */
@Data
public class ChargeRateItem {

	/** The company rate. */
	private Double companyRate;

	/** The personal rate. */
	private Double personalRate;

	/**
	 * Instantiates a new charge rate item.
	 */
	public ChargeRateItem() {
		super();
	}

}
