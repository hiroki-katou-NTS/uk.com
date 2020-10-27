/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.employmentfunction;

import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItem;

/**
 * The Class JpaLinkPlanTimeItemGetMemento.
 */
public class JpaLinkPlanTimeItemGetMemento implements LinkPlanTimeItemGetMemento {

	/** The entity. */
	private KfnmtPlanTimeItem entity;

	/**
	 * Instantiates a new jpa link plan time item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaLinkPlanTimeItemGetMemento(KfnmtPlanTimeItem entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento#getScheduleID()
	 */
	@Override
	public String getScheduleID() {
		return this.entity.getKfnmtPlanTimeItemPK().getScheduleId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento#getAtdID()
	 */
	@Override
	public Integer getAtdID() {
		return this.entity.getKfnmtPlanTimeItemPK().getAtdId();
	}

}
