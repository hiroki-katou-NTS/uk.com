package nts.uk.ctx.exio.dom.input.canonicalize.history;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;

@AllArgsConstructor
public class ExternalImportContinuousHistory 
	implements ContinuousHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	private List<DateHistoryItem> period;
	
	@Override
	public List<DateHistoryItem> items() {
		return period;
	}
	
	@Override
	public void add(DateHistoryItem itemToBeAdded) {
		
		this.latestStartItem().ifPresent(latest -> {
			latest.shortenEndToAccept(itemToBeAdded);
		});
		ContinuousHistory.super.add(itemToBeAdded);
	}
}
