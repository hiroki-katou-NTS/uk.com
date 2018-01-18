/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.employmentfunction;

import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnstPlanTimeItem;

/**
 * The Class JpaLinkPlanTimeItemGetMemento.
 */
public class JpaLinkPlanTimeItemGetMemento implements LinkPlanTimeItemGetMemento {

	/** The entity. */
	private KfnstPlanTimeItem entity;

	/**
	 * Instantiates a new jpa link plan time item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaLinkPlanTimeItemGetMemento(KfnstPlanTimeItem entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento#getScheduleID()
	 */
	@Override
	public String getScheduleID() {
		return this.entity.getKfnstPlanTimeItemPK().getScheduleId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemGetMemento#getAtdID()
	 */
	@Override
	public Integer getAtdID() {
		return this.entity.getKfnstPlanTimeItemPK().getAtdId();
	}

}
