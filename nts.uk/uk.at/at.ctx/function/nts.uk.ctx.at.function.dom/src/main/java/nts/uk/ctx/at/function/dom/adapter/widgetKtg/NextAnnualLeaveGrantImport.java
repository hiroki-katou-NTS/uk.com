package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class NextAnnualLeaveGrantImport {
	/** 付与年月日*/
	private GeneralDate grantDate;
	/** 付与日数 */
	private double grantDays;
	/** 回数 */
	private int times;
	/** 時間年休上限日数 */
	private int timeAnnualLeaveMaxDays;
	/** 時間年休上限時間 */
	private int timeAnnualLeaveMaxTime;
	/** 半日年休上限回数 */
	private int halfDayAnnualLeaveMaxTimes;
	
}
