package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

public class FlowWorkTimezoneSettingHelper {
	public static FlowWorkTimezoneSetting createInLegal(FlowOTTimezone...flowOTTimezones) {
		return new FlowWorkTimezoneSetting(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				Arrays.asList(flowOTTimezones));
	}
}
