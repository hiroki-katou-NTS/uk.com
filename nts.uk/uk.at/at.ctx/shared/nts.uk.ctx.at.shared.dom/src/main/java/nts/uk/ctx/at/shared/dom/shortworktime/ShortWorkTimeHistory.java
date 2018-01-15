/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ShortWorkTimeHistory.
 */
// 短時間勤務履歴
@Getter
public class ShortWorkTimeHistory extends AggregateRoot
		implements UnduplicatableHistory<DateHistoryItem, DatePeriod, GeneralDate> {

	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The history item. */
	// 履歴項目
	private List<DateHistoryItem> historyItems;

	/**
	 * Instantiates a new short work time history.
	 *
	 * @param memento the memento
	 */
	public ShortWorkTimeHistory(SWorkTimeHistGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employeeId = memento.getEmployeeId();
		this.historyItems = memento.getHistoryItems();
	}
	
	public ShortWorkTimeHistory(String cid, String sid,  List<DateHistoryItem> historyItems) {
		this.companyId = cid;
		this.employeeId = sid;
		this.historyItems = historyItems;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SWorkTimeHistSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmployeeId(this.employeeId);
		memento.setHistoryItems(this.historyItems);
	}
	
	/**
	 * Items.
	 *
	 * @return the list
	 */
	@Override
	public List<DateHistoryItem> items() {
		return this.historyItems;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((historyItems == null) ? 0 : historyItems.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortWorkTimeHistory other = (ShortWorkTimeHistory) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (historyItems == null) {
			if (other.historyItems != null)
				return false;
		} else if (!historyItems.equals(other.historyItems))
			return false;
		return true;
	}
	
}
