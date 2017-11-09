/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//流動休憩設定
@Getter
public class FlowRestSet extends DomainObject {

	//打刻を併用する
	private boolean useStamp;
	
	//打刻併用時の計算方法
	private FlowRestClockCalcMethod useStampCalcMethod;
	
	//時刻管理設定区分
	private RestClockManageAtr 	timeManagerSetAtr;
	
	//計算方法
	private FlowRestCalcMethod calculateMethod;
}
