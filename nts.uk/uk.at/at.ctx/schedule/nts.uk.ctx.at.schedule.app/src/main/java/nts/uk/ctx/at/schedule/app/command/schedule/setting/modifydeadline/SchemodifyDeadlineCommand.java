package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CommonAuthor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.DateAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PerWorkplace;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.PersAuthority;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.SchemodifyDeadline;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.ShiftPermisson;
/**
 * 
 * @author phongtq
 *
 */

@Data
@AllArgsConstructor
public class SchemodifyDeadlineCommand {
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用区分*/
	private int useCls;
	
	/** 修正期限*/
	private Integer correctDeadline;
	
	public  SchemodifyDeadline toDomain(String companyId){
		return SchemodifyDeadline.createFromJavaType(
				companyId, 
				this.roleId, 
				this.useCls, 
				this.correctDeadline);
	}
}
