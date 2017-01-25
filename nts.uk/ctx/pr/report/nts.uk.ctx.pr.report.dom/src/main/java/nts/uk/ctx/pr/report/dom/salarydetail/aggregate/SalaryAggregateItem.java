/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.salarydetail.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class SalaryAggregateItem.
 */
@Getter
public class SalaryAggregateItem {
	
	/** The code. */
	private String code;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The name. */
	private SalaryAggregateItemName name;
	
	/** The sub item codes. */
	private Set<String> subItemCodes;
	
	/** The tax division. */
	private TaxDivision taxDivision;
	
	/** The item category. */
	//TODO: need review with EAP.
	private int itemCategory;
	
}
