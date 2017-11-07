/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.commonset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneExtraordTimeSet.
 */
//就業時間帯の臨時時間設定
@Getter
public class WorkTimezoneExtraordTimeSet extends DomainObject{
	
	//休出枠設定
	private HolidayFramset holidayFrameSet;
	
//	//
//	private timeRoundingSet;
//	private OTFrameSet;
//	private calculateMethod;
}
