package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.DateAuthority;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class DateAuthorityCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 日付別権限制御: 利用できる*/
	private int availableDate;
	
	/** 日付別権限制御: 機能NO*/
	private Integer functionNoDate;
	
	public  DateAuthority toDomain(String companyId){
		return DateAuthority.createFromJavaType(companyId, this.roleId, this.availableDate, this.functionNoDate);
	}
}
