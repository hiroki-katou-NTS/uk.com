/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;

/**
 * The Class JpaFixHalfDayWorkTimezoneSetMemento.
 */
public class JpaFixHalfDayWorkTimezoneSetMemento implements FixHalfDayWorkTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFix entity;

	/** The type. */
	private AmPmAtr type;

	/**
	 * Instantiates a new jpa fix half day work timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 * @param type
	 *            the type
	 */
	public JpaFixHalfDayWorkTimezoneSetMemento(KshmtWtFix entity, AmPmAtr type) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtFixedWorkSetPK() == null) {
			this.entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}	
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneSetMemento#setRestTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.fixedset.FixRestTimezoneSet)
	 */
	@Override
	public void setRestTimezone(TimezoneOfFixedRestTimeSet restTimezone) {
		restTimezone.saveToMemento(new JpaFixRestHalfdayTzSetMemento(this.entity,
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
		workTimezone.saveToMemento(new JpaFixedWorkTimezoneSetSetMemento(this.entity, this.type.value));
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
		this.type = type;
	}

}
