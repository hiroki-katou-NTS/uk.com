package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 並び替え優先順
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.社員並び替え.並び替え優先順
 * @author Hieult
 *
 */
@Value
public class OrderedList implements DomainValue{
	/** 種類 **/
	private final SortType type;
	
	/**	並び順 **/
	private final SortOrder sortOrder;
	
	public OrderedList(SortType type, SortOrder sortOrder) {
		super();
		this.type = type;
		this.sortOrder = sortOrder;
	}

	
}
