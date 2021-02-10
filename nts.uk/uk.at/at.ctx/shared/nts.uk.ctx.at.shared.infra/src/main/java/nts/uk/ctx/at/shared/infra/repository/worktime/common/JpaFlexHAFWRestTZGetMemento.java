/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexHAFWRestTZGetMemento.
 */
public class JpaFlexHAFWRestTZGetMemento implements FlowWorkRestTimezoneGetMemento{
	
	/** The entity group. */
	private KshmtWtFleBrFlWek entity;
	

	/**
	 * Instantiates a new jpa flex HAFW rest TZ get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFWRestTZGetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixRestTime()
	 */
	@Override
	public boolean getFixRestTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getFixRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixedRestTimezone()
	 */
	@Override
	public TimezoneOfFixedRestTimeSet getFixedRestTimezone() {
		return new TimezoneOfFixedRestTimeSet(new JpaFlexHATzOFRTimeSetGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFlowRestTimezone()
	 */
	@Override
	public FlowRestTimezone getFlowRestTimezone() {
		return new FlowRestTimezone(new JpaFlexHAFlowRestTzGetMemento(this.entity));
	}

}
