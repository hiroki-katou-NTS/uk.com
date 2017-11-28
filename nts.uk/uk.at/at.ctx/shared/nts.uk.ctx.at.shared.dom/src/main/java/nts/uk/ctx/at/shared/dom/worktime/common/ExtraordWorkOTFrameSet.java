/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ExtraordWorkOTFrameSet.
 */
//臨時勤務時の残業枠設定
@Getter
public class ExtraordWorkOTFrameSet extends DomainObject{

	//TODO 残業枠NO
//	private WorkFrameNo WorkFrameNo;
	
	/** The in legal work frame no. */
	//TODO change Integer to other data type 法内残業枠NO
	private Integer inLegalWorkFrameNo ;
	
	/** The settlement order. */
	//精算順序
	private Integer settlementOrder;
}
