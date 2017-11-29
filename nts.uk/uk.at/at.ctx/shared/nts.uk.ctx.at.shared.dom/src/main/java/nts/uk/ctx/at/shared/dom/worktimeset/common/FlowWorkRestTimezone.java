/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkRestTimezone.
 */
//流動勤務の休憩時間帯

/**
 * Gets the flow rest timezone.
 *
 * @return the flow rest timezone
 */
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
}
