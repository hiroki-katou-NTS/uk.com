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
public class ScheduleShift {
	/** 利用可否権限の機能NO*/
	private int functionNoShift;
	
	/** 表示順*/	
	private int displayOrderShift;
	
	/** 利用可否権限の機能名*/
	private DisplayName displayNameShift;
	
	/** 説明文*/
	private Descripption descripptionShift;
	
	/** 初期値*/
	private int initialValueShift;
	
	public static ScheduleShift createFromJavaType(int functionNoShift, int displayOrderShift, String displayNameShift,String descripptionShift, int initialValueShift){
		return new ScheduleShift(functionNoShift, displayOrderShift, new DisplayName(displayNameShift), new Descripption(descripptionShift), initialValueShift );
	}
}