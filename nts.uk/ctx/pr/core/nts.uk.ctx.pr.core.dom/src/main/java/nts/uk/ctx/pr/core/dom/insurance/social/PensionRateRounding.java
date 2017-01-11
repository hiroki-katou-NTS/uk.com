/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;

/**
 * The Class PensionRateRounding.
 */	
@Getter
public class PensionRateRounding {

	/** The pay type. */
	private PaymentType payType;

	/** The round atrs. */
	private RoundingItem roundAtrs;

}
