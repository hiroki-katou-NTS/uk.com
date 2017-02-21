/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearnPK;

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
		QismtHealthInsuAvgearnPK healthInsuAvgearnPK = new QismtHealthInsuAvgearnPK();
		healthInsuAvgearnPK.setHistId(historyId);
		this.typeValue.setQismtHealthInsuAvgearnPK(healthInsuAvgearnPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.
	 * HealthInsuranceAvgearnSetMemento#setLevelCode(java.lang.Integer)
	 */
	@Override
	public void setLevelCode(Integer levelCode) {
		QismtHealthInsuAvgearnPK healthInsuAvgearnPK = this.typeValue.getQismtHealthInsuAvgearnPK();
		healthInsuAvgearnPK.setHealthInsuGrade(BigDecimal.valueOf(levelCode));
		healthInsuAvgearnPK.setSiOfficeCd("1");
		healthInsuAvgearnPK.setCcd("0001");
		this.typeValue.setQismtHealthInsuAvgearnPK(healthInsuAvgearnPK);
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
		this.typeValue.setCHealthBasicMny(BigDecimal.valueOf(companyAvg.getHealthBasicMny().longValue()));
		this.typeValue.setCHealthGeneralMny(BigDecimal.valueOf(companyAvg.getHealthBasicMny().longValue()));
		this.typeValue.setCHealthNursingMny(BigDecimal.valueOf(companyAvg.getHealthBasicMny().longValue()));
		this.typeValue.setCHealthSpecificMny(BigDecimal.valueOf(companyAvg.getHealthBasicMny().longValue()));
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
		this.typeValue.setPHealthBasicMny(BigDecimal.valueOf(personalAvg.getHealthBasicMny().longValue()));
		this.typeValue.setPHealthGeneralMny(BigDecimal.valueOf(personalAvg.getHealthBasicMny().longValue()));
		this.typeValue.setPHealthNursingMny(BigDecimal.valueOf(personalAvg.getHealthBasicMny().longValue()));
		this.typeValue.setPHealthSpecificMny(BigDecimal.valueOf(personalAvg.getHealthBasicMny().longValue()));
	}
}
