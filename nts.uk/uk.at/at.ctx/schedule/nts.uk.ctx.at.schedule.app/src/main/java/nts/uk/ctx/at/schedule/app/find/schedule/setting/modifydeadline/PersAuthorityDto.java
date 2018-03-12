package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class PersAuthorityDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 個人別権限制御: 利用できる*/
	private int availablePers;
	
	/** 個人別権限制御: 機能NO*/
	private Integer functionNoPers;
}
