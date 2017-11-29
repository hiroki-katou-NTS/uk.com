/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.SettlementOrder;

/**
 * The Class ExtraordWorkOTFrameSet.
 */
// 臨時勤務時の残業枠設定
@Getter
public class ExtraordWorkOTFrameSet extends DomainObject {

	/** The OT frame no. */
	// 残業枠NO
	private OTFrameNo OTFrameNo;

	/** The in legal work frame no. */
	// 法内残業枠NO
	private OTFrameNo inLegalWorkFrameNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;
}
