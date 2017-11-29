/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PrioritySet.
 */
//優先設定
@Getter
public class PrioritySet extends DomainObject {

	/** The priority atr. */
	//優先区分
	private MultiStampTimePiorityAtr priorityAtr;
	
	/** The stamp atr. */
	//打刻区分
	private StampPiorityAtr stampAtr;
}
