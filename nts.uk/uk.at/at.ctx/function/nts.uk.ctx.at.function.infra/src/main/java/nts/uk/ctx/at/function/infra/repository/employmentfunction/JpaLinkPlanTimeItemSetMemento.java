/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.employmentfunction;

import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemSetMemento;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItem;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItemPK;

/**
 * The Class JpaLinkPlanTimeItemSetMemento.
 */
public class JpaLinkPlanTimeItemSetMemento implements LinkPlanTimeItemSetMemento{

	/** The entity. */
	private KfnmtPlanTimeItem entity;
	
	/**
	 * Instantiates a new jpa link plan time item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaLinkPlanTimeItemSetMemento(KfnmtPlanTimeItem entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemSetMemento#setScheduleID(java.lang.String)
	 */
	@Override
	public void setScheduleID(String scheduleId) {
		KfnmtPlanTimeItemPK pk = entity.getKfnmtPlanTimeItemPK();
		pk.setScheduleId(scheduleId);
		this.entity.setKfnmtPlanTimeItemPK(pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemSetMemento#setAtdID(java.lang.Integer)
	 */
	@Override
	public void setAtdID(Integer atdId) {
		KfnmtPlanTimeItemPK pk = entity.getKfnmtPlanTimeItemPK();
		pk.setAtdId(atdId);
		this.entity.setKfnmtPlanTimeItemPK(pk);
	}

}
