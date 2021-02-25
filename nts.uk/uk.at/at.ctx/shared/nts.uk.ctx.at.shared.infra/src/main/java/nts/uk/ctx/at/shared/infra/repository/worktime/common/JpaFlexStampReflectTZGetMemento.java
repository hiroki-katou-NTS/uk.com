/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflectPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexStampReflectTZGetMemento.
 */
public class JpaFlexStampReflectTZGetMemento implements StampReflectTimezoneGetMemento {

	/** The entity. */
	private KshmtFlexStampReflect entity;

	/**
	 * Instantiates a new jpa flex stamp reflect TZ get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexStampReflectTZGetMemento(KshmtFlexStampReflect entity) {
		super();
		if (entity.getKshmtFlexStampReflectPK() == null) {
			entity.setKshmtFlexStampReflectPK(new KshmtFlexStampReflectPK());
		}
		this.entity = entity;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento#
	 * getWorkNo()
	 */
	@Override
	public WorkNo getWorkNo() {
		return new WorkNo(this.entity.getKshmtFlexStampReflectPK().getWorkNo());
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
		return GoLeavingWorkAtr.valueOf(this.entity.getKshmtFlexStampReflectPK().getAtr());
	}

}
