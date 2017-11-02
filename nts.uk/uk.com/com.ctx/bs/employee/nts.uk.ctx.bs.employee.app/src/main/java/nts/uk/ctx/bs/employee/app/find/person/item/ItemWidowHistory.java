package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemWidowHistory extends CtgItemFixDto{
	private String widowHistoryId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int widowType;
	
	private ItemWidowHistory(String widowHistoryId, GeneralDate startDate, GeneralDate endDate, int widowType){
		super();
		this.ctgItemType = CtgItemType.WIDOW_HISTORY;
		this.widowHistoryId = widowHistoryId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.widowType = widowType;
	}
	
	public static ItemWidowHistory createFromJavaType(String widowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		return new ItemWidowHistory(widowHistoryId, startDate, endDate, windowType);
	}
}
