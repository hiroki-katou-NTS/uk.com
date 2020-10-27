/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTsPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedStampReflectTimezoneSetMemento.
 */
public class JpaFixedStampReflectTimezoneSetMemento implements StampReflectTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFixStmpRefTs entity;

	/**
	 * Instantiates a new jpa fixed stamp reflect timezone set memento.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTimeCd
	 *            the work time cd
	 * @param workNo
	 *            the work no
	 * @param classification
	 *            the classification
	 * @param entity
	 *            the entity
	 */
	public JpaFixedStampReflectTimezoneSetMemento(String companyId, String workTimeCd, WorkNo workNo,
			GoLeavingWorkAtr classification, KshmtWtFixStmpRefTs entity) {
		this.entity = entity;
		if (this.entity.getKshmtWtFixStmpRefTsPK() == null) {
			this.entity.setKshmtWtFixStmpRefTsPK(new KshmtWtFixStmpRefTsPK());
		}
		this.entity.getKshmtWtFixStmpRefTsPK().setCid(companyId);
		this.entity.getKshmtWtFixStmpRefTsPK().setWorktimeCd(workTimeCd);
		this.entity.getKshmtWtFixStmpRefTsPK().setWorkNo(workNo.v());
		this.entity.getKshmtWtFixStmpRefTsPK().setAtr(classification.value);
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

}
