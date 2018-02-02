/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * Gets the base date.
 *
 * @return the base date
 */
@Getter

/**
 * Sets the base date.
 *
 * @param baseDate the new base date
 */
@Setter
public class UserInfo {
	
	/** The base date. */
	private GeneralDate baseDate;
	
	/** The is screen C. */
	private Boolean isScreenC;
}
