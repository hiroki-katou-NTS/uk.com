package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.HistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt 年月日期間の汎用履歴項目
 */
@Getter
public class DateHistoryItem extends HistoryItem<DatePeriod, GeneralDate> {

	/** 履歴ID */
	private String historyId;

	/** 所属期間 */
	private DatePeriod datePeriod;

	@Override
	public DatePeriod span() {
		return datePeriod;
	}

	@Override
	public String identifier() {
		return historyId;
	}

	@Override
	public void changeSpan(DatePeriod newSpan) {
		datePeriod = newSpan;
	}
}
