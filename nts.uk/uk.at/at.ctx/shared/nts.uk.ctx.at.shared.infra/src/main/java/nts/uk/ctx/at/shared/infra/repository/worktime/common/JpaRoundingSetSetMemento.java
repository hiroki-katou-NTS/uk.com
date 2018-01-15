/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSet;

/**
 * The Class JpaRoundingSetSetMemento.
 */
public class JpaRoundingSetSetMemento implements RoundingSetSetMemento {

	/** The entity. */
	private KshmtRoundingSet entity;

	/**
	 * Instantiates a new jpa rounding set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaRoundingSetSetMemento(KshmtRoundingSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento#
	 * setRoundingSet(nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding)
	 */
	@Override
	public void setRoundingSet(InstantRounding rounding) {
		this.entity.setFrontRearAtr(rounding.getFontRearSection().value);
		this.entity.setRoundingTimeUnit(rounding.getRoundingTimeUnit().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetSetMemento#setSection
	 * (nts.uk.ctx.at.shared.dom.worktime.common.Superiority)
	 */
	@Override
	public void setSection(Superiority sec) {
		this.entity.getKshmtRoundingSetPK().setAtr(sec.value);
	}

}
