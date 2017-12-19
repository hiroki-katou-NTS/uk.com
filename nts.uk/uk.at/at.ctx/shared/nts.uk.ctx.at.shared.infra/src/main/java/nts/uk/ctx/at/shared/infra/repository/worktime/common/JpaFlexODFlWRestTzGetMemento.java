/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTime;

/**
 * The Class JpaFlexODFlWRestTzGetMemento.
 */
public class JpaFlexODFlWRestTzGetMemento implements FlowWorkRestTimezoneGetMemento{
	
	/** The entity. */
	private KshmtFlexOdRestTime entity;
	
	/** The entity fixed rests. */
	private List<KshmtFlexOdFixRest> entityFixedRests;
	
	/** The entity flow rests. */
	private List<KshmtFlexOdRestSet> entityFlowRests;
	

	/**
	 * Instantiates a new jpa flex OD fl W rest tz get memento.
	 *
	 * @param entity the entity
	 * @param entityFixedRests the entity fixed rests
	 * @param entityFlowRests the entity flow rests
	 */
	public JpaFlexODFlWRestTzGetMemento(KshmtFlexOdRestTime entity, List<KshmtFlexOdFixRest> entityFixedRests,
			List<KshmtFlexOdRestSet> entityFlowRests) {
		super();
		this.entity = entity;
		this.entityFixedRests = entityFixedRests;
		this.entityFlowRests = entityFlowRests;
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
		return new TimezoneOfFixedRestTimeSet(new JpaFlexODTzOFRTimeSetGetMemento(this.entityFixedRests));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFlowRestTimezone()
	 */
	@Override
	public FlowRestTimezone getFlowRestTimezone() {
		return new FlowRestTimezone(new JpaFlexODFlowRestTzGetMemento(this.entity, this.entityFlowRests));
	}

}
