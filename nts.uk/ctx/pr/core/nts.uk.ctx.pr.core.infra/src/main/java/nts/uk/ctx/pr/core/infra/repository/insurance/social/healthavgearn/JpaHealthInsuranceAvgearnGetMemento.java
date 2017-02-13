/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaHealthInsuranceAvgearnGetMemento implements HealthInsuranceAvgearnGetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceAvgearnGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getLevelCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HealthInsuranceAvgearnValue getCompanyAvg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HealthInsuranceAvgearnValue getPersonalAvg() {
		// TODO Auto-generated method stub
		return null;
	}

}
