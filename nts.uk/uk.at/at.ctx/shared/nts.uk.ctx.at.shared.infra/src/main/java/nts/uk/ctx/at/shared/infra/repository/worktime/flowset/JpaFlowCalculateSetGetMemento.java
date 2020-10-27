/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowCalculateSetGetMemento.
 */
public class JpaFlowCalculateSetGetMemento implements FlowCalculateGetMemento {
	
	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow calculate set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowCalculateSetGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlCalcGetMemento#getCalcStartTimeSet()
	 */
	@Override
	public PrePlanWorkTimeCalcMethod getCalcStartTimeSet() {
		return PrePlanWorkTimeCalcMethod.valueOf(this.entity.getCalcStrTimeSet());
	}

}
