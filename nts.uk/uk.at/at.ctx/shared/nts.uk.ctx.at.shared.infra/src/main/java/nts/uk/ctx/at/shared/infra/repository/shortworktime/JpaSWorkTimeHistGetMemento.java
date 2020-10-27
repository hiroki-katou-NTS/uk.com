/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaSWorkTimeHistGetMemento.
 */
public class JpaSWorkTimeHistGetMemento implements SWorkTimeHistGetMemento {

	private final static int FIRST_ITEM_INDEX = 0;

	/** The entity. */
	private List<KshmtShorttimeHist> entities;

	/**
	 * Instantiates a new jpa S work time hist get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistGetMemento(List<KshmtShorttimeHist> entities) {
		this.entities = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entities.get(FIRST_ITEM_INDEX).getKshmtShorttimeHistPK().getSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#
	 * getHistoryItem()
	 */
	@Override
	public List<DateHistoryItem> getHistoryItems() {
		return this.entities.stream()
				.map(item -> new DateHistoryItem(item.getKshmtShorttimeHistPK().getHistId(),
						new DatePeriod(item.getStrYmd(), item.getEndYmd())))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entities.get(FIRST_ITEM_INDEX).getCId();
	}

}
