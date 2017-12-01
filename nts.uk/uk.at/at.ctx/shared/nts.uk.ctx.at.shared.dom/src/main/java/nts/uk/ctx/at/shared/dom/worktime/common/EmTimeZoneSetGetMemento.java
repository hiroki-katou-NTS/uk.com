/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRounding;

/**
 * The Interface EmTimeZoneSetGetMemento.
 */
public interface EmTimeZoneSetGetMemento {

	 /**
 	 * Gets the employment time frame no.
 	 *
 	 * @return the employment time frame no
 	 */
 	EmTimeFrameNo getEmploymentTimeFrameNo();

	/**
	 * Gets the timezone.
	 *
	 * @return the timezone
	 */
	 TimeZoneRounding getTimezone();

}
