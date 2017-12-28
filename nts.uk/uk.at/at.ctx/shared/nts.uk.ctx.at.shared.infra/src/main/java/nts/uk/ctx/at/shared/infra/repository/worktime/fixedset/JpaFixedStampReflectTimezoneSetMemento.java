/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflectPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedStampReflectTimezoneSetMemento.
 */
public class JpaFixedStampReflectTimezoneSetMemento implements StampReflectTimezoneSetMemento {

	/** The entity. */
	private KshmtFixedStampReflect entity;

	/**
	 * Instantiates a new jpa fixed stamp reflect timezone set memento.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTimeCd
	 *            the work time cd
	 * @param entity
	 *            the entity
	 */
	public JpaFixedStampReflectTimezoneSetMemento(String companyId, String workTimeCd, KshmtFixedStampReflect entity) {		
		this.entity = entity;
		if (this.entity.getKshmtFixedStampReflectPK() == null) {
			this.entity.setKshmtFixedStampReflectPK(new KshmtFixedStampReflectPK());
		}
		this.entity.getKshmtFixedStampReflectPK().setCid(companyId);
		this.entity.getKshmtFixedStampReflectPK().setWorktimeCd(workTimeCd);
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
		this.entity.getKshmtFixedStampReflectPK().setWorkNo(workNo.v().intValue());
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
		this.entity.setAtr(classification.value);
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
