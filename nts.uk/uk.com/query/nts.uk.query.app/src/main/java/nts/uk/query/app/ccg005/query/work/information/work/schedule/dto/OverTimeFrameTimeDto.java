package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class OverTimeFrameTimeDto {
	/** 残業枠NO: 残業枠NO */
	private Integer OverWorkFrameNo;
	/** 残業時間: 計算付き時間 */
	@Setter
	private TimeDivergenceWithCalculationDto OverTimeWork;
	@Setter
	/** 振替時間: 計算付き時間 */
	private TimeDivergenceWithCalculationDto TransferTime;
	/** 事前申請時間: 勤怠時間 */
	@Setter
	private Integer BeforeApplicationTime;
	/** 指示時間: 勤怠時間 */
	private Integer orderTime;
}
