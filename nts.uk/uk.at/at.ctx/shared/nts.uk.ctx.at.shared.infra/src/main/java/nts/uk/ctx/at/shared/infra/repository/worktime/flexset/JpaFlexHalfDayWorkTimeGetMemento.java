/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleOverTs;

/**
 * The Class JpaFlexHalfDayWorkTimeGetMemento.
 */
public class JpaFlexHalfDayWorkTimeGetMemento implements FlexHalfDayWorkTimeGetMemento {

	/** The entity. */
	/*private*/ List<KshmtWtFleOverTs> entity;

	/** The entity group. */
	// private KshmtFlexHaGroup entityGroup;

	/**
	 * Instantiates a new jpa flex half day work time get memento.
	 *
	 * @param entity
	 *            the entity
	 * @param entityGroup
	 *            the entity group
	 */
//	public JpaFlexHalfDayWorkTimeGetMemento(List<KshmtWtFleOverTs> entity,
//			KshmtFlexHaGroup entityGroup) {
//		super();
//		// this.entity = entity;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#
	 * getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		// TODO Auto-generated method stub
		return null;
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
		return null;
		// return new FixedWorkTimezoneSet(new
		// JpaFlexFixedWorkTimezoneSetGetMemento(this.entity));
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
		return AmPmAtr.AM;
	}

}
