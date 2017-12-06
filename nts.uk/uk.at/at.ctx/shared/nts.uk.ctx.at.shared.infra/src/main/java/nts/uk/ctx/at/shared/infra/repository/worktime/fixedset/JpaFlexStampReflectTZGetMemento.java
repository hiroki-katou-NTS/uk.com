/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflectPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFlexStampReflectTZGetMemento.
 */
public class JpaFlexStampReflectTZGetMemento implements StampReflectTimezoneGetMemento{
	
	/** The entity. */
	private KshmtFlexStampReflect entity;
	
	/**
	 * Instantiates a new jpa flex stamp reflect TZ get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexStampReflectTZGetMemento(KshmtFlexStampReflect entity) {
		super();
		if(entity.getKshmtFlexStampReflectPK() == null){
			entity.setKshmtFlexStampReflectPK(new KshmtFlexStampReflectPK());
		}
		this.entity = entity;
	}

	/**
	 * Gets the work no.
	 *
	 * @return the work no
	 */
	@Override
	public WorkNo getWorkNo() {
		return new WorkNo(this.entity.getKshmtFlexStampReflectPK().getWorkNo());
	}

	/**
	 * Gets the classification.
	 *
	 * @return the classification
	 */
	@Override
	public GoLeavingWorkAtr getClassification() {
		return GoLeavingWorkAtr.valueOf(this.entity.getClass1());
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	@Override
	public TimeWithDayAttr getEndTime() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	@Override
	public TimeWithDayAttr getStartTime() {
		return new TimeWithDayAttr(this.entity.getStartTime());
	}
	

}
