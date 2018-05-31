/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySet;

/**
 * The Class JpaPrioritySettingGetMemento.
 */
public class JpaPrioritySettingGetMemento implements PrioritySettingGetMemento {

	/** The entity. */
	private KshmtPioritySet entity;

	/**
	 * Instantiates a new jpa priority setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaPrioritySettingGetMemento(KshmtPioritySet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento#
	 * getPriorityAtr()
	 */
	@Override
	public MultiStampTimePiorityAtr getPriorityAtr() {
		return MultiStampTimePiorityAtr.valueOf(this.entity.getKshmtPioritySetPK().getPiorityAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.PrioritySettingGetMemento#
	 * getStampAtr()
	 */
	@Override
	public StampPiorityAtr getStampAtr() {
		return StampPiorityAtr.valueOf(this.entity.getKshmtPioritySetPK().getStampAtr());
	}

}
