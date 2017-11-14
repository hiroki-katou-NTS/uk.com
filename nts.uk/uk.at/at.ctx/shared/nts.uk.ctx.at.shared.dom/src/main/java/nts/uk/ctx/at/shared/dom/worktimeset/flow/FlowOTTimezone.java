/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.SettlementOrder;

/**
 * The Class FlowOTTimezone.
 */
//流動残業時間帯

/**
 * Gets the settlement order.
 *
 * @return the settlement order
 */
@Getter
public class FlowOTTimezone extends DomainObject {

	/** The restrict time. */
	// 拘束時間として扱う
	private boolean restrictTime;

	// TODO 残業枠NO
	// private OTFrameNo;

	/** The flow time setting. */
	// 流動時間設定
	private FlowTimeSetting flowTimeSetting;

	// TODO 法定内残業枠NO
	// private inLegalOTFrameNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;
}
