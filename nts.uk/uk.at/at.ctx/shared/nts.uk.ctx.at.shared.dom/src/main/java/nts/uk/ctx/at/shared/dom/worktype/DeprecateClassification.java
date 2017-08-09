/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum DeprecateClassification.
 */
@AllArgsConstructor
public enum DeprecateClassification {

	/** The Deprecated. */
	// 廃止区分
	Deprecated(1), // 廃止する

	/** The Not deprecated. */
	NotDeprecated(0); // 廃止しない

	/** The value. */
	public final int value;
}
