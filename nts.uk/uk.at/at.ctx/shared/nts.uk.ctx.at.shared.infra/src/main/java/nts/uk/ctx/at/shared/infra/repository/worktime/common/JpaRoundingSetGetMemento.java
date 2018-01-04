/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSet;

/**
 * The Class JpaRoundingSetGetMemento.
 */
public class JpaRoundingSetGetMemento implements RoundingSetGetMemento {

	/** The entity. */
	private KshmtRoundingSet entity;

	/**
	 * Instantiates a new jpa rounding set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaRoundingSetGetMemento(KshmtRoundingSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento#
	 * getRoundingSet()
	 */
	@Override
	public InstantRounding getRoundingSet() {
		return new InstantRounding(FontRearSection.valueOf(this.entity.getFrontRearAtr()),
				RoundingTimeUnit.valueOf(this.entity.getRoundingTimeUnit()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.RoundingSetGetMemento#getSection
	 * ()
	 */
	@Override
	public Superiority getSection() {
		return Superiority.valueOf(this.entity.getKshmtRoundingSetPK().getAtr());
	}

}
