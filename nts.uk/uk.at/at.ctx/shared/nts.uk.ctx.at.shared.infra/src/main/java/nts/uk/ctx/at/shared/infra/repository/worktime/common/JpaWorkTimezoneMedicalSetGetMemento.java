/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComMedical;

/**
 * The Class JpaWorkTimezoneMedicalSetGetMemento.
 */
public class JpaWorkTimezoneMedicalSetGetMemento implements WorkTimezoneMedicalSetGetMemento {

	/** The entity. */
	private KshmtWtComMedical entity;

	/**
	 * Instantiates a new jpa work timezone medical set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneMedicalSetGetMemento(KshmtWtComMedical entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getRoundingSet() {
		return new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getWorkSystemAtr()
	 */
	@Override
	public WorkSystemAtr getWorkSystemAtr() {
		return WorkSystemAtr.valueOf(this.entity.getKshmtWtComMedicalPK().getWorkSysAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetGetMemento
	 * #getApplicationTime()
	 */
	@Override
	public OneDayTime getApplicationTime() {
		return new OneDayTime(this.entity.getAppTime());
	}

}
