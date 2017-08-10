/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum DeprecateClassification.
 */
// 廃止区分
@AllArgsConstructor
public enum DeprecateClassification {
	
	/** The Deprecated. */
	// 廃止する
	Deprecated(1), 

	/** The Not deprecated. */
	// 廃止しない
	NotDeprecated(0); 

	/** The value. */
	public final int value;
}
