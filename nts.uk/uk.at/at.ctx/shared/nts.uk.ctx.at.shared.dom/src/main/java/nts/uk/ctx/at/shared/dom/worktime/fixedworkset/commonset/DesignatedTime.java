/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.commonset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;

/**
 * The Class DesignatedTime.
 */
//指定時間
@Getter
public class DesignatedTime extends DomainObject {
	
	/** The one day time. */
	//1日の時間
	private OneDayTime oneDayTime;
	
	/** The half day time. */
	//半日の時間
	private OneDayTime halfDayTime;
}
