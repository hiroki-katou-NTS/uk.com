package nts.uk.screen.at.app.command.kmk.kmk004.k;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;

/**
 * 
 * @author sonnlb
 *
 */
public class FlexTimeHandleCommand {
	/** 残業時間をフレックス時間に含める */
	private boolean includeOverTime;

	/** 法定外休出時間をフレックス時間に含める */
	private boolean includeIllegalHdwk;

	public FlexTimeHandle toDomain() {
		return FlexTimeHandle.of(includeOverTime, includeIllegalHdwk);
	}
}
