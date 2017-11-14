/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class RoundingSet.
 */
//丸め設定
@Getter
public class RoundingSet extends DomainObject {
	
	/** The rounding set. */
	// 時刻丸め
	private RoundingTime roundingSet;
	
	/** The section. */
	// 打優丸め区分
	private Superiority section;
}
