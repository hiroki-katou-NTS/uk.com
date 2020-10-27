/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComMedical;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComMedicalPK;

/**
 * The Class JpaWorkTimezoneMedicalSetSetMemento.
 */
public class JpaWorkTimezoneMedicalSetSetMemento implements WorkTimezoneMedicalSetSetMemento {

	/** The entity. */
	private KshmtWtComMedical entity;

	/**
	 * Instantiates a new jpa work timezone medical set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneMedicalSetSetMemento(KshmtWtComMedical entity) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtWtComMedicalPK() == null) {
			this.entity.setKshmtWtComMedicalPK(new KshmtWtComMedicalPK());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setRoundingSet(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setRoundingSet(TimeRoundingSetting set) {
		this.entity.setUnit(set.getRoundingTime().value);
		this.entity.setRounding(set.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setWorkSystemAtr(nts.uk.ctx.at.shared.dom.worktime.common.WorkSystemAtr)
	 */
	@Override
	public void setWorkSystemAtr(WorkSystemAtr atr) {
		this.entity.getKshmtWtComMedicalPK().setWorkFormAtr(atr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSetSetMemento
	 * #setApplicationTime(nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime)
	 */
	@Override
	public void setApplicationTime(OneDayTime time) {
		this.entity.setAppTime(time.valueAsMinutes());
	}

}
