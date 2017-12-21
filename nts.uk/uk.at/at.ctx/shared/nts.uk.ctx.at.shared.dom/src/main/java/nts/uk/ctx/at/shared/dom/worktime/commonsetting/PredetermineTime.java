package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;

/**
 * 所定時間
 * @author keisuke_hoshina
 *
 */

@Value
public class PredetermineTime {
	private BreakdownTimeDay time;
	private BreakdownTimeDay addTime;
}
