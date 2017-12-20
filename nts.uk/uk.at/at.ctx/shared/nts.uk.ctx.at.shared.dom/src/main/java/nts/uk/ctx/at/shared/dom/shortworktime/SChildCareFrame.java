/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class SChildCareFrame.
 */
// 社員の育児介護時間帯
@Getter
@Builder
@AllArgsConstructor
public class SChildCareFrame extends DomainObject {
	
	/** The time slot. */
	// 回数
	public int timeSlot;
	
	/** The start time. */
	// 開始時刻
	public TimeWithDayAttr startTime;
	
	/** The end time. */
	// 終了時刻
	public TimeWithDayAttr endTime;
}
