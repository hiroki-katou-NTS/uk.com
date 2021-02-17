/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowOTSetGetMemento.
 */
public class JpaFlowOTSetGetMemento implements FlowOTGetMemento {
	
	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow OT set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowOTSetGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTGetMemento#getFixedChangeAtr()
	 */
	@Override
	public FixedChangeAtr getFixedChangeAtr() {
		return FixedChangeAtr.valueOf(entity.getFixedChangeAtr());
	}

}
