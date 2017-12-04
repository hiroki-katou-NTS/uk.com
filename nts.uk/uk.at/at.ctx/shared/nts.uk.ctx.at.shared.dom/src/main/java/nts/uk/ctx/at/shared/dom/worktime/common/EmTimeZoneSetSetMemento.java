/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.fixedset.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.TimeZoneRounding;

/**
 * The Interface EmTimeZoneSetSetMemento.
 */
public interface EmTimeZoneSetSetMemento {

	/**
 	 * Sets the employment time frame no.
 	 *
 	 * @param no the new employment time frame no
 	 */
 	void setEmploymentTimeFrameNo(EmTimeFrameNo no);

	/**
	 * Sets the timezone.
	 *
	 * @param rounding the new timezone
	 */
	 void setTimezone(TimeZoneRounding rounding);

}
