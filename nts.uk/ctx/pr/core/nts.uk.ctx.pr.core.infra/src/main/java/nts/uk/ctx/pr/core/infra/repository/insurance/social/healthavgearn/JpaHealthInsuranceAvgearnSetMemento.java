/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaHealthInsuranceAvgearnSetMemento implements HealthInsuranceAvgearnSetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceAvgearnSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevelCode(Integer levelCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompanyAvg(HealthInsuranceAvgearnValue companyAvg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPersonalAvg(HealthInsuranceAvgearnValue personalAvg) {
		// TODO Auto-generated method stub

	}

}
