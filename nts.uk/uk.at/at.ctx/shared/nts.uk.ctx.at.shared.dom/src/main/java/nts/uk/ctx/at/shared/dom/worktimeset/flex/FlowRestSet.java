/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowRestSet.
 */
//流動休憩設定

/**
 * Gets the calculate method.
 *
 * @return the calculate method
 */
@Getter
public class FlowRestSet extends DomainObject {

	/** The use stamp. */
	//打刻を併用する
	private boolean useStamp;
	
	/** The use stamp calc method. */
	//打刻併用時の計算方法
	private FlowRestClockCalcMethod useStampCalcMethod;
	
	/** The time manager set atr. */
	//時刻管理設定区分
	private RestClockManageAtr 	timeManagerSetAtr;
	
	/** The calculate method. */
	//計算方法
	private FlowRestCalcMethod calculateMethod;
}
