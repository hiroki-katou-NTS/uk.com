/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRefTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaStampReflectTimezoneGetMemento.
 */
public class JpaStampReflectTimezoneGetMemento implements StampReflectTimezoneGetMemento {

	/** The entity. */
	private KshmtWtFloStmpRefTs entity;

	/**
	 * Instantiates a new jpa stamp reflect timezone get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaStampReflectTimezoneGetMemento(KshmtWtFloStmpRefTs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento#
	 * getWorkNo()
	 */
	@Override
	public WorkNo getWorkNo() {
		return new WorkNo(this.entity.getKshmtWtFloStmpRefTsPK().getWorkNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento#
	 * getClassification()
	 */
	@Override
	public GoLeavingWorkAtr getClassification() {
		return GoLeavingWorkAtr.valueOf(this.entity.getKshmtWtFloStmpRefTsPK().getAttendAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento#
	 * getEndTime()
	 */
	@Override
	public TimeWithDayAttr getEndTime() {
		return new TimeWithDayAttr(this.entity.getEndClock());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento#
	 * getStartTime()
	 */
	@Override
	public TimeWithDayAttr getStartTime() {
		return new TimeWithDayAttr(this.entity.getStrClock());
	}

}
