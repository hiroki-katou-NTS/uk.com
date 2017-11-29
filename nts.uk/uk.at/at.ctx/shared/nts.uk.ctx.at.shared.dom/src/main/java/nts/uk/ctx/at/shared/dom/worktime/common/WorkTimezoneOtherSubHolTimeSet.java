/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneOtherSubHolTimeSet.
 */
//就業時間帯別代休時間設定
@Getter
public class WorkTimezoneOtherSubHolTimeSet extends DomainObject {

	/** The sub hol time set. */
	// 代休時間設定
	private SubHolTransferSet subHolTimeSet;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The origin atr. */
	// 発生元区分
	private OriginAtr originAtr;
}
