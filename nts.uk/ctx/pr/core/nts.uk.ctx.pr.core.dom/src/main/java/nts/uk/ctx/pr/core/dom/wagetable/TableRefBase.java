/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import lombok.Getter;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Class TableRefBase.
 */
@Getter
public abstract class TableRefBase {

	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private String refNo;

	/** The ref name. */
	private String refName;

}
