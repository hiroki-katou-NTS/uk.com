package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ScheduleAuthorityDto {
	/** 利用可否権限の機能NO*/
	private int functionNoAuth;
	
	/** 表示順*/	
	private int displayOrderAuth;
	
	/** 利用可否権限の機能名*/
	private String displayNameAuth;
	
	/** 説明文*/
	private String descripptionAuth;
	
	/** 初期値*/
	private int initialValueAuth;
}
