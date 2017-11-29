/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FixedWorkTimezoneSet.
 */
// 固定勤務時間帯設定
@Getter
public class FixedWorkTimezoneSet extends DomainObject {

	/** The working timezone. */
	// 就業時間帯
	private List<EmTimeOfTimezoneSet> workingTimezone;

	/** The OT timezone. */
	// 残業時間帯
	private List<OverTimeOfTimeZoneSet> OTTimezone;
}
