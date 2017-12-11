package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class DateHistoryItem.
 */
@Getter
public class DateHistoryItem extends GeneralHistoryItem<DateHistoryItem, DatePeriod, GeneralDate> {

	/**
	 * Instantiates a new date history item.
	 *
	 * @param memento the memento
	 */
	public DateHistoryItem(DateHistoryItemGetMemento memento) {
		super(memento.getHistoryId(), memento.getPeriod());
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DateHistoryItemSetMemento memento) {
		memento.setHistoryId(this.identifier());
		memento.setPeriod(this.span());
	}

}
