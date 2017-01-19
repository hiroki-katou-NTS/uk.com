/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import lombok.Data;

/**
 * The Class HealthInsuranceAvgearn.
 */
@Data
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

	/**
	 * Instantiates a new health insurance avgearn.
	 */
	public HealthInsuranceAvgearn() {
		super();
	}

}
