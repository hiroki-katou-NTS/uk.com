/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class HolidayFramset.
 */
//臨時勤務時の休出枠設定
@Getter
public class HolidayFramset extends DomainObject{
	
	/** The in legal breakout frame no. */
	//法定内休出枠NO
	private BreakoutFrameNo inLegalBreakoutFrameNo;
	
	/** The out legal breakout frame no. */
	//法定外休出枠NO
	private BreakoutFrameNo outLegalBreakoutFrameNo;
	
	/** The out legal pub hol frame no. */
	//法定外祝日枠NO
	private BreakoutFrameNo outLegalPubHolFrameNo;
}
