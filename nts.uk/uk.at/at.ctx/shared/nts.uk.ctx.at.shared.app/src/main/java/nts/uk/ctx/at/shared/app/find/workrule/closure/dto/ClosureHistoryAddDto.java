/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ClosureHistoryAddDto.
 */
@Getter
@Setter
public class ClosureHistoryAddDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The closure name. */
	private String closureName;
	
	/** The closure id. */
	private int closureId;
		
	/** The closure year. */
	private int endDate;
	
	/** The closure date. */
	private int closureDate;
	
	/** The start date. */
	private int startDate;
}
