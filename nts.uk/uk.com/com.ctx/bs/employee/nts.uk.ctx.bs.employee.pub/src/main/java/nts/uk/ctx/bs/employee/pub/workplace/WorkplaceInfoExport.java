/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.workplace;

import lombok.Builder;
import lombok.Data;

/**
 * The Class WorkplaceInfoExport.
 */
@Data
@Builder
public class WorkplaceInfoExport {

	/** The company id. */
	private String companyId;

	/** The history id. */
	private String historyId;

	/** The workplace id. */
	private String workplaceId;

	/** The workplace code. */
	private String workplaceCode;

	/** The workplace name. */
	private String workplaceName;

	/** The wkp generic name. */
	private String wkpGenericName;

	/** The wkp display name. */
	private String wkpDisplayName;

	/** The outside wkp code. */
	private String outsideWkpCode;

}
