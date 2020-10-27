/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetailGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowRestSetGetMemento;

/**
 * The Class JpaFlexFlowWorkRestSettingDetailGetMemento.
 */
public class JpaFlexFlowWorkRestSettingDetailGetMemento implements FlowWorkRestSettingDetailGetMemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;

	/**
	 * Instantiates a new jpa flex flow work rest setting detail get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFlowWorkRestSettingDetailGetMemento(KshmtWtFleBrFl entity) {
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
		return new FlowRestSet(new JpaFlexFlowRestSetGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSettingDetailGetMemento#getFlowFixedRestSetting()
	 */
	@Override
	public FlowFixedRestSet getFlowFixedRestSetting() {
		return new FlowFixedRestSet(new JpaFlexFlowFixedRestSetGetMemento(this.entity));
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
