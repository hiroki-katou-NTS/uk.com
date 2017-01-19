/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import lombok.Data;

/**
 * The Class PensionAvgearn.
 */
@Data
public class PensionAvgearn {

	/** The history id. */
	// historyId
	private String historyId;

	/** The child contribution amount. */
	private Long childContributionAmount;

	/** The company fund. */
	private PensionAvgearnValue companyFund;

	/** The company fund exemption. */
	private PensionAvgearnValue companyFundExemption;

	/** The company pension. */
	private PensionAvgearnValue companyPension;

	/** The personal fund. */
	private PensionAvgearnValue personalFund;

	/** The personal fund exemption. */
	private PensionAvgearnValue personalFundExemption;

	/** The personal pension. */
	private PensionAvgearnValue personalPension;

	/**
	 * Instantiates a new pension avgearn.
	 */
	public PensionAvgearn() {
		super();
	}

}
