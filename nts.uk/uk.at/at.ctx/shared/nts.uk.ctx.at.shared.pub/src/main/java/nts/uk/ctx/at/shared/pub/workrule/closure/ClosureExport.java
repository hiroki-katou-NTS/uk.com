/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.workrule.closure;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClosureExport.
 */
@Builder
@Data
public class ClosureExport {

	/** The closure id. */
	private Integer closureId;

	/** The closure name. */
	private String closureName;
}
