/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.flexset.KshmtFlexHaGroup;

/**
 * The Class JpaFlexHAFWRestTZGetMemento.
 */
public class JpaFlexHAFWRestTZGetMemento implements FlowWorkRestTimezoneGetMemento{
	
	/** The entity group. */
	private KshmtFlexHaGroup entityGroup;
	

	/**
	 * Instantiates a new jpa flex HAFW rest TZ get memento.
	 *
	 * @param entityGroup the entity group
	 */
	public JpaFlexHAFWRestTZGetMemento(KshmtFlexHaGroup entityGroup) {
		super();
		this.entityGroup = entityGroup;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixRestTime()
	 */
	@Override
	public boolean getFixRestTime() {
		return BooleanGetAtr.getAtrByInteger(this.entityGroup.getEntity().getFixRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixedRestTimezone()
	 */
	@Override
	public TimezoneOfFixedRestTimeSet getFixedRestTimezone() {
		return new TimezoneOfFixedRestTimeSet(
				new JpaFlexHATzOFRTimeSetGetMemento(this.entityGroup.getEntityFixedRests()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFlowRestTimezone()
	 */
	@Override
	public FlowRestTimezone getFlowRestTimezone() {
		return new FlowRestTimezone(new JpaFlexHAFlowRestTzGetMemento(this.entityGroup.getEntity()));
	}

}
