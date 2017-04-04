/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;

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
	private CommonAmount childContributionAmount;

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
	 * Health insurance avgearn.
	 */
	private PensionAvgearn() {
	};
	
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

	/**
	 * Copy with new history id.
	 *
	 * @param newHistoryId the new history id
	 * @return the pension avgearn
	 */
	public PensionAvgearn copyWithNewHistoryId(String newHistoryId) {
		PensionAvgearn pensionAvgearn = new PensionAvgearn();
		pensionAvgearn.historyId = newHistoryId;
		pensionAvgearn.levelCode = this.levelCode;
		pensionAvgearn.personalFund = this.personalFund;
		pensionAvgearn.personalFundExemption = this.personalFundExemption;
		pensionAvgearn.personalPension = this.personalPension;
		pensionAvgearn.companyFund = this.companyFund;
		pensionAvgearn.companyFundExemption = this.companyFundExemption;
		pensionAvgearn.companyPension = this.companyPension;
		pensionAvgearn.childContributionAmount = this.childContributionAmount;
		return pensionAvgearn;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param newHistoryId the new history id
	 * @param levelCode the level code
	 * @return the object
	 */
	public static PensionAvgearn createWithIntial(String newHistoryId, Integer levelCode) {
		PensionAvgearn pensionAvgearn = new PensionAvgearn();
		pensionAvgearn.historyId = newHistoryId;
		pensionAvgearn.levelCode = levelCode;
		CommonAmount defaultAmount = new CommonAmount(BigDecimal.ZERO);
		PensionAvgearnValue defaultValue = new PensionAvgearnValue(defaultAmount, defaultAmount, defaultAmount);
		pensionAvgearn.personalFund = defaultValue;
		pensionAvgearn.personalFundExemption = defaultValue;
		pensionAvgearn.personalPension = defaultValue;
		pensionAvgearn.companyFund = defaultValue;
		pensionAvgearn.companyFundExemption = defaultValue;
		pensionAvgearn.companyPension = defaultValue;
		pensionAvgearn.childContributionAmount = defaultAmount;
		return pensionAvgearn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((levelCode == null) ? 0 : levelCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PensionAvgearn))
			return false;
		PensionAvgearn other = (PensionAvgearn) obj;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		if (levelCode == null) {
			if (other.levelCode != null)
				return false;
		} else if (!levelCode.equals(other.levelCode))
			return false;
		return true;
	}
	
}
