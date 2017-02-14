/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;

/**
 * The Class PensionPremiumRateItem.
 */
@Data
public class PensionPremiumRateItem extends AggregateRoot {

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	/** The raise charge rate. */
	private PensionChargeRateItem chargeRate;

	/**
	 * Instantiates a new pension premium rate item.
	 */
	public PensionPremiumRateItem() {
		super();
	}

	/**
	 * Instantiates a new pension premium rate item.
	 *
	 * @param payType
	 *            the pay type
	 * @param genderType
	 *            the gender type
	 * @param chargeRate
	 *            the charge rate
	 */
	public PensionPremiumRateItem(PaymentType payType, InsuranceGender genderType, PensionChargeRateItem chargeRate) {
		super();
		this.payType = payType;
		this.genderType = genderType;
		this.chargeRate = chargeRate;
	}

}
