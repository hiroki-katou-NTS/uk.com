package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithinStatutoryTimeOfDailyDto {
	// 就業時間
	private Integer workTime;
	// 実働就業時間
	private Integer actualWorkTime;
	// 所定内割増時間
	private Integer withinPrescribedPremiumTime;
	/** 実働所定内割増時間 */
	private Integer actualWithinPremiumTime;
	// 所定内深夜時間
	private WithinStatutoryMidNightTimeDto withinStatutoryMidNightTime;
	// 休暇加算時間
	private Integer vacationAddTime;
}
