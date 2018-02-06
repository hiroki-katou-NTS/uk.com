package nts.uk.ctx.at.schedule.app.find.schedule.setting.worktypedisplay;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;

@Data
@AllArgsConstructor
public class WorktypeDisplaySetDto {
	
	private String companyId;

	private String workTypeCode;
	
	public static WorktypeDisplaySetDto fromDomain(WorkTypeDisplaySetting dis){
		return new WorktypeDisplaySetDto(
				dis.getCompanyId(), 
				dis.getWorkTypeCode());
		
	}
}
