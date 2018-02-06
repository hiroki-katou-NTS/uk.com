package nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;
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
	
	private List<WorkTypeSetCommand> workTypeList;
	
	public  WorktypeDis toDomain(String companyId){
		List<WorkTypeDisplaySetting> displaySettings = this.workTypeList.stream().map(x-> {
			return new WorkTypeDisplaySetting(companyId, x.getWorkTypeCode());
		}).collect(Collectors.toList());
		return WorktypeDis.createFromJavaType(companyId, this.useAtr, displaySettings);
	}
	
}
