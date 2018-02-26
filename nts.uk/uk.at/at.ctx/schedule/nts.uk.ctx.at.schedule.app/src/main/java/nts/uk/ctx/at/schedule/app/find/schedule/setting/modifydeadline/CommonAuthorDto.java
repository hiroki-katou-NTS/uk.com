package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class CommonAuthorDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 共通権限制御: 利用できる*/
	private int availableCommon;
	
	/** 共通権限制御: 機能NO*/
	private Integer functionNoCommon;
}
