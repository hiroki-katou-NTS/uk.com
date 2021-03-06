package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthor;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class CommonAuthorCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 共通権限制御: 利用できる*/
	private int availableCommon;
	
	/** 共通権限制御: 機能NO*/
	private Integer functionNoCommon;
	
	public  CommonAuthor toDomain(String companyId){
		return CommonAuthor.createFromJavaType(companyId, this.roleId, this.availableCommon, this.functionNoCommon);
	}
}
