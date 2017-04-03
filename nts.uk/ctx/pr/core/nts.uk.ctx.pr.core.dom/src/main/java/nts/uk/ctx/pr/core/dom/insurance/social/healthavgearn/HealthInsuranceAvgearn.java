/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;

/**
 * The Class HealthInsuranceAvgearn.
 */
@Getter
public class HealthInsuranceAvgearn extends DomainObject {

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
	 * Instantiates a new HealthInsuranceAvgearn.
	 */
	private HealthInsuranceAvgearn() {
	};

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new health insurance rate.
	 *
	 * @param memento the memento
	 */
	public HealthInsuranceAvgearn(HealthInsuranceAvgearnGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.levelCode = memento.getLevelCode();
		this.companyAvg = memento.getCompanyAvg();
		this.personalAvg = memento.getPersonalAvg();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(HealthInsuranceAvgearnSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setLevelCode(this.levelCode);
		memento.setCompanyAvg(this.companyAvg);
		memento.setPersonalAvg(this.personalAvg);
	}

	/**
	 * Copy with new history id.
	 *
	 * @param newHistoryId the new history id
	 * @return the health insurance avgearn
	 */
	public HealthInsuranceAvgearn copyWithNewHistoryId(String newHistoryId) {
		HealthInsuranceAvgearn healthInsuranceAvgearn = new HealthInsuranceAvgearn();
		healthInsuranceAvgearn.historyId = newHistoryId;
		healthInsuranceAvgearn.levelCode = this.levelCode;
		healthInsuranceAvgearn.companyAvg = this.companyAvg;
		healthInsuranceAvgearn.personalAvg = this.personalAvg;
		return healthInsuranceAvgearn;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param newHistoryId the new history id
	 * @param levelCode the level code
	 * @return the health insurance avgearn
	 */
	public static HealthInsuranceAvgearn createWithIntial(String newHistoryId, Integer levelCode) {
		HealthInsuranceAvgearn healthInsuranceAvgearn = new HealthInsuranceAvgearn();
		healthInsuranceAvgearn.historyId = newHistoryId;
		healthInsuranceAvgearn.levelCode = levelCode;
		CommonAmount defaultCommonAmount = new CommonAmount(BigDecimal.ZERO);
		InsuranceAmount defaultInsuranceAmount = new InsuranceAmount(BigDecimal.ZERO);
		healthInsuranceAvgearn.companyAvg = new HealthInsuranceAvgearnValue(defaultInsuranceAmount, defaultCommonAmount,
				defaultCommonAmount, defaultInsuranceAmount);
		healthInsuranceAvgearn.personalAvg = new HealthInsuranceAvgearnValue(defaultInsuranceAmount,
				defaultCommonAmount, defaultCommonAmount, defaultInsuranceAmount);
		return healthInsuranceAvgearn;
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
		if (!(obj instanceof HealthInsuranceAvgearn))
			return false;
		HealthInsuranceAvgearn other = (HealthInsuranceAvgearn) obj;
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
