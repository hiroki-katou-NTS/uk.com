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
public class DayMonthChangeInDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The month. */
	private int month;
	
	
	/** The closure date. */
	private int closureDate;
	
	
	/** The change closure date. */
	private int changeClosureDate;

}
