/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;

/**
 * The Class PensionPremiumRateItem.
 */
@Getter
public class PensionPremiumRateItem extends AggregateRoot {

	/** The charge rates. */
	private Set<ChargeRateItem> chargeRates;

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

}
