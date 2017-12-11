/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaSWorkTimeHistGetMemento.
 */
public class JpaSWorkTimeHistGetMemento implements SWorkTimeHistGetMemento {

	/** The entity. */
	private BshmtWorktimeHist entity;
	
	/**
	 * Instantiates a new jpa S work time hist get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSWorkTimeHistGetMemento(BshmtWorktimeHist entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getBshmtWorktimeHistPK().getSid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#getHistoryItem()
	 */
	@Override
	public DateHistoryItem getHistoryItem() {
		return new DateHistoryItem(this.entity.getBshmtWorktimeHistPK().getHistId(),
				new DatePeriod(this.entity.getStrYmd(), this.entity.getEndYmd()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getCId();
	}

}
