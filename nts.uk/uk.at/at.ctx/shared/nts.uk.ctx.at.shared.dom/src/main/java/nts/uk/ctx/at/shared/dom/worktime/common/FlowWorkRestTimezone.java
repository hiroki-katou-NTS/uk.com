/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkRestTimezone.
 */
//流動勤務の休憩時間帯
@Getter
public class FlowWorkRestTimezone extends DomainObject{

	/** The fix rest time. */
	//休憩時間帯を固定にする
	private boolean fixRestTime;
	
	/** The fixed rest timezone. */
	//固定休憩時間帯
	private TimezoneOfFixedRestTimeSet fixedRestTimezone;
	
	/** The flow rest timezone. */
	//流動休憩時間帯
	private FlowRestTimezone flowRestTimezone;

	/**
	 * Instantiates a new flow work rest timezone.
	 *
	 * @param fixRestTime the fix rest time
	 * @param fixedRestTimezone the fixed rest timezone
	 * @param flowRestTimezone the flow rest timezone
	 */
	public FlowWorkRestTimezone(boolean fixRestTime, TimezoneOfFixedRestTimeSet fixedRestTimezone,
			FlowRestTimezone flowRestTimezone) {
		super();
		this.fixRestTime = fixRestTime;
		this.fixedRestTimezone = fixedRestTimezone;
		this.flowRestTimezone = flowRestTimezone;
	}
	
	
}
