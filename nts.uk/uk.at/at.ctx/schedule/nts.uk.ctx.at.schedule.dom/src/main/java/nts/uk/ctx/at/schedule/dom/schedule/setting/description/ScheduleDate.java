package nts.uk.ctx.at.schedule.dom.schedule.setting.description;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
public class ScheduleDate {
	/** 利用可否権限の機能NO*/
	private int functionNoDate;
	
	/** 表示順*/	
	private int displayOrderDate;
	
	/** 利用可否権限の機能名*/
	private DisplayName displayNameDate;
	
	/** 説明文*/
	private Descripption descripptionDate;
	
	/** 初期値*/
	private int initialValueDate;
	
	public static ScheduleDate createFromJavaType(int functionNoDate, int displayOrderDate, String displayNameDate,String descripptionDate, int initialValueDate){
		return new ScheduleDate(functionNoDate, displayOrderDate, new DisplayName(displayNameDate), new Descripption(descripptionDate), initialValueDate );
	}
}