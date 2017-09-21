/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.AllArgsConstructor;

/**
 * The Enum EmpConditionAtr.
 */
// 雇用条件区分

@AllArgsConstructor
public enum EmpConditionAtr {

	/** The no condition. */
	// 条件なし
	NO_CONDITION(0),

	/** The with condition. */
	// 条件あり
	WITH_CONDITION(1);

	/** The value. */
	public final int value;
}
