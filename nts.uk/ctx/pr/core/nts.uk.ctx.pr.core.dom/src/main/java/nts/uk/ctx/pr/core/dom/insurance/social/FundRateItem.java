/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;

/**
 * The Class FundRateItem.
 */
@Getter
public class FundRateItem {

	/** The burden charge rate. */
	private ChargeRateItem burdenChargeRate;

	/** The pay type. */
	private PaymentType payType;

	/** The exemption charge rate. */
	private ChargeRateItem exemptionChargeRate;

	/** The gender type. */
	private InsuranceGender genderType;

}
