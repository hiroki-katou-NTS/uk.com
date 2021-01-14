package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeActualStampDto {
	// 実打刻
	private WorkStampDto actualStamp;
	// 打刻
	private WorkStampDto stamp;

	// 打刻反映回数
	private Integer numberOfReflectionStamp;

	// 時間外の申告
	private OvertimeDeclarationDto overtimeDeclaration;

	// 時間休暇時間帯
	private TimeZoneDto timeVacation;
}
