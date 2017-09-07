/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import lombok.Getter;

/**
 * The Class EmploymentCondition.
 */
// 雇用条件
@Getter
public class EmploymentCondition {

	/** The emp cd. */
	// 雇用コード
	private String empCd;

	/** The emp applicable cls. */
	// 雇用適用区分
	private EmpConditionClassification empApplicableCls;
}
