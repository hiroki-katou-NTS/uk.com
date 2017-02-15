/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;

/**
 * The Class PensionAvgearn.
 */
@Getter
public class PensionAvgearn extends DomainObject {

	/** The history id. */
	// historyId
	private String historyId;

	/** The level code. */
	private Integer levelCode;

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

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new health insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public PensionAvgearn(PensionAvgearnGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.levelCode = memento.getLevelCode();
		this.childContributionAmount = memento.getChildContributionAmount();
		this.companyFund = memento.getCompanyFund();
		this.companyFundExemption = memento.getCompanyFundExemption();
		this.companyPension = memento.getCompanyPension();
		this.personalFund = memento.getPersonalFund();
		this.personalFundExemption = memento.getPersonalFundExemption();
		this.personalPension = memento.getPersonalPension();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(PensionAvgearnSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setLevelCode(this.levelCode);
		memento.setChildContributionAmount(this.childContributionAmount);
		memento.setCompanyFund(this.companyFund);
		memento.setCompanyFundExemption(this.companyFundExemption);
		memento.setCompanyPension(this.companyPension);
		memento.setPersonalFund(this.personalFund);
		memento.setPersonalFundExemption(this.personalFundExemption);
		memento.setPersonalPension(this.personalPension);
	}

}
