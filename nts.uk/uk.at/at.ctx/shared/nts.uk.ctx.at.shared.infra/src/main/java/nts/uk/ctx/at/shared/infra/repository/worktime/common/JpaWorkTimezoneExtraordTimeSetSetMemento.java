/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordTimeCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramset;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComTemporary;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComTemporaryPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;

/**
 * The Class JpaWorkTimezoneExtraordTimeSetSetMemento.
 */
public class JpaWorkTimezoneExtraordTimeSetSetMemento implements WorkTimezoneExtraordTimeSetSetMemento {

	/** The entity. */
	private KshmtWtComTemporary entity;

	/**
	 * Instantiates a new jpa work timezone extraord time set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneExtraordTimeSetSetMemento(KshmtWtCom parentEntity) {
		super();
		this.initialEntity(parentEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setHolidayFrameSet(nts.uk.ctx.at.
	 * shared.dom.worktime.common.HolidayFramset)
	 */
	@Override
	public void setHolidayFrameSet(HolidayFramset set) {
		this.entity.setInLegalBreakFrameNo(set.getInLegalBreakoutFrameNo().v());
		this.entity.setOutLegalBreakFrameNo(set.getOutLegalBreakoutFrameNo().v());
		this.entity.setOutLegalPubHolFrameNo(set.getOutLegalPubHolFrameNo().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setTimeRoundingSet(nts.uk.ctx.at.
	 * shared.dom.common.timerounding.TimeRoundingSetting)
	 */
	@Override
	public void setTimeRoundingSet(TimeRoundingSetting set) {
		this.entity.setUnit(set.getRoundingTime().value);
		this.entity.setRounding(set.getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setOTFrameSet(nts.uk.ctx.at.shared.
	 * dom.worktime.common.ExtraordWorkOTFrameSet)
	 */
	@Override
	public void setOTFrameSet(ExtraordWorkOTFrameSet set) {
		this.entity.setOtFrameNo(set.getOtFrameNo().v());
		this.entity.setInLegalBreakFrameNo(set.getInLegalWorkFrameNo().v());
		this.entity.setSettlementOrder(set.getSettlementOrder().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSetSetMemento#setCalculateMethod(nts.uk.ctx.at.
	 * shared.dom.worktime.common.ExtraordTimeCalculateMethod)
	 */
	@Override
	public void setCalculateMethod(ExtraordTimeCalculateMethod method) {
		this.entity.setCalcMethod(method.value);
	}

	/**
	 * Initial entity.
	 *
	 * @param parentEntity
	 *            the parent entity
	 */
	private void initialEntity(KshmtWtCom parentEntity) {
		if (parentEntity.getKshmtWtComTemporary() == null) {
			parentEntity.setKshmtWtComTemporary(new KshmtWtComTemporary());
		}
		
		// check existed key
		if (parentEntity.getKshmtWtComTemporary().getKshmtWtComTemporaryPK() == null) {
			// set primary key
			KshmtWtComTemporaryPK pk = new KshmtWtComTemporaryPK();
			pk.setCid(parentEntity.getKshmtWtComPK().getCid());
			pk.setWorktimeCd(parentEntity.getKshmtWtComPK().getWorktimeCd());
			pk.setWorkFormAtr(parentEntity.getKshmtWtComPK().getWorkFormAtr());
			pk.setWorktimeSetMethod(parentEntity.getKshmtWtComPK().getWorktimeSetMethod());
			parentEntity.getKshmtWtComTemporary().setKshmtWtComTemporaryPK(pk);
		}

		// set entity
		this.entity = parentEntity.getKshmtWtComTemporary();
	}

}
