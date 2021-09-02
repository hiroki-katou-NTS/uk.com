package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;

/**
 * 連続かつ永続する履歴の汎用クラス
 */
public class ExternalImportPersistentHistory
	extends ExternalImportContinuousHistory
	implements PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	public ExternalImportPersistentHistory(String employeeId,List<DateHistoryItem> period) {
		super(employeeId, period);
	}

	@Override
	public void add(DateHistoryItem itemToBeAdded) {
		itemToBeAdded.changeSpan(new DatePeriod(itemToBeAdded.start(), GeneralDate.max()));
		super.add(itemToBeAdded);
	}
	
	@Override
	public void exValidateIfCanAdd(DateHistoryItem itemToBeAdded) {
	}
}
