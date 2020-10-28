package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 並び替え優先順
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author Hieult
 *
 */
@Value
public class OrderedList implements DomainValue{
	
	/**	並び順 **/
	private final SortOrder sortOrder;
	/** 並び替え種類**/
	private final SortType type;
	
	
	public OrderedList(SortOrder sortOrder, SortType type) {
		super();
		this.type = type;
		this.sortOrder = sortOrder;
	}

	
}
