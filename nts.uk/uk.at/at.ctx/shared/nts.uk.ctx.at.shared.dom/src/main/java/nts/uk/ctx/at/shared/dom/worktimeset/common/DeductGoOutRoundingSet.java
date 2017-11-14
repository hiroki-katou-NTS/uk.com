/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DeductGoOutRoundingSet.
 */
//計上控除別外出丸め設定
@Getter
public class DeductGoOutRoundingSet extends DomainObject {

	/** The deduct time rounding setting. */
	//控除時間の丸め設定
	private GoOutTimeRoundingSetting deductTimeRoundingSetting;
	
	/** The appro time rounding setting. */
	//計上時間の丸め設定
	private GoOutTimeRoundingSetting approTimeRoundingSetting;
}
