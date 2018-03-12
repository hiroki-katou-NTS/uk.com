package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class ShiftPermissonDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** シフト別権限制御: 利用区分*/
	private int availableShift;
	
	/** シフト別権限制御: 修正期限*/
	private Integer functionNoShift;
}
