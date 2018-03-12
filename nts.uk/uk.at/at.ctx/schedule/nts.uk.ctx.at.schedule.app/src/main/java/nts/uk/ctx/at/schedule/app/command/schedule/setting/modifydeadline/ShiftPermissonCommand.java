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
	
	/** シフト別権限制御: 利用区分*/
	private Integer availableShift; 
	
	/** シフト別権限制御: 修正期限*/
	private Integer functionNoShift;
	
	public  ShiftPermisson toDomain(String companyId){
		return ShiftPermisson.createFromJavaType(companyId, this.roleId, this.availableShift, this.functionNoShift);
	}
}
