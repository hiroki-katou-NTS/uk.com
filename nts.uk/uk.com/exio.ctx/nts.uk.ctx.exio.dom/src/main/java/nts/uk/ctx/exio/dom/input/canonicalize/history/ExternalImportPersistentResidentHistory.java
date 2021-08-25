package nts.uk.ctx.exio.dom.input.canonicalize.history;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;

/**
 * 連続かつ永続する履歴（常に1つ以上存在）の汎用クラス
 */
@AllArgsConstructor
public class ExternalImportPersistentResidentHistory 
	extends ExternalImportContinuousHistory 
	implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate>{
	
	@Override
	public void exValidateIfCanAdd(DateHistoryItem itemToBeAdded) {
	}
	
}
