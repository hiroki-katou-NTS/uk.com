/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DayMonthDto.
 */
@Getter
@Setter
public class DayMonthDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The begin day. */
	private String beginDay;
	
	/** The end day. */
	private String endDay;
}
