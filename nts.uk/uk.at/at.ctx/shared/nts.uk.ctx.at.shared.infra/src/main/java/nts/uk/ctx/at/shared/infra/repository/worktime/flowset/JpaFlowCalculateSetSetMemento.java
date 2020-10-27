/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowCalculateSetSetMemento.
 */
public class JpaFlowCalculateSetSetMemento implements FlowCalculateSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow calculate set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowCalculateSetSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlCalcSetMemento#setCalcStartTimeSet(nts.uk.ctx.at.shared.dom.worktime.flowset.PrePlanWorkTimeCalcMethod)
	 */
	@Override
	public void setCalcStartTimeSet(PrePlanWorkTimeCalcMethod method) {
		this.entity.setCalcStrTimeSet(method.value);
	}

}
