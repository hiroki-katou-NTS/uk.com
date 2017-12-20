package nts.uk.shr.sample.history.jobposition;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@RequiredArgsConstructor
public class SampleJobPositionMain implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
	
	@Getter
	private final String employeeId;

	@Getter
	private final List<DateHistoryItem> historyItems;

	@Override
	public List<DateHistoryItem> items() {
		return this.historyItems;
	}

}
