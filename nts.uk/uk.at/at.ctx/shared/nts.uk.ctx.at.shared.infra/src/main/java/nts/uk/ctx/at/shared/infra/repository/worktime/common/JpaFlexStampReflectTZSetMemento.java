/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleStmpRefTsPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexStampReflectTZSetMemento.
 */
public class JpaFlexStampReflectTZSetMemento implements StampReflectTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFleStmpRefTs entity;

	/**
	 * Instantiates a new jpa flex stamp reflect TZ set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexStampReflectTZSetMemento(KshmtWtFleStmpRefTs entity) {
		super();
		if (entity.getKshmtWtFleStmpRefTsPK() == null) {
			entity.setKshmtWtFleStmpRefTsPK(new KshmtWtFleStmpRefTsPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #setEndTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEndTime(TimeWithDayAttr endTime) {
		this.entity.setEndTime(endTime.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.StampReflectTimezoneSetMemento
	 * #setStartTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartTime(TimeWithDayAttr startTime) {
		this.entity.setStartTime(startTime.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#
	 * setWorkNo(nts.uk.ctx.at.shared.dom.worktime.common.WorkNo)
	 */
	@Override
	public void setWorkNo(WorkNo workNo) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento#
	 * setClassification(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoLeavingWorkAtr)
	 */
	@Override
	public void setClassification(GoLeavingWorkAtr classification) {
		// Do nothing
	}

}
