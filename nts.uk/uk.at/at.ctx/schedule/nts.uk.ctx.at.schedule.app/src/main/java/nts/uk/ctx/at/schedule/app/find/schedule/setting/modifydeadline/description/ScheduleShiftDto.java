package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ScheduleShiftDto {
	/** 利用可否権限の機能NO*/
	private int functionNoShift;
	
	/** 表示順*/	
	private int displayOrderShift;
	
	/** 利用可否権限の機能名*/
	private String displayNameShift;
	
	/** 説明文*/
	private String descripptionShift;
	
	/** 初期値*/
	private int initialValueShift;
}
