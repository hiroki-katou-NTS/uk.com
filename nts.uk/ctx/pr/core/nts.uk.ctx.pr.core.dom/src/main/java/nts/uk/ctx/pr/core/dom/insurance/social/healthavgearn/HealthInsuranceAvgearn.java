/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class HealthInsuranceAvgearn.
 */
@Getter
public class HealthInsuranceAvgearn extends AggregateRoot {

	/** The history id. */
	// historyId
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValue companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValue personalAvg;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new health insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public HealthInsuranceAvgearn(HealthInsuranceAvgearnGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.levelCode = memento.getLevelCode();
		this.companyAvg = memento.getCompanyAvg();
		this.personalAvg = memento.getPersonalAvg();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(HealthInsuranceAvgearnSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setLevelCode(this.levelCode);
		memento.setCompanyAvg(this.companyAvg);
		memento.setPersonalAvg(this.personalAvg);
		memento.setVersion(this.getVersion());
	}

}
