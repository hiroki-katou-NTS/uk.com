/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;

/**
 * The Class HealthInsuranceRounding.
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = { "payType" })
public class HealthInsuranceRounding extends AggregateRoot {

	/** The pay type. */
	private PaymentType payType;

	/** The round atrs. */
	private RoundingItem roundAtrs;

	/**
	 * @param payType
	 * @param roundAtrs
	 */
	public HealthInsuranceRounding(PaymentType payType, RoundingItem roundAtrs) {
		super();
		this.payType = payType;
		this.roundAtrs = roundAtrs;
	}

}
