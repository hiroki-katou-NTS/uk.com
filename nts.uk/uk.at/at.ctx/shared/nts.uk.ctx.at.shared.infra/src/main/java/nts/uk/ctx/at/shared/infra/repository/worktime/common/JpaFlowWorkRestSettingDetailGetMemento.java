/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingDetailGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;

/**
 * The Class JpaFlowWorkRestSettingDetailGetMemento.
 */
public class JpaFlowWorkRestSettingDetailGetMemento implements FlowWorkRestSettingDetailGetMemento {

	/** The entity. */
	KshmtFlowRestSet entity;
	
	/**
	 * Jpa flow work rest setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkRestSettingDetailGetMemento(KshmtFlowRestSet entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingDetailGetMemento#getFlowRestSetting()
	 */
	@Override
	public FlowRestSet getFlowRestSetting() {
		return new FlowRestSet(new JpaFlowRestSetGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingDetailGetMemento#getFlowFixedRestSetting()
	 */
	@Override
	public FlowFixedRestSet getFlowFixedRestSetting() {
		return new FlowFixedRestSet(new JpaFlowFixedRestSetGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSettingDetailGetMemento#getUsePluralWorkRestTime()
	 */
	@Override
	public boolean getUsePluralWorkRestTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUsePluralWorkRestTime());
	}
}
