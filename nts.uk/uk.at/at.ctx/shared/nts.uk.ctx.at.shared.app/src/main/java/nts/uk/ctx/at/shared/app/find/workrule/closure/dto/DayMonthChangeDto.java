/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DayMonthChangeDto.
 */

@Getter
@Setter
public class DayMonthChangeDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The before closure date. */
	private DayMonthDto beforeClosureDate;
	
	
	/** The after closure date. */
	private DayMonthDto afterClosureDate;

}
