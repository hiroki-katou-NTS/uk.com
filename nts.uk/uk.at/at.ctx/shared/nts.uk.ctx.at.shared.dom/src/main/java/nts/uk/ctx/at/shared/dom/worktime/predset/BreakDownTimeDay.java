/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Builder;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Class BreakDownTimeDay.
 */
@Builder
@Getter
//１日の時間内訳
public class BreakDownTimeDay extends DomainObject{

	/** The one day. */
	// 1日
	private AttendanceTime oneDay;

	/** The morning. */
	// 午前
	private AttendanceTime morning;

	/** The afternoon. */
	// 午後
	private AttendanceTime afternoon;
	
	
	/** The Constant TOTAL_ONE_DAY_MINUTES. */
	// 24:00
	public static final int TOTAL_ONE_DAY_MINUTES = 1440;
	
	/** The Constant ZERO_MINUTES. */
	// 00:00
	public static final int ZERO_MINUTES = 0;
	
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		// 1日＞午前 => Msg_518 
		// 1日＞午後 => Msg_518
		if (this.oneDay.valueAsMinutes() > this.morning.valueAsMinutes()
				|| this.oneDay.valueAsMinutes() > this.afternoon.valueAsMinutes()) {
			throw new BusinessException("Msg_518");
		}
		
		// １日の範囲時間内であること => Msg_781
		if(this.oneDay.valueAsMinutes() >= TOTAL_ONE_DAY_MINUTES){
			throw new BusinessException("Msg_518"); 
		}
		
		// < 0:01 => Msg_778
		if (this.oneDay.valueAsMinutes() <= ZERO_MINUTES || this.morning.valueAsMinutes() <= ZERO_MINUTES
				|| this.afternoon.valueAsMinutes() <= ZERO_MINUTES) {
			throw new BusinessException("Msg_778"); 
		}
	}
}
