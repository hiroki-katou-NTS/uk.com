package nts.uk.ctx.pereg.dom.person.setting.selectionitem.history;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class SelectionHistory implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate>{
	
	// domain name: 選択肢履歴
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 選択項目ID
	 */
	private String selectionItemId;
	
	/**
	 * 履歴
	 */
	private List<DateHistoryItem> dateHistoryItems;
	
	@Override
	public List<DateHistoryItem> items() {
		return dateHistoryItems;
	}

}
