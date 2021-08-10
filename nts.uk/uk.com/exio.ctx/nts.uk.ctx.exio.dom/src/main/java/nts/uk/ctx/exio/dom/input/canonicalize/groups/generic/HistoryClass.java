package nts.uk.ctx.exio.dom.input.canonicalize.groups.generic;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

/**
 * 汎用クラス　履歴を持つクラスを取得するための
 */
@AllArgsConstructor
public class HistoryClass implements History<DateHistoryItem, DatePeriod, GeneralDate>{

	private List<DateHistoryItem> period;
	
	@Override
	public List<DateHistoryItem> items() {
		return period;
	}
}
