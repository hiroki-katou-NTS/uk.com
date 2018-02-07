package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.Data;

@Data
public class PerWorkplaceDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用区分*/
	private int availableWorkplace;
	
	/** 修正期限*/
	private Integer functionNoWorkplace;
}
