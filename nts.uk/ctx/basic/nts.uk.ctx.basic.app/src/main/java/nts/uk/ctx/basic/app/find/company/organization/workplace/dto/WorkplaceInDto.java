/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.workplace.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkplaceInDto.
 */
@Setter
@Getter
public class WorkplaceInDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1934239969905395571L;
	
	/** The date. */
	private String date;
	
	/** The format. */
	private String format;

}
