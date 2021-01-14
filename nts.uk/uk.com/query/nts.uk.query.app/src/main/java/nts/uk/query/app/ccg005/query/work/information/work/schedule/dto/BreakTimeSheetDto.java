package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BreakTimeSheetDto {
	// 休憩枠NO
	private Integer breakFrameNo;

	// 開始 - 勤怠打刻(実打刻付き)
	private Integer startTime;

	// 終了 - 勤怠打刻(実打刻付き)
	private Integer endTime;

	/** 休憩時間: 勤怠時間 */
	private Integer breakTime;
}
