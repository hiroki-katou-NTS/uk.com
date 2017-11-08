/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmTimeOfTimezoneSet.
 */
//就業時間の時間帯設定
@Getter
public class EmTimeOfTimezoneSet extends DomainObject{

	/** The em time frame no. */
	// 就業時間枠NO
	private EmTimeFrameNo emTimeFrameNo;
	
	/** The timezone. */
	// 時間帯
	private TimeZoneRounding timezone;
}
