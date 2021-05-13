package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskScheduleDetail;

@Value
@AllArgsConstructor
public class TaskScheduleDetailDto {
	public String taskCode;
	//時間帯
	public TimeSpanForCalcDto timeSpanForCalcDto;
	
	public static TaskScheduleDetailDto toDto(TaskScheduleDetail domain){
		TimeSpanForCalcDto timeSpanForCalcDto = new TimeSpanForCalcDto(domain.getTimeSpan().start(), domain.getTimeSpan().end());
		TaskScheduleDetailDto dto = new TaskScheduleDetailDto(domain.getTaskCode().v(), timeSpanForCalcDto);
		return dto;
	}
}
