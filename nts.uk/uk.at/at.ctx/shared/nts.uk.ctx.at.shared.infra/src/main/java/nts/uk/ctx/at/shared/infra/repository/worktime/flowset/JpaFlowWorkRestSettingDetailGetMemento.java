/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;

/**
 * The Class JpaFlowWorkRestSettingDetailGetMemento.
 */
public class JpaFlowWorkRestSettingDetailGetMemento implements FlowWorkRestSettingDetailGetMemento {

	/** The entity. */
	KshmtWtFloBrFlAll entity;

	/**
	 * Instantiates a new jpa flow work rest setting detail get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkRestSettingDetailGetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getFlowRestSetting()
	 */
	@Override
	public FlowRestSet getFlowRestSetting() {
		return new FlowRestSet(new JpaFlowRestSetGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getFlowFixedRestSetting()
	 */
	@Override
	public FlowFixedRestSet getFlowFixedRestSetting() {
		return new FlowFixedRestSet(new JpaFlowFixedRestSetGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getUsePluralWorkRestTime()
	 */
	@Override
	public boolean getUsePluralWorkRestTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUsePluralWorkRestTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkRestSettingDetailGetMemento#getRoundingBreakMultipleWork()
	 */
	@Override
	public TimeRoundingSetting getRoundingBreakMultipleWork() {
		return new TimeRoundingSetting(this.entity.getRestSetUnit(), this.entity.getRestSetRounding());
	}
}
