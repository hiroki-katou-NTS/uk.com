/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;

/**
 * The Class InsuranceRateItem.
 */
@Data
public class InsuranceRateItem extends AggregateRoot {

	/** The pay type. */
	private PaymentType payType;

	/** The insurance type. */
	private HealthInsuranceType insuranceType;

	/** The charge rate. */
	private HealthChargeRateItem chargeRate;

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
	public InsuranceRateItem(PaymentType payType, HealthInsuranceType insuranceType, HealthChargeRateItem chargeRate) {
		super();

		// Validate required item
		if (payType == null || insuranceType == null || chargeRate == null) {
			throw new BusinessException("ER001");
		}

		this.chargeRate = chargeRate;
		this.payType = payType;
		this.insuranceType = insuranceType;
	}

}
