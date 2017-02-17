/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;

/**
 * The Class JpaHealthInsuranceAvgearnGetMemento.
 */
public class JpaHealthInsuranceAvgearnGetMemento implements HealthInsuranceAvgearnGetMemento {

	/** The type value. */
	protected QismtHealthInsuAvgearn typeValue;

	/**
	 * Instantiates a new jpa health insurance avgearn get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceAvgearnGetMemento(QismtHealthInsuAvgearn typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getCompanyAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getCompanyAvg() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnGetMemento#getPersonalAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getPersonalAvg() {
		// TODO Auto-generated method stub
		return null;
	}

}
