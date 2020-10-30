package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OneMonthTimeExport {

	/** エラーアラーム時間 */
	private int errorTime;
	/** エラーアラーム時間 */
	private int alarmTime;
	/** 上限時間 */
	private int upperLimit;
}
