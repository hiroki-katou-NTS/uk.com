/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import lombok.AllArgsConstructor;

/**
 * The Enum EmpConditionClassification.
 */
// 雇用適用区分
@AllArgsConstructor
public enum EmpConditionClassification {

	/** The not apply. */
	// 適用しない
	NOT_APPLY(0),

	/** The apply. */
	// 適用する
	APPLY(1);

	/** The value. */
	public final int value;
}
