/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.social.ChargeRateItem;

/**
 * The Class InsuranceRateItem.
 */
@Data
public class InsuranceRateItem extends AggregateRoot {

	/** The charge rate. */
	private ChargeRateItem chargeRate;

	/** The pay type. */
	private PaymentType payType;

	/** The insurance type. */
	private HealthInsuranceType insuranceType;

	/**
	 * Instantiates a new insurance rate item.
	 */
	public InsuranceRateItem() {
		super();
	}

	/**
	 * @param chargeRate
	 * @param payType
	 * @param insuranceType
	 */
	public InsuranceRateItem(ChargeRateItem chargeRate, PaymentType payType, HealthInsuranceType insuranceType) {
		super();
		this.chargeRate = chargeRate;
		this.payType = payType;
		this.insuranceType = insuranceType;
	}
	
}
