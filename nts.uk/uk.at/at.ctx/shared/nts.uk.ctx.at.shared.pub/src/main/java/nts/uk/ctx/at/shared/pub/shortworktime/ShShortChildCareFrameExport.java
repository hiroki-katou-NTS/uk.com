/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.shortworktime;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ShShortChildCareFrameExport.
 */
@Getter
@Builder
public class ShShortChildCareFrameExport extends DomainObject {
	
	/** The time slot. */
	// 回数
	private int timeSlot;
	
	/** The start time. */
	// 開始時刻
	private TimeWithDayAttr startTime;
	
	/** The end time. */
	// 終了時刻
	private TimeWithDayAttr endTime;
}
