/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaFixHalfDayWorkTimezoneSetMemento.
 */
public class JpaFixHalfDayWorkTimezoneSetMemento implements FixHalfDayWorkTimezoneSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/** The type. */
	private int type;

	/**
	 * Instantiates a new jpa fix half day work timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 * @param type
	 *            the type
	 */
	public JpaFixHalfDayWorkTimezoneSetMemento(KshmtFixedWorkSet entity, AmPmAtr type) {
		super();
		if (entity.getKshmtFixedWorkSetPK() == null) {
			entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
		this.entity = entity;
		this.type = type.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setRestTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.fixedset.FixRestTimezoneSet)
	 */
	@Override
	public void setRestTimezone(FixRestTimezoneSet restTimezone) {
		restTimezone.saveToMemento(new JpaFixRestHalfdayTzSetMemento(this.entity.getKshmtFixedHalfRestSets(),
				this.entity.getKshmtFixedWorkSetPK().getCid(), this.entity.getKshmtFixedWorkSetPK().getWorktimeCd(),
				this.type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setWorkTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.common.FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		workTimezone.saveToMemento(new JpaFixedWorkTimezoneSetSetMemento(this.entity.getKshmtFixedWorkTimeSets(),
				this.entity.getKshmtFixedOtTimeSets(), this.entity.getKshmtFixedWorkSetPK().getCid(),
				this.entity.getKshmtFixedWorkSetPK().getWorktimeCd(), this.type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setDayAtr(nts.uk.ctx.at.shared.dom.
	 * worktime.common.AmPmAtr)
	 */
	@Override
	public void setDayAtr(AmPmAtr type) {
		this.type = type.value;
	}

}
