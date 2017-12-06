/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PredetermineTime.
 */
@Builder
@Getter
//所定時間
public class PredetermineTime extends DomainObject {

	/** The add time. */
	//就業加算時間
	private BreakDownTimeDay addTime;
	
	/** The pred time. */
	//所定時間
	private BreakDownTimeDay predTime;
}
