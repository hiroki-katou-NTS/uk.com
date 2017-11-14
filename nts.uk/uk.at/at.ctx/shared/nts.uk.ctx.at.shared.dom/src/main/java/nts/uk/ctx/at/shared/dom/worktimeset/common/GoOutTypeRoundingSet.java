/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;

/**
 * The Class GoOutTypeRoundingSet.
 */
//外出種類別丸め設定
@Getter
public class GoOutTypeRoundingSet {

	/** The offical use compen go out. */
	//公用、有償外出
	private DeductGoOutRoundingSet officalUseCompenGoOut;
	
	/** The private union go out. */
	//私用、組合外出
	private DeductGoOutRoundingSet privateUnionGoOut;
}
