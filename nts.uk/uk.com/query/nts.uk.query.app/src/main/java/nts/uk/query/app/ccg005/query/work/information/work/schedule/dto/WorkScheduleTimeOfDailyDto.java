package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkScheduleTimeOfDailyDto {
	//勤務予定時間
	private WorkScheduleTimeDto workScheduleTime;
	
	//実績所定労働時間
	private Integer recordPrescribedLaborTime;
}
