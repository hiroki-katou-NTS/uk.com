/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder
@Getter
public class HealthInsuranceAvgearnDto implements HealthInsuranceAvgearnSetMemento {

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValueDto companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValueDto personalAvg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setLevelCode(java.lang.Integer)
	 */
	@Override
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setCompanyAvg(nts.uk.ctx.pr.core.dom.
	 * insurance.social.healthavgearn.HealthInsuranceAvgearnValue)
	 */
	@Override
	public void setCompanyAvg(HealthInsuranceAvgearnValue companyAvg) {
		this.companyAvg = HealthInsuranceAvgearnValueDto.fromDomain(companyAvg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setPersonalAvg(nts.uk.ctx.pr.core.dom.
	 * insurance.social.healthavgearn.HealthInsuranceAvgearnValue)
	 */
	@Override
	public void setPersonalAvg(HealthInsuranceAvgearnValue personalAvg) {
		this.personalAvg = HealthInsuranceAvgearnValueDto.fromDomain(personalAvg);
	}

}
