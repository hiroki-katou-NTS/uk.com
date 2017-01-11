/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import lombok.Getter;

/**
 * The Class UnemployeeInsuranceRateItem.
 */
@Getter
public class UnemployeeInsuranceRateItem {

	/** The career group. */
	private CareerGroup careerGroup;

	/** The company setting. */
	private UnemployeeInsuranceRateItemSetting companySetting;

	/** The personal setting. */
	private UnemployeeInsuranceRateItemSetting personalSetting;
}
