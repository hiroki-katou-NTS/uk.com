/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.query.workrule.closure;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClosureResultModel.
 */
@Builder
@Data
public class ClosureResultModel {

	/** The closure id. */
	private Integer closureId;

	/** The closure name. */
	private String closureName;
}
