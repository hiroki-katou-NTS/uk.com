package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemWidowHistory extends CtgItemFixDto{
	private String windowHistoryId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int windowType;
	
	private ItemWidowHistory(String windowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		super();
		this.ctgItemType = CtgItemType.WIDOW_HISTORY;
		this.windowHistoryId = windowHistoryId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.windowType = windowType;
	}
	
	public static ItemWidowHistory createFromJavaType(String windowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		return new ItemWidowHistory(windowHistoryId, startDate, endDate, windowType);
	}
}
