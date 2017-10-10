package nts.uk.ctx.bs.person.dom.person.info.widowhistory;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;

/**寡夫寡婦履歴
 * @author xuan vinh
 * */

@Getter
public class WidowHistory {
	//寡夫寡婦ID
	private String windowHistoryId;
	//start date
	private GeneralDate startDate;
	//end date
	private GeneralDate endDate;
	//寡夫寡婦区分
	private WidowType windowType;
	
	private WidowHistory(String windowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		this.windowHistoryId = windowHistoryId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.windowType = EnumAdaptor.valueOf(windowType, WidowType.class);
	}
	
	public static WidowHistory createObjectFromJavaType(String windowHistoryId, GeneralDate startDate, GeneralDate endDate, int windowType){
		return new WidowHistory(windowHistoryId, startDate, endDate, windowType);
	}
}
