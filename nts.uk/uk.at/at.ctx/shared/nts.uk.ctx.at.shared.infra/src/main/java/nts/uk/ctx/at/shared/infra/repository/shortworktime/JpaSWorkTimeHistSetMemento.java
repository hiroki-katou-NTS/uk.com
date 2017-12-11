/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHist;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistPK;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaSWorkTimeHistSetMemento.
 */
public class JpaSWorkTimeHistSetMemento implements SWorkTimeHistSetMemento {

	/** The entity. */
	private List<BshmtWorktimeHist> entities;

	/**
	 * Instantiates a new jpa S work time hist set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistSetMemento(List<BshmtWorktimeHist> entities) {
		entities.stream().forEach(item -> {
			if (item.getBshmtWorktimeHistPK() == null) {
				item.setBshmtWorktimeHistPK(new BshmtWorktimeHistPK());
			}
		});
		this.entities = entities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String empId) {
		this.entities.stream().forEach(item -> {
			BshmtWorktimeHistPK bshmtWorktimeHistPK = item.getBshmtWorktimeHistPK();
			bshmtWorktimeHistPK.setSid(empId);
			item.setBshmtWorktimeHistPK(bshmtWorktimeHistPK);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setHistoryItem(nts.uk.shr.com.history.DateHistoryItem)
	 */
	@Override
	public void setHistoryItems(List<DateHistoryItem> historyItems) {
		Map<String, DatePeriod> mapHistoryItem = historyItems.stream().collect(Collectors.toMap(DateHistoryItem::identifier, DateHistoryItem::span));
		this.entities.stream().forEach(item -> {
			item.setStrYmd(mapHistoryItem.get(item.getBshmtWorktimeHistPK().getHistId()).start());
			item.setEndYmd(mapHistoryItem.get(item.getBshmtWorktimeHistPK().getHistId()).end());
		});
		
		// TODO: Check again
		List<String> existHistIds = this.entities.stream().map(item -> {
			return item.getBshmtWorktimeHistPK().getHistId();
		}).collect(Collectors.toList());
		
		historyItems.stream().forEach(item -> {
			BshmtWorktimeHistPK bshmtWorktimeHistPK = new BshmtWorktimeHistPK();
			if(!existHistIds.contains(item.identifier())){
				bshmtWorktimeHistPK.setHistId(bshmtWorktimeHistPK.getHistId());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String cid) {
		this.entities.stream().forEach(item -> {
			item.setCId(cid);
		});
	}

}
