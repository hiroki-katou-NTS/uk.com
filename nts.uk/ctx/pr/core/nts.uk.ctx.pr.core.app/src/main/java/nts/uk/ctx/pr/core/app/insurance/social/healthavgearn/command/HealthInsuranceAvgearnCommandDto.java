/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnValueDto;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

/**
 * The Class HealthInsuranceAvgearnCommandDto.
 */

@Getter
@Setter
public class HealthInsuranceAvgearnCommandDto implements HealthInsuranceAvgearnGetMemento {

	/** The history id. */
	private String historyId;

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
	 * HealthInsuranceAvgearnGetMemento#getPersonalAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getPersonalAvg() {
		return HealthInsuranceAvgearnValueDto.toDomain(this.personalAvg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		return this.levelCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.historyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getCompanyAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getCompanyAvg() {
		return HealthInsuranceAvgearnValueDto.toDomain(this.companyAvg);
	}

}
