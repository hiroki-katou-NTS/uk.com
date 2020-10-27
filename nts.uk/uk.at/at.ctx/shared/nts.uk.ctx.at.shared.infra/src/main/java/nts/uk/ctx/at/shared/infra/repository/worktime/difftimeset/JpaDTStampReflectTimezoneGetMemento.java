/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifStmpRefTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDTStampReflectTimezoneGetMemento.
 */
public class JpaDTStampReflectTimezoneGetMemento implements StampReflectTimezoneGetMemento {

	/** The entity. */
	private KshmtWtDifStmpRefTs entity;

	/**
	 * Instantiates a new jpa DT stamp reflect timezone get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDTStampReflectTimezoneGetMemento(KshmtWtDifStmpRefTs entity) {
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
		return new WorkNo(this.entity.getKshmtWtDifStmpRefTsPK().getWorkNo());
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
		return GoLeavingWorkAtr.valueOf(this.entity.getKshmtWtDifStmpRefTsPK().getAtr());
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
		return new TimeWithDayAttr(this.entity.getEndTime());
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
		return new TimeWithDayAttr(this.entity.getStartTime());
	}

}
