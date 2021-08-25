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

	public ExternalImportPersistentHistory(List<DateHistoryItem> period) {
		super(period);
	}

	@Override
	public void exValidateIfCanAdd(DateHistoryItem itemToBeAdded) {
	}
}
