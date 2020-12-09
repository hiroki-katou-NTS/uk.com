/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcom;

/**
 * The Class JpaSubHolTransferSetSetMemento.
 */
public class JpaSubHolTransferSetSetMemento implements SubHolTransferSetSetMemento {

	/** The entity. */
	private KshmtWtComHdcom entity;

	/**
	 * Instantiates a new jpa sub hol transfer set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSubHolTransferSetSetMemento(KshmtWtComHdcom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setCertainTime(nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime)
	 */
	@Override
	public void setCertainTime(OneDayTime time) {
		this.entity.setCertainTime(time.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setUseDivision(boolean)
	 */
	@Override
	public void setUseDivision(boolean val) {
		this.entity.setUseAtr(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setDesignatedTime(nts.uk.ctx.at.shared.dom.worktime.common.
	 * DesignatedTime)
	 */
	@Override
	public void setDesignatedTime(DesignatedTime time) {
		this.entity.setOneDayTime(time.getOneDayTime().valueAsMinutes());
		this.entity.setHalfDayTime(time.getHalfDayTime().valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetSetMemento#
	 * setSubHolTransferSetAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * SubHolTransferSetAtr)
	 */
	@Override
	public void setSubHolTransferSetAtr(SubHolTransferSetAtr atr) {
		this.entity.setTranferAtr(atr.value);
	}
}
