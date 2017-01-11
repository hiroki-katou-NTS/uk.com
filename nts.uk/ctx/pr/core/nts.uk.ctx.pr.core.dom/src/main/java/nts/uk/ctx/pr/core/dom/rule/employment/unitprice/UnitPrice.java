/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * The Class UnitPrice.
 */
@Getter
public class UnitPrice extends AggregateRoot {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private UnitPriceCode code;

	/** The name. */
	private UnitPriceName name;

}
