/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.TimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekTsPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgTsPK;
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

		if (this.entity instanceof KshmtWorkcondCtgTs
				&& ((KshmtWorkcondCtgTs) this.entity).getKshmtWorkcondCtgTsPK() == null) {
			((KshmtWorkcondCtgTs) this.entity)
					.setKshmtWorkcondCtgTsPK(new KshmtWorkcondCtgTsPK(historyId, perAtr, 0));
		}

		if (entity instanceof KshmtWorkcondWeekTs
				&& ((KshmtWorkcondWeekTs) this.entity).getKshmtWorkcondWeekTsPK() == null) {
			((KshmtWorkcondWeekTs) this.entity).setKshmtWorkcondWeekTsPK(
					new KshmtWorkcondWeekTsPK(historyId, perAtr, 0));
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
		// Is KshmtWorkcondCtgTs
		if (this.entity instanceof KshmtWorkcondCtgTs) {
			KshmtWorkcondCtgTsPK kshmtWorkcondCtgTsPK = ((KshmtWorkcondCtgTs) this.entity)
					.getKshmtWorkcondCtgTsPK();
			kshmtWorkcondCtgTsPK.setCnt(workNo);
			((KshmtWorkcondCtgTs) this.entity).setKshmtWorkcondCtgTsPK(kshmtWorkcondCtgTsPK);
		}

		// Is KshmtWorkcondWeekTs
		if (entity instanceof KshmtWorkcondWeekTs) {
			KshmtWorkcondWeekTsPK kshmtWorkcondWeekTsPK = ((KshmtWorkcondWeekTs) this.entity)
					.getKshmtWorkcondWeekTsPK();
			kshmtWorkcondWeekTsPK.setCnt(workNo);
			((KshmtWorkcondWeekTs) this.entity)
					.setKshmtWorkcondWeekTsPK(kshmtWorkcondWeekTsPK);
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
