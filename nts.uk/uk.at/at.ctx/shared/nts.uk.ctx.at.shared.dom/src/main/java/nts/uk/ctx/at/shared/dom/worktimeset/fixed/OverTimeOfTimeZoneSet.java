/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OverTimeOfTimeZoneSet.
 */
@Getter
public class OverTimeOfTimeZoneSet extends DomainObject {

	/** The work timezone no. */
	// 就業時間帯NO
	private EmTimezoneNo workTimezoneNo;

	/** The restraint time use. */
	// 拘束時間として扱う
	private boolean restraintTimeUse;

	/** The early OT use. */
	// 早出残業として扱う
	private boolean earlyOTUse;

	// 時間帯
	private TimeZoneRounding timezone;

	/** The OT frame no. */
	// TODO 残業枠NO
	private Integer OTFrameNo;

	/** The legal O tframe no. */
	// 法定内残業枠NO
	private Integer legalOTframeNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;
}
