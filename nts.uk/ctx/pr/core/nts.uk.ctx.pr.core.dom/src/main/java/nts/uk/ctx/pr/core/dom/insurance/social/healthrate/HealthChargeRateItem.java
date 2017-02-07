/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.Ins3Rate;

/**
 * The Class ChargeRateItem.
 */
@Data
public class HealthChargeRateItem {

	/** The company rate. */
	private Ins3Rate companyRate;

	/** The personal rate. */
	private Ins3Rate personalRate;

	/**
	 * Instantiates a new charge rate item.
	 */
	public HealthChargeRateItem() {
		super();
	}

}
