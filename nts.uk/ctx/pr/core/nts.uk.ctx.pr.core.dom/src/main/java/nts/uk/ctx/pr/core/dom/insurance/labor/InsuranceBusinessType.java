/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import lombok.Getter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;

/**
 * The Class InsuranceBusinessType.
 */
@Getter
public class InsuranceBusinessType {

	/** The company code. */
	private CompanyCode companyCode;

	/** The biz order. */
	private BusinessTypeEnum bizOrder;

	/** The biz name. */
	private BusinessName bizName;

}
