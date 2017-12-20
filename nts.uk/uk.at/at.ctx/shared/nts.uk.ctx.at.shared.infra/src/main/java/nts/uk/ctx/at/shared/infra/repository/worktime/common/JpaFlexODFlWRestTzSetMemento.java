/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexArrayGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexSetGroup;

/**
 * The Class JpaFlexODFlWRestTzGetMemento.
 */
public class JpaFlexODFlWRestTzSetMemento implements FlowWorkRestTimezoneSetMemento{
	
	/** The entity array group. */
	private KshmtFlexArrayGroup entityArrayGroup;
	
	/** The entity set group. */
	private KshmtFlexSetGroup entitySetGroup;
	
	

	/**
	 * Instantiates a new jpa flex OD fl W rest tz set memento.
	 *
	 * @param entityArrayGroup the entity array group
	 * @param entitySetGroup the entity set group
	 */
	public JpaFlexODFlWRestTzSetMemento(KshmtFlexArrayGroup entityArrayGroup, KshmtFlexSetGroup entitySetGroup) {
		super();
		this.entityArrayGroup = entityArrayGroup;
		this.entitySetGroup = entitySetGroup;
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#setFixRestTime(boolean)
	 */
	@Override
	public void setFixRestTime(boolean fixRestTime) {
		this.entitySetGroup.getEntityOffday().setFixRestTime(BooleanGetAtr.getAtrByBoolean(fixRestTime));
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
		if (fixedRestTimezone != null) {
			fixedRestTimezone
					.saveToMemento(new JpaFlexODTzOFRTimeSetSetMemento(this.entityArrayGroup.getEntityFixedRests()));
		}
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
		if (flowRestTimezone != null) {
			flowRestTimezone.saveToMemento(new JpaFlexODFlowRestTzSetMemento(this.entitySetGroup.getEntityOffday(),
					this.entityArrayGroup.getEntityFlowRests()));
		}
	}

}
