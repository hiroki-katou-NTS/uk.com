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
public class ScheduleCommon {
	/** 利用可否権限の機能NO*/
	private int functionNoCom;
	
	/** 表示順*/	
	private int displayOrderCom;
	
	/** 利用可否権限の機能名*/
	private DisplayName displayNameCom;
	
	/** 説明文*/
	private Descripption descripptionCom;
	
	/** 初期値*/
	private int initialValueCom;
	
	public static ScheduleCommon createFromJavaType(int functionNoCom, int displayOrderCom, String displayNameCom,String descripptionCom, int initialValueCom){
		return new ScheduleCommon(functionNoCom, displayOrderCom, new DisplayName(displayNameCom), new Descripption(descripptionCom), initialValueCom );
	}
}