/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHist;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Class JpaSWorkTimeHistSetMemento.
 */
public class JpaSWorkTimeHistSetMemento implements SWorkTimeHistSetMemento {

	/** The entity. */
	private BshmtWorktimeHist entity;

	/**
	 * Instantiates a new jpa S work time hist set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistSetMemento(BshmtWorktimeHist entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String empId) {
		this.entity.getBshmtWorktimeHistPK().setSid(empId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setHistoryItem(nts.uk.shr.com.history.DateHistoryItem)
	 */
	@Override
	public void setHistoryItem(DateHistoryItem historyItem) {
		this.entity.getBshmtWorktimeHistPK().setHistId(historyItem.identifier());
		this.entity.setStrYmd(historyItem.start());
		this.entity.setEndYmd(historyItem.end());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String cid) {
		this.entity.setCId(cid);
	}

}
