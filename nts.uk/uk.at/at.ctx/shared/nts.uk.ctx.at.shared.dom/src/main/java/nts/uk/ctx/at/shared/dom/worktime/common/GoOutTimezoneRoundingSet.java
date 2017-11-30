/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class GoOutTimezoneRoundingSet.
 */
//時間帯別外出丸め設定
@Getter
public class GoOutTimezoneRoundingSet extends DomainObject {
	
	/** The pub hol work timezone. */
	//休日出勤時間帯
	private GoOutTypeRoundingSet pubHolWorkTimezone;
	
	/** The work timezone. */
	//就業時間帯
	private GoOutTypeRoundingSet workTimezone;
	
	/** The OT timezone. */
	//残業時間帯
	private GoOutTypeRoundingSet OTTimezone;
}
