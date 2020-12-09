/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowWorkDedicateSettingSetMemento.
 */
public class JpaFlowWorkDedicateSettingSetMemento implements FlWorkDedSetMemento {
	
	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow work dedicate setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkDedicateSettingSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetMemento#setOvertimeSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet)
	 */
	@Override
	public void setOvertimeSetting(FlowOTSet otSet) {
		otSet.saveToMemento(new JpaFlowOTSetSetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetMemento#setCalculateSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSet)
	 */
	@Override
	public void setCalculateSetting(FlowCalculateSet fcSet) {
		fcSet.saveToMemento(new JpaFlowCalculateSetSetMemento(this.entity));
	}

}
