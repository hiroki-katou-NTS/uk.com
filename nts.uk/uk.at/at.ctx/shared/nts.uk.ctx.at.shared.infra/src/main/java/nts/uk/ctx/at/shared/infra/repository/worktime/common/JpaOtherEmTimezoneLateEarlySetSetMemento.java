/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtOtherLateEarly;

/**
 * The Class JpaOtherEmTimezoneLateEarlySetSetMemento.
 */
public class JpaOtherEmTimezoneLateEarlySetSetMemento implements OtherEmTimezoneLateEarlySetSetMemento {

	/** The entity. */
	private KshmtOtherLateEarly entity;

	/**
	 * Instantiates a new jpa other em timezone late early set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOtherEmTimezoneLateEarlySetSetMemento(KshmtOtherLateEarly entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setDelTimeRoundingSet(nts.uk.ctx.at
	 * .shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setDelTimeRoundingSet(TimeRoundingSetting delTimeRoundingSet) {
		this.entity.setDeductionUnit(delTimeRoundingSet.getRoundingTime().value);
		this.entity.setDeductionRounding(delTimeRoundingSet.getRounding().value);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setGraceTimeSet(nts.uk.ctx.at.
	 * shared.dom.worktime.common.GraceTimeSetting)
	 */
	@Override
	public void setGraceTimeSet(GraceTimeSetting graceTimeSet) {
		this.entity.setIncludeWorktime(BooleanGetAtr.getAtrByBoolean(graceTimeSet.isIncludeWorkingHour()));
		this.entity.setGraceTime(graceTimeSet.getGraceTime().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setRecordTimeRoundingSet(nts.uk.ctx
	 * .at.shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setRecordTimeRoundingSet(TimeRoundingSetting recordTimeRoundingSet) {
		this.entity.setRecordUnit(recordTimeRoundingSet.getRoundingTime().value);
		this.entity.setRecordRounding(recordTimeRoundingSet.getRounding().value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetSetMemento#setLateEarlyAtr(nts.uk.ctx.at.
	 * shared.dom.worktime.common.LateEarlyAtr)
	 */
	@Override
	public void setLateEarlyAtr(LateEarlyAtr lateEarlyAtr) {
		this.entity.getKshmtOtherLateEarlyPK().setLateEarlyAtr(lateEarlyAtr.value);
	}

}
