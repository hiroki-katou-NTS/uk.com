/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOtTimeSet;

/**
 * The Class JpaFlexFixedWorkTimezoneSetGetMemento.
 */
public class JpaFlexFixedWorkTimezoneSetGetMemento implements FixedWorkTimezoneSetGetMemento{
	
	/** The entity overtimes. */
	private List<KshmtFlexOtTimeSet> entityOvertimes;

	/**
	 * Instantiates a new jpa flex fixed work timezone set get memento.
	 *
	 * @param entityOvertimes the entity overtimes
	 */
	public JpaFlexFixedWorkTimezoneSetGetMemento(List<KshmtFlexOtTimeSet> entityOvertimes) {
		super();
		this.entityOvertimes = entityOvertimes;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#getLstWorkingTimezone()
	 */
	@Override
	public List<EmTimeZoneSet> getLstWorkingTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#
	 * getLstOTTimezone()
	 */
	@Override
	public List<OverTimeOfTimeZoneSet> getLstOTTimezone() {
		if(CollectionUtil.isEmpty(this.entityOvertimes)){
			return new ArrayList<>();
		}
		return this.entityOvertimes.stream()
				.map(entity -> new OverTimeOfTimeZoneSet(new JpaFlexOverTimeOfTimeZoneSetGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
