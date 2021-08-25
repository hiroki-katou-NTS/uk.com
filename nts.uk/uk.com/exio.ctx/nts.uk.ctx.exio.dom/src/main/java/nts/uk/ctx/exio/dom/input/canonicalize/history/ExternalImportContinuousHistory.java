package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;

/**
 * 連続する履歴用汎用クラス
 */
public class ExternalImportContinuousHistory
	extends ExternalImportHistory
	implements ContinuousHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	public ExternalImportContinuousHistory(List<DateHistoryItem> period) {
		super(period);
	}

	@Override
	public void add(DateHistoryItem itemToBeAdded) {
		
		this.latestStartItem().ifPresent(latest -> {
			latest.shortenEndToAccept(itemToBeAdded);
		});
		ContinuousHistory.super.add(itemToBeAdded);
	}
}
