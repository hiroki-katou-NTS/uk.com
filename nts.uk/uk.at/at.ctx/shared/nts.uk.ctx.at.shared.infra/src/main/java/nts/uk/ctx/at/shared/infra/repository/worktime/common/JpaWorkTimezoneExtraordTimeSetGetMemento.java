/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordTimeCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramset;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComTemporary;

/**
 * The Class JpaWorkTimezoneExtraordTimeSetGetMemento.
 */
public class JpaWorkTimezoneExtraordTimeSetGetMemento
		implements WorkTimezoneExtraordTimeSetGetMemento {

	/** The entity. */
	private KshmtWtComTemporary entity;

	/**
	 * Instantiates a new jpa work timezone extraord time set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneExtraordTimeSetGetMemento(KshmtWtComTemporary entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetGetMemento#getHolidayFrameSet()
	 */
	@Override
	public HolidayFramset getHolidayFrameSet() {
		return new HolidayFramset(new BreakoutFrameNo(this.entity.getInLegalBreakFrameNo()),
				new BreakoutFrameNo(this.entity.getOutLegalBreakFrameNo()),
				new BreakoutFrameNo(this.entity.getOutLegalPubHolFrameNo()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetGetMemento#getTimeRoundingSet()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSet() {
		return new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetGetMemento#getOTFrameSet()
	 */
	@Override
	public ExtraordWorkOTFrameSet getOTFrameSet() {
		return new ExtraordWorkOTFrameSet(new OTFrameNo(this.entity.getOtFrameNo()),
				new OTFrameNo(this.entity.getInLegalBreakFrameNo()),
				new SettlementOrder(this.entity.getSettlementOrder()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetGetMemento#getCalculateMethod()
	 */
	@Override
	public ExtraordTimeCalculateMethod getCalculateMethod() {
		return ExtraordTimeCalculateMethod.valueOf(this.entity.getCalcMethod());
	}

}
