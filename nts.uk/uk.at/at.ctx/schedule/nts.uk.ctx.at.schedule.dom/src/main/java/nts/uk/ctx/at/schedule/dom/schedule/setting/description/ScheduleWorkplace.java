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
public class ScheduleWorkplace {
	/** 利用可否権限の機能NO*/
	private int functionNoWork;
	
	/** 表示順*/	
	private int displayOrderWork;
	
	/** 利用可否権限の機能名*/
	private DisplayName displayNameWork;
	
	/** 説明文*/
	private Descripption descripptionWork;
	
	/** 初期値*/
	private int initialValueWork;
	
	public static ScheduleWorkplace createFromJavaType(int functionNoWork, int displayOrderWork, String displayNameWork,String descripptionWork, int initialValueWork){
		return new ScheduleWorkplace(functionNoWork, displayOrderWork, new DisplayName(displayNameWork), new Descripption(descripptionWork), initialValueWork );
	}
}
