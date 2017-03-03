/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import lombok.Data;

/**
 * Instantiates a new history dto.
 */
@Data
public class HistoryDto {
	
	/** The company code. */
	public String companyCode;
	
	/** The office code. */
	public String officeCode;
	
	/** The start. */
	public String start;
	
	/** The end. */
	public String end;
	
	/** The code. */
	public String code;

	/** The name. */
	public String name;
}
