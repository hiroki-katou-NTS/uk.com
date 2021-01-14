package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OvertimeDeclarationDto {
	/**
	 * 時間外時間 就業時間帯コード old
	 */
	private final Integer overTime;

	/**
	 * 時間外深夜時間
	 */
	private final Integer overLateNightTime;
}
