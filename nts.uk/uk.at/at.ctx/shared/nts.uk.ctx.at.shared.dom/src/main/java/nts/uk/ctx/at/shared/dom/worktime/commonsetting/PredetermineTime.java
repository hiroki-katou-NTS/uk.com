package nts.uk.ctx.at.shared.dom.worktime.CommonSetting;
/**
 * 所定時間
 * @author keisuke_hoshina
 *
 */

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;

@Value
public class PredetermineTime {
	private BreakdownTimeDay time;
	private BreakdownTimeDay addTime;
}
