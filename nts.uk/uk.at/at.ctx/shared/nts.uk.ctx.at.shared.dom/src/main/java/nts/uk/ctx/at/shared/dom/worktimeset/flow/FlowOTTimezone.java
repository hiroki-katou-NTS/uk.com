/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.SettlementOrder;

/**
 * The Class FlowOTTimezone.
 */
//流動残業時間帯
@Getter
public class FlowOTTimezone extends DomainObject {

	/** The worktime no. */
	// 就業時間帯NO
	private Integer worktimeNo;
	
	/** The restrict time. */
	// 拘束時間として扱う
	private boolean restrictTime;

	/** The OT frame no. */
	// 残業枠NO
	private OvertimeWorkFrameNo OTFrameNo;

	/** The flow time setting. */
	// 流動時間設定
	private FlowTimeSetting flowTimeSetting;

	/** The in legal OT frame no. */
	// 法定内残業枠NO
	private OvertimeWorkFrameNo inLegalOTFrameNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;
}
