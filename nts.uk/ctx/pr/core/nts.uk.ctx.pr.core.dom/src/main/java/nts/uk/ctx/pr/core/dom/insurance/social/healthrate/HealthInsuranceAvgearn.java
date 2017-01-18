/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.Getter;

/**
 * The Class HealthInsuranceAvgearn.
 */
@Getter
public class HealthInsuranceAvgearn {

	/** The history id. */
	// historyId
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValue personalAvg;

}
