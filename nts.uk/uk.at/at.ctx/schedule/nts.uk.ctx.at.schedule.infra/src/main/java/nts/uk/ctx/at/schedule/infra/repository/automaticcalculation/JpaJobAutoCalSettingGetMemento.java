/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.PositionId;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoJobCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaJobAutoCalSettingGetMemento.
 */
public class JpaJobAutoCalSettingGetMemento implements JobAutoCalSettingGetMemento {

	/** The entity. */
	private KshmtAutoJobCalSet entity;

	/**
	 * Instantiates a new jpa job auto cal setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaJobAutoCalSettingGetMemento(KshmtAutoJobCalSet entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshmtAutoJobCalSetPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingGetMemento#getPositionId()
	 */
	@Override
	public PositionId getPositionId() {
		return new PositionId(this.entity.getKshmtAutoJobCalSetPK().getJobid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingGetMemento#getNormalOTTime()
	 */
	@Override
	public AutoCalOvertimeSetting getNormalOTTime() {
		return new AutoCalOvertimeSetting(new JpaAutoCalOvertimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getFlexOTTime()
	 */
	@Override
	public AutoCalFlexOvertimeSetting getFlexOTTime() {
		return new AutoCalFlexOvertimeSetting(new JpaAutoCalFlexOvertimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getRestTime()
	 */
	@Override
	public AutoCalRestTimeSetting getRestTime() {
		return new AutoCalRestTimeSetting(new JpaAutoCalRestTimeSettingGetMemento(this.entity));
	}

}
