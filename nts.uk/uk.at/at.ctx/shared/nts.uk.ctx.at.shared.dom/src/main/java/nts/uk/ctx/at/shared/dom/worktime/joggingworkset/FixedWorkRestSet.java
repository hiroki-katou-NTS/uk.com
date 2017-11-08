/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import lombok.Getter;

/**
 * The Class FixedWorkRestSet.
 */
//固定勤務の休憩設定
@Getter
public class FixedWorkRestSet {

	/** The common rest set. */
	//共通の休憩設定
	private CommonRestSet commonRestSet; 
	
	/** The calculate method. */
	//計算方法
	private FixedRestCalculateMethod calculateMethod;
}
