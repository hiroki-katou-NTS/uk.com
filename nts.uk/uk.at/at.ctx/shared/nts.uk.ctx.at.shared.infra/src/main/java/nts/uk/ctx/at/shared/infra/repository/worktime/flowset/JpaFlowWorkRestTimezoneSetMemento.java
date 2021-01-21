/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFl;

/**
 * The Class JpaFlowWorkRestTimezoneSetMemento.
 */
public class JpaFlowWorkRestTimezoneSetMemento implements FlowWorkRestTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFloBrFl entity;

	/**
	 * Instantiates a new jpa flow work rest timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkRestTimezoneSetMemento(KshmtWtFloBrFl entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFixRestTime(boolean)
	 */
	@Override
	public void setFixRestTime(boolean fixRestTime) {
		this.entity.setFixRestTime(BooleanGetAtr.getAtrByBoolean(fixRestTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFixedRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSet)
	 */
	@Override
	public void setFixedRestTimezone(TimezoneOfFixedRestTimeSet fixedRestTimezone) {
		fixedRestTimezone.saveToMemento(new JpaTimezoneOfFixedRestTimeSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFlowRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestTimezone)
	 */
	@Override
	public void setFlowRestTimezone(FlowRestTimezone flowRestTimezone) {
		flowRestTimezone.saveToMemento(new JpaFlowRestTimezoneSetMemento(this.entity));
	}

}
