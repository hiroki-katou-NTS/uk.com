package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthority;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class PersAuthorityCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 個人別権限制御: 利用できる*/
	private int availablePers;
	
	/** 個人別権限制御: 機能NO*/
	private Integer functionNoPers;
	
	public  PersAuthority toDomain(String companyId){
		return PersAuthority.createFromJavaType(companyId, this.roleId, this.availablePers, this.functionNoPers);
	}
}
