/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.imports.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaborInsuranceOfficeCheckImportDto {
	
	/** The code. */
	private String code; // "0" succ 1 "rows duplication"
	
	/** The message. */
	private String message;
}
