/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Setter
@Getter
@Builder
public class ClosureInfor {
	
	/** The closure id. */
	// 締めＩＤ
	private ClosureId closureId;
	
	/** The closure name. */
	//締め名称
	private ClosureName closureName;
	
	/** The closure month. */
	// 当月
	private CurrentMonth closureMonth; 
	
	/** The period. */
	//期間
	private DatePeriod period;
	
	/** The closure date. */
	//締め日
	private ClosureDate closureDate;
}
