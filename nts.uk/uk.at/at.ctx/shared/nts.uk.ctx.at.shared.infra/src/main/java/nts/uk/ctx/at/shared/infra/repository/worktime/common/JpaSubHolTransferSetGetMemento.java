/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcom;

/**
 * The Class JpaSubHolTransferSetGetMemento.
 */
public class JpaSubHolTransferSetGetMemento implements SubHolTransferSetGetMemento {

	/** The entity. */
	private KshmtWtComHdcom entity;

	/**
	 * Instantiates a new jpa sub hol transfer set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSubHolTransferSetGetMemento(KshmtWtComHdcom entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getCertainTime()
	 */
	@Override
	public OneDayTime getCertainTime() {
		return new OneDayTime(this.entity.getCertainTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getUseDivision()
	 */
	@Override
	public boolean getUseDivision() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getDesignatedTime()
	 */
	@Override
	public DesignatedTime getDesignatedTime() {
		return new DesignatedTime(new OneDayTime(this.entity.getOneDayTime()),
				new OneDayTime(this.entity.getHalfDayTime()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetGetMemento#
	 * getSubHolTransferSetAtr()
	 */
	@Override
	public SubHolTransferSetAtr getSubHolTransferSetAtr() {
		return SubHolTransferSetAtr.valueOf(this.entity.getTranferAtr());
	}

}
