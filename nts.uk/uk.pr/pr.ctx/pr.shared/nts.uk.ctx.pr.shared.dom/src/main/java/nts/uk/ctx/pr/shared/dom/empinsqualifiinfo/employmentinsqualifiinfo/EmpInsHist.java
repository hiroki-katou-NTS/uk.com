package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員雇用保険履歴
 */
@Getter
public class EmpInsHist extends AggregateRoot
		implements ContinuousResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {

	/**
	 * 社員ID
	 */
	private String sid;

	/**
	 * 期間
	 */
	private List<DateHistoryItem> historyItem;

	public EmpInsHist(String sid, List<DateHistoryItem> historyItem) {
		this.sid = sid;
		this.historyItem = historyItem;
	}

	@Override
	public List<DateHistoryItem> items() {
		return historyItem;
	}
}
