/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;

/**
 * The Class JpaHealthInsuranceAvgearnSetMemento.
 */
public class JpaHealthInsuranceAvgearnSetMemento implements HealthInsuranceAvgearnSetMemento {

	/** The type value. */
	protected QismtHealthInsuAvgearn typeValue;

	/**
	 * Instantiates a new jpa health insurance avgearn set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaHealthInsuranceAvgearnSetMemento(QismtHealthInsuAvgearn typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		// TODO ko co historyID?
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setLevelCode(java.lang.Integer)
	 */
	@Override
	public void setLevelCode(Integer levelCode) {
		// TODO ko co level code?
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
		// TODO rot cuc la long hay bigDecimal?
//		this.typeValue.setCHealthBasicMny(companyAvg.getHealthBasicMny().longValue());
//		this.typeValue.setCHealthGeneralMny(companyAvg.getHealthBasicMny().longValue());
//		this.typeValue.setCHealthNursingMny(companyAvg.getHealthBasicMny().longValue());
//		this.typeValue.setCHealthSpecificMny(companyAvg.getHealthBasicMny().longValue());
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
		// TODO rot cuc la long hay bigDecimal?
//		this.typeValue.setPHealthBasicMny(personalAvg.getHealthBasicMny().longValue());
//		this.typeValue.setPHealthGeneralMny(personalAvg.getHealthBasicMny().longValue());
//		this.typeValue.setPHealthNursingMny(personalAvg.getHealthBasicMny().longValue());
//		this.typeValue.setPHealthSpecificMny(personalAvg.getHealthBasicMny().longValue());
	}

}
