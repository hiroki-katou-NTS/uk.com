/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OverTimeOfTimeZoneSet.
 */
@Getter
public class OverTimeOfTimeZoneSet extends DomainObject{

	/** The work timezone no. */
	// TODO 就業時間帯NO
	private Integer workTimezoneNo;

	/** The restraint time use. */
	// 拘束時間として扱う
	private boolean restraintTimeUse;

	/** The early OT use. */
	// 早出残業として扱う
	private boolean earlyOTUse;

	// TODO 時間帯
	// private timezone;

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
