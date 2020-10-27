/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.usagesetting;

import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UseClassification;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.usagesetting.KscmtEstUsage;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaUsageSettingSetMemento.
 */
public class JpaUsageSettingSetMemento implements UsageSettingSetMemento {

	/** The entity. */
	private KscmtEstUsage entity;

	/**
	 * Instantiates a new jpa usage setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaUsageSettingSetMemento(KscmtEstUsage entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingSetMemento#setEmploymentSetting(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.usagesetting.UseClassification)
	 */
	@Override
	public void setEmploymentSetting(UseClassification employmentSetting) {
		this.entity.setEmpSet(employmentSetting.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
	 * UsageSettingSetMemento#setPersonalSetting(nts.uk.ctx.at.schedule.dom.
	 * shift.estimate.usagesetting.UseClassification)
	 */
	@Override
	public void setPersonalSetting(UseClassification personalSetting) {
		this.entity.setPSet(personalSetting.value);
	}

}
