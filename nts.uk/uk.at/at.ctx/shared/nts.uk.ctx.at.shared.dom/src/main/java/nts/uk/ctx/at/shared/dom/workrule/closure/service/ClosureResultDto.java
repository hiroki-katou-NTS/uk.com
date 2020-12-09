/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClosureResultModel.
 */
@Builder
@Data
public class ClosureResultDto {

	/** The closure id. */
	private Integer closureId;

	/** The closure name. */
	private String closureName;
}
