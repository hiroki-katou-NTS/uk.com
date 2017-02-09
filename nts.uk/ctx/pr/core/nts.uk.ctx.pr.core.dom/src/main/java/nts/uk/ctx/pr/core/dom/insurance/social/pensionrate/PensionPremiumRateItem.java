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

	/** The pay type. */
	private PaymentType payType;

	/** The gender type. */
	private InsuranceGender genderType;

	/** The raise charge rate. */
	private PensionChargeRateItem raiseChargeRate;

	/** The deduct charge rate. */
	private PensionChargeRateItem deductChargeRate;

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
	 * @param raiseChargeRate
	 *            the raise charge rate
	 * @param deductChargeRate
	 *            the deduct charge rate
	 */
	public PensionPremiumRateItem(PaymentType payType, InsuranceGender genderType,
			PensionChargeRateItem raiseChargeRate, PensionChargeRateItem deductChargeRate) {
		super();
		this.payType = payType;
		this.genderType = genderType;
		this.raiseChargeRate = raiseChargeRate;
		this.deductChargeRate = deductChargeRate;
	}

}
