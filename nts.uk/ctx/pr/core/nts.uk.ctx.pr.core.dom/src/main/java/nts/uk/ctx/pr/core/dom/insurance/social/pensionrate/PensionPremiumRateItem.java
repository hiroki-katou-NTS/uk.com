/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.ChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;

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
