/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.commonset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;

//代休振替設定
@Getter
public class SubHolTransferSet extends DomainObject{
	
	//一定時間
	private OneDayTime certainTime;
	
	//使用区分
	private boolean useDivision;
	
	//指定時間
	private DesignatedTime designatedTime;
	
	//振替区分
	private SubHolTransferSetAtr SubHolTransferSetAtr;
}
