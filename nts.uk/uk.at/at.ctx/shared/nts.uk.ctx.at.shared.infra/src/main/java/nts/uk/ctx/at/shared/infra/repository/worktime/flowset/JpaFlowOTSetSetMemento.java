/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowOTSetSetMemento.
 */
public class JpaFlowOTSetSetMemento implements FlowOTSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;

	/**
	 * Instantiates a new jpa flow OT set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowOTSetSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTSetMemento#
	 * setFixedChangeAtr(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FixedChangeAtr)
	 */
	@Override
	public void setFixedChangeAtr(FixedChangeAtr atr) {
		this.entity.setFixedChangeAtr(atr.value);
	}

}
