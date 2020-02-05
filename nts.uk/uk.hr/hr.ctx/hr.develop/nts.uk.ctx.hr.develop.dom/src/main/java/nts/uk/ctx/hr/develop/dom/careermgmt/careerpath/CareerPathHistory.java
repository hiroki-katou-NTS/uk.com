package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.arc.time.calendar.period.DatePeriod;

/**キャリアパスの履歴*/
@AllArgsConstructor
@Getter
public class CareerPathHistory extends AggregateRoot implements PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate>{

	private String companyId;
	
	private List<DateHistoryItem> careerPathHistory;

	@Override
	public List<DateHistoryItem> items() {
		// TODO Auto-generated method stub
		return careerPathHistory;
	}
	
	public List<String> getHistoryIds() {
		return careerPathHistory.stream().map( item -> item.identifier()).collect(Collectors.toList());
	}
}
