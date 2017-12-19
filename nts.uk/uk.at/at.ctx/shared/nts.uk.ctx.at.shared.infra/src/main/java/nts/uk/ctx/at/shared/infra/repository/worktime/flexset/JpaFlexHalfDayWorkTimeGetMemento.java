/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOtTimeSet;

/**
 * The Class JpaFlexHalfDayWorkTimeGetMemento.
 */
public class JpaFlexHalfDayWorkTimeGetMemento implements FlexHalfDayWorkTimeGetMemento{
	
	/** The entity. */
	private KshmtFlexOtTimeSet entity;
	
	/**
	 * Instantiates a new jpa flex half day work time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHalfDayWorkTimeGetMemento(KshmtFlexOtTimeSet entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getAmpmAtr()
	 */
	@Override
	public AmPmAtr getAmpmAtr() {
		return AmPmAtr.valueOf(this.entity.getKshmtFlexOtTimeSetPK().getAmPmAtr());
	}

}
