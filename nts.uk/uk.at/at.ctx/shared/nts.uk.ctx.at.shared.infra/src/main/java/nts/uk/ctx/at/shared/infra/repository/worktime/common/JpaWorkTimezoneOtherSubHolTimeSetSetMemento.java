/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcomPK;

/**
 * The Class JpaWorkTimezoneOtherSubHolTimeSetSetMemento.
 */
public class JpaWorkTimezoneOtherSubHolTimeSetSetMemento implements WorkTimezoneOtherSubHolTimeSetSetMemento {

	/** The entity. */
	private KshmtWtComHdcom entity;

	/**
	 * Instantiates a new jpa work timezone other sub hol time set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneOtherSubHolTimeSetSetMemento(KshmtWtComHdcom entity) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtWtComHdcomPK() == null) {
			this.entity.setKshmtWtComHdcomPK(new KshmtWtComHdcomPK());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetSetMemento#setSubHolTimeSet(nts.uk.ctx.at.
	 * shared.dom.worktime.common.SubHolTransferSet)
	 */
	@Override
	public void setSubHolTimeSet(SubHolTransferSet set) {
		set.saveToMemento(new JpaSubHolTransferSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetSetMemento#setWorkTimeCode(nts.uk.ctx.at.
	 * shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode cd) {
		this.entity.getKshmtWtComHdcomPK().setWorktimeCd(cd.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetSetMemento#setOriginAtr(nts.uk.ctx.at.
	 * shared.dom.worktime.common.CompensatoryOccurrenceDivision)
	 */
	@Override
	public void setOriginAtr(CompensatoryOccurrenceDivision atr) {
		this.entity.getKshmtWtComHdcomPK().setOriginAtr(atr.value);
	}

}
