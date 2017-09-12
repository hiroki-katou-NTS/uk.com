/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmploymentCondition.
 */
// 雇用条件
@Getter
public class EmploymentCondition extends DomainObject {

	/** The emp cd. */
	// 雇用コード
	private String empCd;

	/** The emp applicable cls. */
	// 雇用適用区分
	private EmpApplicableAtr empApplicableAtr;
}
