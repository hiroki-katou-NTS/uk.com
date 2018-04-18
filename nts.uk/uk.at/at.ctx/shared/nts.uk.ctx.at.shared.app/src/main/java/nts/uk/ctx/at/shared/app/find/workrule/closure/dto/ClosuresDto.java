/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ClosuresDto {

	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	private String closeName;
	
	/** The closure date. */
	private int closureDate;
	
}
