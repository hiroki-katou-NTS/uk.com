package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ScheduleWorkplaceDto {
	/** 利用可否権限の機能NO*/
	private int functionNoWork;
	
	/** 表示順*/	
	private int displayOrderWork;
	
	/** 利用可否権限の機能名*/
	private String displayNameWork;
	
	/** 説明文*/
	private String descripptionWork;
	
	/** 初期値*/
	private int initialValueWork;
}
