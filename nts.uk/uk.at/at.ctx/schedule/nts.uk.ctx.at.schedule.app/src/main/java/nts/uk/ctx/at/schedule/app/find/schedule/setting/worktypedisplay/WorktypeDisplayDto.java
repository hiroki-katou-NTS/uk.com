package nts.uk.ctx.at.schedule.app.find.schedule.setting.worktypedisplay;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncCondDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFuncControlDto;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorktypeDisplayDto {
	
	/** 会社ID*/
	private String companyId;
	
	/**  利用区分*/
	private int useAtr;
	
	private List<WorktypeDisplaySetDto> workTypeList;
	
	public static WorktypeDisplayDto fromDomain(WorktypeDis worktypeDis ){
		List<WorktypeDisplaySetDto> items = worktypeDis.getWorkTypeList().stream()
				.map(x-> WorktypeDisplaySetDto.fromDomain(x))
				.collect(Collectors.toList());
		return new WorktypeDisplayDto(
				worktypeDis.getCompanyId(), 
				worktypeDis.getUseAtr().value,
				items);
		
	}
	
}
