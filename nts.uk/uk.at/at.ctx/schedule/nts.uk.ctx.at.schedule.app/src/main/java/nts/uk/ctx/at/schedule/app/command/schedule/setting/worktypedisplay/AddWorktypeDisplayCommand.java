package nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class AddWorktypeDisplayCommand {
	
	/** 利用区分 */
	private Integer useAtr;
	
	private List<String> workTypeList;
	
	public  WorktypeDis toDomain(String companyId){
		return WorktypeDis.createFromJavaType(companyId, this.useAtr, this.workTypeList);
	}
	
}
