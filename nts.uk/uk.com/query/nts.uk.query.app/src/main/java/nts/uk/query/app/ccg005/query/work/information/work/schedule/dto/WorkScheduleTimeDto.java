package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkScheduleTimeDto {
	/** 合計時間: 勤怠時間 */
	private Integer total;

	/** 所定外時間: 勤怠時間 */
	private Integer excessOfStatutoryTime;

	/** 所定内時間: 勤怠時間 */
	private Integer withinStatutoryTime;
}
