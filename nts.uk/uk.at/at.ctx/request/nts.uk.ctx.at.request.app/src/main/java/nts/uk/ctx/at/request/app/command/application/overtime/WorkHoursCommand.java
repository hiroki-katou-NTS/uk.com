package nts.uk.ctx.at.request.app.command.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;

@AllArgsConstructor
@NoArgsConstructor
public class WorkHoursCommand {
	// 開始時刻1
	public Integer startTimeOp1;
	// 開始時刻2
	public Integer startTimeOp2;
	// 終了時刻1
	public Integer endTimeOp1;
	// 終了時刻2
	public Integer endTimeOp2;
	
	public WorkHours toDomain() {
		return new WorkHours(
				startTimeOp1,
				startTimeOp2,
				endTimeOp1,
				endTimeOp2);
	}
}
