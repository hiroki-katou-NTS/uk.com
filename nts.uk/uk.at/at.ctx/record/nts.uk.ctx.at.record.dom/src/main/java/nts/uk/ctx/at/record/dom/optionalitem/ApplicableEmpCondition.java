/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class ApplicableEmpCondition.
 */
// 適用する雇用条件
@Getter
public class ApplicableEmpCondition extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	// 雇用条件
	private List<EmploymentCondition> employmentConditions;

}
