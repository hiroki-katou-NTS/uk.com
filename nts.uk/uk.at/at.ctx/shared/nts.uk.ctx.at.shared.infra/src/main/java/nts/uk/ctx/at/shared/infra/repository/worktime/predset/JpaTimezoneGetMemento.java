/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaTimezoneGetMemento.
 */
public class JpaTimezoneGetMemento implements TimezoneGetMemento{

	/** The entity. */
	private KshmtWtComPredTs entity;
	
	/**
	 * Instantiates a new jpa timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaTimezoneGetMemento(KshmtWtComPredTs entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getUseAtr()
	 */
	@Override
	public UseSetting getUseAtr() {
		return UseSetting.valueOf(this.entity.getUseAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getWorkNo()
	 */
	@Override
	public int getWorkNo() {
		return this.entity.getKshmtWtComPredTsPK().getWorkNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getStart()
	 */
	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStartTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneGetMemento#getEnd()
	 */
	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}
	
}
