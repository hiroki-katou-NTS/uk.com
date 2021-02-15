/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtStampReflect;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDTStampReflectTimezoneSetMemento.
 */
public class JpaDTStampReflectTimezoneSetMemento implements StampReflectTimezoneSetMemento {

	/** The entity. */
	private KshmtDtStampReflect entity;

	/**
	 * Instantiates a new jpa DT stamp reflect timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDTStampReflectTimezoneSetMemento(KshmtDtStampReflect entity) {
		this.entity = entity;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime
	 *            the new end time
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#
	 * setEndTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEndTime(TimeWithDayAttr endTime) {
		this.entity.setEndTime(endTime.v());
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime
	 *            the new start time
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#
	 * setStartTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartTime(TimeWithDayAttr startTime) {
		this.entity.setStartTime(startTime.v());
	}

	/**
	 * Sets the work no.
	 *
	 * @param workNo
	 *            the new work no
	 */
	@Override
	public void setWorkNo(WorkNo workNo) {
		// Do nothing
	}

	/**
	 * Sets the classification.
	 *
	 * @param classification
	 *            the new classification
	 */
	@Override
	public void setClassification(GoLeavingWorkAtr classification) {
		// Do nothing
	}

}
