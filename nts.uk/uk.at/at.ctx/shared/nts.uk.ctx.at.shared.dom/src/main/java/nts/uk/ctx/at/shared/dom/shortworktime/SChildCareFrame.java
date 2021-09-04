/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import lombok.Builder;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class SChildCareFrame.
 */
// 社員の育児介護時間帯
@Getter
@Builder
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

	public SChildCareFrame(int timeSlot, TimeWithDayAttr startTime, TimeWithDayAttr endTime) {
		super();
		
		// 開始時刻、終了時刻は必ず入力が必要
		if (startTime == null || endTime == null) {
			throw new BusinessException("Msg_858");
		}
		
		// 開始時刻＜＝終了時刻でなければならない
		if (!startTime.lessThanOrEqualTo(endTime)) {
			throw new BusinessException("Msg_857");
		}
		
		this.timeSlot = timeSlot;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public TimeSpanForCalc getSpan() {
		return new TimeSpanForCalc(startTime, endTime);
	}
}
