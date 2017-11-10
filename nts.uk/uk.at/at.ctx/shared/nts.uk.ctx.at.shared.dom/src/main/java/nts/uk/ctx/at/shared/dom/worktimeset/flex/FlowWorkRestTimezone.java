/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//流動勤務の休憩時間帯
@Getter
public class FlowWorkRestTimezone extends DomainObject{

	//休憩時間帯を固定にする
	private boolean fixRestTime;
	
	//固定休憩時間帯
	private TimezoneOfFixedRestTimeSet fixedRestTimezone;
	
	//流動休憩時間帯
	private FlowRestTimezone flowRestTimezone;
}
