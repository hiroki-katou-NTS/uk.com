package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
public class DateAuthorityDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 日付別権限制御: 利用できる*/
	private int availableDate;
	
	/** 日付別権限制御: 機能NO*/
	private Integer functionNoDate;
}
