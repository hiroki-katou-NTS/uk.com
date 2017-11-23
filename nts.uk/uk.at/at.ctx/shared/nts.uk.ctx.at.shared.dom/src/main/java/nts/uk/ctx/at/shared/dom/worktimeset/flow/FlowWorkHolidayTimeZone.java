/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.BreakFrameNo;

/**
 * The Class FlowWorkHolidayTimeZone.
 */
//流動休出時間帯
@Getter
public class FlowWorkHolidayTimeZone extends DomainObject {

	/** The worktime no. */
	// 就業時間帯NO
	private Integer worktimeNo;
	
	/** The use in legal break restrict time. */
	// 法定内休出を拘束時間として扱う
	private boolean useInLegalBreakRestrictTime;
	
	/** The in legal break frame no. */
	// 法定内休出枠NO
	private BreakFrameNo inLegalBreakFrameNo;
	
	/** The use out legal break restrict time. */
	// 法定外休出を拘束時間として扱う
	private boolean useOutLegalBreakRestrictTime;

	/** The out legal break frame no. */
	// 法定外休出枠NO
	private BreakFrameNo outLegalBreakFrameNo;

	/** The use out legal pub hol restrict time. */
	// 法定外祝日を拘束時間として扱う
	private boolean useOutLegalPubHolRestrictTime;

	/** The out legal pub hol frame no. */
	// 法定外祝日枠NO
	private BreakFrameNo outLegalPubHolFrameNo;
	
	//流動時間設定
	private FlowTimeSetting flowTimeSetting;
}
