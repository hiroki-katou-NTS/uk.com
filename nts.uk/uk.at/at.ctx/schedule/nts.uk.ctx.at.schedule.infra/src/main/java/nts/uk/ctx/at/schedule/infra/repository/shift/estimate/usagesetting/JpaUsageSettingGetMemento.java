/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.usagesetting;

import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UseClassification;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.usagesetting.KscmtEstUsage;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaUsageSettingGetMemento.
 */
public class JpaUsageSettingGetMemento implements UsageSettingGetMemento {

	/** The entity. */
	private KscmtEstUsage entity;

	/**
	 * Instantiates a new jpa usage setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaUsageSettingGetMemento(KscmtEstUsage entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingGetMemento#getEmploymentSetting()
	 */
	@Override
	public UseClassification getEmploymentSetting() {
		return UseClassification.valueOf(this.entity.getEmpSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingGetMemento#getPersonalSetting()
	 */
	@Override
	public UseClassification getPersonalSetting() {
		return UseClassification.valueOf(this.entity.getPSet());
	}

}
