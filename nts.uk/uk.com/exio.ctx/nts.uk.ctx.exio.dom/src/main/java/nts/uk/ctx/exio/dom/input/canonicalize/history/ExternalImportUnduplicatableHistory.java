package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;

/**
 * 重複禁止する履歴の汎用クラス
 */
@AllArgsConstructor
public class ExternalImportUnduplicatableHistory implements UnduplicatableHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	private List<DateHistoryItem> period;
	
	@Override
	public List<DateHistoryItem> items() {
		return period;
	}

}
