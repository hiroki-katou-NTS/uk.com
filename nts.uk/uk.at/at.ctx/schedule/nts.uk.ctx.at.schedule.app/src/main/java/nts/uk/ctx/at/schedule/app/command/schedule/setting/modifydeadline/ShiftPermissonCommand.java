package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermisson;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class ShiftPermissonCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用できる*/
	private Integer availableShift; 
	
	/** 機能NO*/
	private Integer functionNoShift;
	
	public  ShiftPermisson toDomain(String companyId){
		return ShiftPermisson.createFromJavaType(companyId, this.roleId, this.availableShift, this.functionNoShift);
	}
}
