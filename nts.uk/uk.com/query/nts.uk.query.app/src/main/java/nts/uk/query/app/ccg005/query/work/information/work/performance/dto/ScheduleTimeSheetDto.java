package nts.uk.query.app.ccg005.query.work.information.work.performance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleTimeSheetDto {
	
	private Integer workNo;
	
	private Integer attendance;
	
	private Integer leaveWork;
}
