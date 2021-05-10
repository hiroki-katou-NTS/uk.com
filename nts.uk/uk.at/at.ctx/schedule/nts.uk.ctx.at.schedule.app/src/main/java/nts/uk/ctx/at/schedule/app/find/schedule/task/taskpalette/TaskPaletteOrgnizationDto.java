package nts.uk.ctx.at.schedule.app.find.schedule.task.taskpalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette.TaskPalette;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette.TaskPaletteOrganization;
/**
 * 
 * @author quytb
 *
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPaletteOrgnizationDto {
	private Integer targetUnit;
	private String targetId;
	private Integer page;
	private String name;
	private String remarks;	
	Set<Integer> keys;
	List<String> taskCodes;
	List<String> taskNames;
	List<String> taskAbNames;
	
	public static TaskPaletteOrgnizationDto setData(TaskPaletteOrganization domain, TaskPalette taskPalette) {
//		Set<Integer> keys = domain.getTasks().keySet();
//		List<String> values = new ArrayList<String>();
//		keys.forEach(key ->{
//			values.add(domain.getTasks().get(key).v());
//		});
		
		Set<Integer> keys = taskPalette.getTasks().keySet();
		List<String> taskCodes = new ArrayList<String>();
		List<String> taskNames = new ArrayList<String>();
		List<String> taskAbNames = new ArrayList<String>();
		keys.forEach(key ->{
			taskCodes.add(taskPalette.getTasks().get(key).getTaskCode().v());
			taskNames.add(taskPalette.getTasks().get(key).getTaskName().isPresent() ? taskPalette.getTasks().get(key).getTaskName().get().v(): null);
			taskAbNames.add(taskPalette.getTasks().get(key).getTaskAbName().isPresent() ? taskPalette.getTasks().get(key).getTaskAbName().get().v(): null);
		});
		
		return new TaskPaletteOrgnizationDto(domain.getTargetOrg().getUnit().value, domain.getTargetOrg().getTargetId(),
				domain.getPage(), domain.getDisplayInfo().getName().v(),
				domain.getDisplayInfo().getRemark().isPresent() ? domain.getDisplayInfo().getRemark().get().v() : "",
				keys, taskCodes, taskNames, taskAbNames);
	}	
}

