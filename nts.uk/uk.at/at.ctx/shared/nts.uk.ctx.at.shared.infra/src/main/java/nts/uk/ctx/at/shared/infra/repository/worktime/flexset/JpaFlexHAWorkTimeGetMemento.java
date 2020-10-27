/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFixedWorkTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexHAFWRestTZGetMemento;

/**
 * The Class JpaFlexHAWorkTimeGetMemento.
 */
public class JpaFlexHAWorkTimeGetMemento implements FlexHalfDayWorkTimeGetMemento {

	/** The entity. */
	 private KshmtWtFleBrFlWek entity;
	 
	 
	/**
	 * Instantiates a new jpa flex HA work time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAWorkTimeGetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getLstRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(new JpaFlexHAFWRestTZGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		return new FixedWorkTimezoneSet(new JpaFlexFixedWorkTimezoneSetGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getAmpmAtr()
	 */
	@Override
	public AmPmAtr getAmpmAtr() {
		return AmPmAtr.valueOf(this.entity.getKshmtWtFleBrFlWekPK().getAmPmAtr());
	}

}
