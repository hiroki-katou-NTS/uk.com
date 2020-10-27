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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexFixedWorkTimezoneSetGetMemento.
 */
public class JpaFlexFixedWorkTimezoneSetGetMemento implements FixedWorkTimezoneSetGetMemento{
	
	
	/** The entity. */
	private KshmtWtFleBrFlWek entity;
	
	/**
	 * Instantiates a new jpa flex fixed work timezone set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFixedWorkTimezoneSetGetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#getLstWorkingTimezone()
	 */
	@Override
	public List<EmTimeZoneSet> getLstWorkingTimezone() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleWorkTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleWorkTss().stream()
				.map(entity -> new EmTimeZoneSet(new JpaFlexEmTimeZoneSetGetMemento(entity)))
				.collect(Collectors.toList());
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleOverTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleOverTss().stream()
				.map(entity -> new OverTimeOfTimeZoneSet(new JpaFlexOverTimeOfTimeZoneSetGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
