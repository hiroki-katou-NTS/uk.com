package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class PerWorkplaceDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 職場別権限制御: 利用できる*/
	private int availableWorkplace;
	
	/** 職場別権限制御: 機能NO*/
	private Integer functionNoWorkplace;
}
