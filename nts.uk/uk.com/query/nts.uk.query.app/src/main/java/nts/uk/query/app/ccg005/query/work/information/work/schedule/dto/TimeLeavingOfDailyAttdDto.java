package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeLeavingOfDailyAttdDto {
	// 1 ~ 2
	/** 出退勤 */
	private List<TimeLeavingWorkDto> timeLeavingWorks;
	/** 勤務回数 */
	private Integer workTimes;
}
