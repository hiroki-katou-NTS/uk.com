package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ScheduleCommonDto {
	/** 利用可否権限の機能NO*/
	private int functionNoCom;
	
	/** 表示順*/	
	private int displayOrderCom;
	
	/** 利用可否権限の機能名*/
	private String displayNameCom;
	
	/** 説明文*/
	private String descripptionCom;
	
	/** 初期値*/
	private int initialValueCom;
}
