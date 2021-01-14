package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PremiumTimeDto {
	// 割増時間NO - primitive value
	private Integer premiumTimeNo;

	// 割増時間
	private Integer premitumTime;

	/** 割増金額: 勤怠日別金額 */
	private Integer premiumAmount;
}
