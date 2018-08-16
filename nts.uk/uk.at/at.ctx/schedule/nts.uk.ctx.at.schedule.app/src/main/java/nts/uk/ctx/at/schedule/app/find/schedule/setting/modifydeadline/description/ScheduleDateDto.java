package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ScheduleDateDto {
	/** 利用可否権限の機能NO*/
	private int functionNoDate;
	
	/** 表示順*/	
	private int displayOrderDate;
	
	/** 利用可否権限の機能名*/
	private String displayNameDate;
	
	/** 説明文*/
	private String descripptionDate;
	
	/** 初期値*/
	private int initialValueDate;
}
