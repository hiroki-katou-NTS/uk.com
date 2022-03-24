package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;

public class FlowOTTimezoneHelper {
	public static FlowOTTimezone createInLegal(OvertimeWorkFrameNo oTFrameNo, OvertimeWorkFrameNo inLegalOTFrameNo) {
		FlowOTTimezone result = new FlowOTTimezone();
		result.setOTFrameNo(oTFrameNo);
		result.setInLegalOTFrameNo(inLegalOTFrameNo);
		return result;
	}
}
