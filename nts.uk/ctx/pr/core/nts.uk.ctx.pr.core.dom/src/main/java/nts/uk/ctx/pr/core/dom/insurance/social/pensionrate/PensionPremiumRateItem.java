/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.util.Set;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;

/**
 * The Class PensionPremiumRateItem.
 */
@Data
public class PensionPremiumRateItem extends AggregateRoot {

	/** The charge rates. */
	private Set<PensionChargeRateItem> chargeRates;

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	/**
	 * Instantiates a new pension premium rate item.
	 */
	public PensionPremiumRateItem() {
		super();
	}

	/**
	 * @param chargeRates
	 * @param payType
	 * @param genderType
	 */
	public PensionPremiumRateItem(Set<PensionChargeRateItem> chargeRates, PaymentType payType,
			InsuranceGender genderType) {
		super();
		this.chargeRates = chargeRates;
		this.payType = payType;
		this.genderType = genderType;
	}

}
