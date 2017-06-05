/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The Class ClosureYear.
 */
@Getter
@Setter
public class ClosureYear implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The year. */
	private Integer year;
	
}
