/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import java.util.List;

import lombok.Getter;

/**
 * The Class DiffTimezoneSet.
 */
///時差勤務時間帯設定
@Getter
public class DiffTimezoneSet {
	
	/** The employment timezone. */
	// 就業時間帯
	private List<EmTimeZoneSet> employmentTimezone;
	
	/** The OT timezone. */
	// 残業時間帯
	private List<DiffTimeOTTimezoneSet> OTTimezone;
}
