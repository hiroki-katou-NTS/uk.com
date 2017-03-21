/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;

/**
 * The Class ChargeRateItem.
 */
@Data
public class PensionChargeRateItem {

	/** The company rate. */
	private Ins2Rate companyRate;

	/** The personal rate. */
	private Ins2Rate personalRate;

	/**
	 * Instantiates a new charge rate item.
	 */
	public PensionChargeRateItem() {
		super();
	}

}
