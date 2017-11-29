/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.TimeZoneRounding;

/**
 * The Class EmTimeZoneSet.
 */
//就業時間の時間帯設定
@Getter
public class EmTimeZoneSet {

	/** The Employment time frame no. */
	//就業時間枠NO
	private EmploymentTimeFrameNo EmploymentTimeFrameNo;
	
	/** The timezone. */
	//時間帯
	private TimeZoneRounding timezone;
}
