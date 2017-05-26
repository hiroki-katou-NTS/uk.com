/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * Sets the half day time.
 *
 * @param halfDayTime the new half day time
 */
@Setter
public class DesignTime extends DomainObject{

	/** The one day time. */
	private OneDayTime oneDayTime;
	
	/** The half day time. */
	private OneDayTime halfDayTime;
}
