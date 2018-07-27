/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySet;

/**
 * The Class JpaPrioritySettingSetMemento.
 */
public class JpaPrioritySettingSetMemento implements PrioritySettingSetMemento {

	/** The entity. */
	private KshmtPioritySet entity;

	/**
	 * Instantiates a new jpa priority setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaPrioritySettingSetMemento(KshmtPioritySet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento#
	 * setPriorityAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * MultiStampTimePiorityAtr)
	 */
	@Override
	public void setPriorityAtr(MultiStampTimePiorityAtr atr) {
		//Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingSetMemento#
	 * setStampAtr(nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr)
	 */
	@Override
	public void setStampAtr(StampPiorityAtr atr) {
		//Do nothing
	}

}
