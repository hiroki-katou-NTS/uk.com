package nts.uk.shr.com.history;

import nts.arc.time.GeneralDate;
import nts.arc.time.period.DatePeriod;

/**
 * Item of history that has a days period.
 * 年月日期間の汎用履歴項目
 */
public class DateHistoryItem extends GeneralHistoryItem<DateHistoryItem, DatePeriod, GeneralDate> {

	/**
	 * Constructs.
	 * 
	 * @param historyId history ID
	 * @param period period
	 */
	public DateHistoryItem(String historyId, DatePeriod period) {
		super(historyId, period);
	}

}
