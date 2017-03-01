/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.reference;

import lombok.Getter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;

/**
 * The Class WageTableRefCd.
 */
@Getter
public class WageTableCodeRef {

	/** The company code. */
	private CompanyCode companyCode;

	/** The ref no. */
	private WtElementRefNo refNo;

	/** The ref name. */
	private String refName;

	/** The wage ref value. */
	private String wageRefValue;

	/** The wage person table. */
	private String wagePersonTable;

	/** The wage person field. */
	private String wagePersonField;

	/** The wage person query. */
	private String wagePersonQuery;

}
