package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間と労働条件項目を同時に保持するクラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class WorkingConditionWithDataPeriod {
	//期間、期間に対応した労働条件項目
	Map<DateHistoryItem, WorkingConditionItem> mappingItems;

	
	public WorkingConditionWithDataPeriod(Map<DateHistoryItem, WorkingConditionItem> mappingItems) {
		super();
		this.mappingItems = mappingItems;
	}
	
	public Optional<Entry<DateHistoryItem, WorkingConditionItem>> getItemAtDate(GeneralDate date){
		return this.mappingItems.entrySet().stream().filter(c -> {
				   return c.getKey().contains(GeneralDate.today());
			   }).findFirst();
	}
	
	/**
	 * 期間の取得
	 * @return
	 */
	public List<DatePeriod> getPeriod(){
		List<DatePeriod> returnList = new ArrayList<>();
		for(DateHistoryItem item: this.mappingItems.keySet()) {
			returnList.add(item.span());
		}
		return returnList;
	}
}
