/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComLatetime;

/**
 * The Class JpaOtherEmTimezoneLateEarlySetGetMemento.
 */
public class JpaOtherEmTimezoneLateEarlySetGetMemento
		implements OtherEmTimezoneLateEarlySetGetMemento {

	/** The entity. */
	private KshmtWtComLatetime entity;

	/**
	 * Instantiates a new jpa other em timezone late early set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaOtherEmTimezoneLateEarlySetGetMemento(KshmtWtComLatetime entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getDelTimeRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getDelTimeRoundingSet() {
		return new TimeRoundingSetting(this.entity.getDeductionUnit(),
				this.entity.getDeductionRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getStampExactlyTimeIsLateEarly()
	 */
	@Override
	public boolean getStampExactlyTimeIsLateEarly() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getExtractLateEarlyTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getGraceTimeSet()
	 */
	@Override
	public GraceTimeSetting getGraceTimeSet() {
		return new GraceTimeSetting(BooleanGetAtr.getAtrByInteger(this.entity.getIncludeWorktime()),
				new LateEarlyGraceTime(this.entity.getGraceTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getRecordTimeRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getRecordTimeRoundingSet() {
		return new TimeRoundingSetting(this.entity.getRecordUnit(),
				this.entity.getRecordRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * OtherEmTimezoneLateEarlySetGetMemento#getLateEarlyAtr()
	 */
	@Override
	public LateEarlyAtr getLateEarlyAtr() {
		return LateEarlyAtr.valueOf(this.entity.getKshmtWtComLatetimePK().getLateEarlyAtr());
	}

}
