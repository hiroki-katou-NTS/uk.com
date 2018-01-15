/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

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
