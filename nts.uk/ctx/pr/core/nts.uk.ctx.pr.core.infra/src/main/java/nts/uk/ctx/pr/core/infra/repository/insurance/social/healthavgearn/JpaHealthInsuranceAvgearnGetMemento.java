/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthavgearn;

import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;

/**
 * The Class JpaHealthInsuranceAvgearnGetMemento.
 */
public class JpaHealthInsuranceAvgearnGetMemento implements HealthInsuranceAvgearnGetMemento {

	/** The entity. */
	protected QismtHealthInsuAvgearn entity;

	/**
	 * Instantiates a new jpa health insurance avgearn get memento.
	 *
	 * @param entity the entity
	 */
	public JpaHealthInsuranceAvgearnGetMemento(QismtHealthInsuAvgearn entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento#getPersonalAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getPersonalAvg() {
		return new HealthInsuranceAvgearnValue(new InsuranceAmount(entity.getPHealthBasicMny()),
				new CommonAmount(entity.getPHealthGeneralMny()), new CommonAmount(entity.getPHealthNursingMny()),
				new InsuranceAmount(entity.getPHealthSpecificMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		return entity.getQismtHealthInsuAvgearnPK().getHealthInsuGrade().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return entity.getQismtHealthInsuAvgearnPK().getHistId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento#getCompanyAvg()
	 */
	@Override
	public HealthInsuranceAvgearnValue getCompanyAvg() {
		return new HealthInsuranceAvgearnValue(new InsuranceAmount(entity.getCHealthBasicMny()),
				new CommonAmount(entity.getCHealthGeneralMny()), new CommonAmount(entity.getCHealthNursingMny()),
				new InsuranceAmount(entity.getCHealthSpecificMny()));
	}

}
