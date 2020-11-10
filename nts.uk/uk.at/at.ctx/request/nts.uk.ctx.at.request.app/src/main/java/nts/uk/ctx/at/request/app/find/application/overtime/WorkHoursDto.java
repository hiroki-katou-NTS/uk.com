package nts.uk.ctx.at.request.app.find.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;

@AllArgsConstructor
@NoArgsConstructor
public class WorkHoursDto {
	// 開始時刻1
	public Integer startTimeOp1;
	// 開始時刻2
	public Integer startTimeOp2;
	// 終了時刻1
	public Integer endTimeOp1;
	// 終了時刻2
	public Integer endTimeOp2;
	
	public static WorkHoursDto fromDomain(WorkHours workHours) {
		if (workHours == null) return null;
		return new WorkHoursDto(
				workHours.getStartTimeOp1().map(x -> x.v()).orElse(null),
				workHours.getStartTimeOp2().map(x -> x.v()).orElse(null),
				workHours.getEndTimeOp1().map(x -> x.v()).orElse(null),
				workHours.getEndTimeOp2().map(x -> x.v()).orElse(null));
	}
}
