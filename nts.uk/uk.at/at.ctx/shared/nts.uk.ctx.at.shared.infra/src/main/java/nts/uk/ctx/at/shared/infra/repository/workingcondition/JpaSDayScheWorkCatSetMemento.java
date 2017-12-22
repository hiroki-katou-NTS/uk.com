/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCatPK;

/**
 * The Class JpaSingleDayScheduleSetMemento.
 */
public class JpaSDayScheWorkCatSetMemento implements SingleDayScheduleSetMemento {

	/** The entity. */
	private KshmtPerWorkCat entity;

	/**
	 * Instantiates a new jpa single day schedule set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheWorkCatSetMemento(KshmtPerWorkCat entity) {
		if (entity.getKshmtPerWorkCatPK() == null) {
			entity.setKshmtPerWorkCatPK(new KshmtPerWorkCatPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTypeCode(nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		if (workTypeCode !=null){
			this.entity.setWorkTypeCode(workTypeCode.v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {

		// TODO:
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode.isPresent() && workTimeCode != null){
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		}
	}

}
