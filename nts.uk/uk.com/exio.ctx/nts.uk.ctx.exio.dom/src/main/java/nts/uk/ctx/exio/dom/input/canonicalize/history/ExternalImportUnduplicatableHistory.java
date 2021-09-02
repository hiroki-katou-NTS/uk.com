package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;

/**
 * 重複禁止する履歴の汎用クラス
 */
public class ExternalImportUnduplicatableHistory
	extends ExternalImportHistory
	implements UnduplicatableHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	public ExternalImportUnduplicatableHistory(String employeeId, List<DateHistoryItem> period) {
		super(employeeId, period);
	}

}
