/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtDayofweekTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtDayofweekTimeZonePK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkCatTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkCatTimeZonePK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaTimezoneSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaTimezoneSetMemento<T extends KshmtTimeZone> implements TimezoneSetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa timezone set memento.
	 *
	 * @param historyId
	 *            the history id
	 * @param perAtr
	 *            the per atr
	 * @param entity
	 *            the entity
	 */
	public JpaTimezoneSetMemento(String historyId, int perAtr, T entity) {
		super();
		this.entity = entity;

		if (this.entity instanceof KshmtWorkCatTimeZone
				&& ((KshmtWorkCatTimeZone) this.entity).getKshmtWorkCatTimeZonePK() == null) {
			((KshmtWorkCatTimeZone) this.entity)
					.setKshmtWorkCatTimeZonePK(new KshmtWorkCatTimeZonePK(historyId, perAtr, 0));
		}

		if (entity instanceof KshmtDayofweekTimeZone
				&& ((KshmtDayofweekTimeZone) this.entity).getKshmtDayofweekTimeZonePK() == null) {
			((KshmtDayofweekTimeZone) this.entity).setKshmtDayofweekTimeZonePK(
					new KshmtDayofweekTimeZonePK(historyId, perAtr, 0));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento#setUseAtr(
	 * nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr)
	 */
	@Override
	public void setUseAtr(NotUseAtr useAtr) {
		this.entity.setUseAtr(useAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento#setCnt(int)
	 */
	@Override
	public void setCnt(int workNo) {
		// Is KshmtWorkCatTimeZone
		if (this.entity instanceof KshmtWorkCatTimeZone) {
			KshmtWorkCatTimeZonePK kshmtWorkCatTimeZonePK = ((KshmtWorkCatTimeZone) this.entity)
					.getKshmtWorkCatTimeZonePK();
			kshmtWorkCatTimeZonePK.setCnt(workNo);
			((KshmtWorkCatTimeZone) this.entity).setKshmtWorkCatTimeZonePK(kshmtWorkCatTimeZonePK);
		}

		// Is KshmtDayofweekTimeZone
		if (entity instanceof KshmtDayofweekTimeZone) {
			KshmtDayofweekTimeZonePK kshmtDayofweekTimeZonePK = ((KshmtDayofweekTimeZone) this.entity)
					.getKshmtDayofweekTimeZonePK();
			kshmtDayofweekTimeZonePK.setCnt(workNo);
			((KshmtDayofweekTimeZone) this.entity)
					.setKshmtDayofweekTimeZonePK(kshmtDayofweekTimeZonePK);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento#setStart(nts
	 * .uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setStartTime(start.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento#setEnd(nts.
	 * uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setEndTime(end.v());
	}

}
