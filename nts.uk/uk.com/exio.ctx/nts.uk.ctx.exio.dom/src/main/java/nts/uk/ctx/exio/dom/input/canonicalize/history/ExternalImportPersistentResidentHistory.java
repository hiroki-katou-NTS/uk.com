package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;

/**
 * 連続かつ永続する履歴（常に1つ以上存在）の汎用クラス
 */
@AllArgsConstructor
public class ExternalImportPersistentResidentHistory implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	private List<DateHistoryItem> period;
	
	@Override
	public void exValidateIfCanAdd(DateHistoryItem itemToBeAdded) {
	}
	
	@Override
	public List<DateHistoryItem> items() {
		return period;
	}
}
