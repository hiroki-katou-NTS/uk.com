package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplace;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class PerWorkplaceCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用できる*/
	private int availableWorkplace;
	
	/** 機能NO*/
	private Integer functionNoWorkplace;
	
	public  PerWorkplace toDomain(String companyId){
		return PerWorkplace.createFromJavaType(companyId, this.roleId, this.availableWorkplace, this.functionNoWorkplace);
	}
}
