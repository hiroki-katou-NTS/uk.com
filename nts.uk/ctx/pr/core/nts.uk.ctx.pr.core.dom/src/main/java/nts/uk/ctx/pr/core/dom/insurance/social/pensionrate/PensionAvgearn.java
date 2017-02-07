/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;

/**
 * The Class PensionAvgearn.
 */
@Data
public class PensionAvgearn {

	/** The history id. */
	// historyId
	private String historyId;

	/** The child contribution amount. */
	private InsuranceAmount childContributionAmount;

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

	/**
	 * Instantiates a new pension avgearn.
	 *
	 * @param historyId
	 *            the history id
	 * @param childContributionAmount
	 *            the child contribution amount
	 * @param companyFund
	 *            the company fund
	 * @param companyFundExemption
	 *            the company fund exemption
	 * @param companyPension
	 *            the company pension
	 * @param personalFund
	 *            the personal fund
	 * @param personalFundExemption
	 *            the personal fund exemption
	 * @param personalPension
	 *            the personal pension
	 */
	public PensionAvgearn(String historyId, InsuranceAmount childContributionAmount, PensionAvgearnValue companyFund,
			PensionAvgearnValue companyFundExemption, PensionAvgearnValue companyPension,
			PensionAvgearnValue personalFund, PensionAvgearnValue personalFundExemption,
			PensionAvgearnValue personalPension) {
		super();

		// Validate required item
		if (childContributionAmount == null || companyFund == null || companyFundExemption == null
				|| companyPension == null || personalFund == null || personalFundExemption == null
				|| personalPension == null) {
			throw new BusinessException("ER001");
		}

		this.historyId = historyId;
		this.childContributionAmount = childContributionAmount;
		this.companyFund = companyFund;
		this.companyFundExemption = companyFundExemption;
		this.companyPension = companyPension;
		this.personalFund = personalFund;
		this.personalFundExemption = personalFundExemption;
		this.personalPension = personalPension;
	}

}
