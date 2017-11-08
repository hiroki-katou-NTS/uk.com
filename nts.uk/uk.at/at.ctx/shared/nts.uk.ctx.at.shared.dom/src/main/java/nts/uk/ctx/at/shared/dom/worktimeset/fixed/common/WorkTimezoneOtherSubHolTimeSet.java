/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.SiftCode;

//就業時間帯別代休時間設定
@Getter
public class WorkTimezoneOtherSubHolTimeSet extends DomainObject{
	
	//代休時間設定
	private SubHolTransferSet subHolTimeSet;
	
	//就業時間帯コード
	private SiftCode siftCode;
	
	//発生元区分
	private OriginAtr originAtr; 
}
