/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowWorkDedicateSettingGetMemento.
 */
public class JpaFlowWorkDedicateSettingGetMemento implements FlWorkDedGetMemento {

	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow work dedicate setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkDedicateSettingGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento#getOvertimeSetting()
	 */
	@Override
	public FlowOTSet getOvertimeSetting() {
		return new FlowOTSet(new JpaFlowOTSetGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedGetMemento#getCalculateSetting()
	 */
	@Override
	public FlowCalculateSet getCalculateSetting() {
		return new FlowCalculateSet(new JpaFlowCalculateSetGetMemento(this.entity));
	}

}
