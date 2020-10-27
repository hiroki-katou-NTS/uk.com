/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.TimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtgTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaTimezoneGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaTimezoneGetMemento<T extends KshmtTimeZone> implements TimezoneGetMemento {

	/** The entity. */
	private T entity;

	/**
	 * Instantiates a new jpa timezone get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaTimezoneGetMemento(T entity) {
		super();

		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneGetMemento#getUseAtr()
	 */
	@Override
	public NotUseAtr getUseAtr() {
		return NotUseAtr.valueOf(this.entity.getUseAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneGetMemento#getCnt()
	 */
	@Override
	public int getCnt() {
		if (entity instanceof KshmtWorkcondCtgTs) {
			return ((KshmtWorkcondCtgTs) this.entity).getKshmtWorkcondCtgTsPK().getCnt();
		}

		return ((KshmtWorkcondWeekTs) this.entity).getKshmtWorkcondWeekTsPK().getCnt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStartTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.TimezoneGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

}
