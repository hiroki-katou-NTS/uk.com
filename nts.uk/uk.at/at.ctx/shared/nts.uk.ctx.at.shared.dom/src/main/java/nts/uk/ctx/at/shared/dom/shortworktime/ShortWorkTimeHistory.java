/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.Arrays;
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

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The history item. */
	// 履歴項目
	private DateHistoryItem historyItem;

	/**
	 * Instantiates a new short work time history.
	 *
	 * @param memento the memento
	 */
	public ShortWorkTimeHistory(SWorkTimeHistGetMemento memento) {
		this.employeeId = memento.getEmployeeId();
		this.historyItem = memento.getHistoryItem();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SWorkTimeHistSetMemento memento) {
		memento.setEmployeeId(this.employeeId);
		memento.setHistoryItem(this.historyItem);
	}
	
	/**
	 * Items.
	 *
	 * @return the list
	 */
	@Override
	public List<DateHistoryItem> items() {
		return Arrays.asList(this.historyItem);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((historyItem == null) ? 0 : historyItem.hashCode());
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
		if (historyItem == null) {
			if (other.historyItem != null)
				return false;
		} else if (!historyItem.equals(other.historyItem))
			return false;
		return true;
	}
	
}
