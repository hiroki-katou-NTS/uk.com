/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.shared;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * フレックス勤務労働時間設定.
 */
@Getter
@Setter
public class FlexSetting extends DomainObject {

	/** 所定労働時間設定. */
	private WorkingTimeSetting specifiedSetting;

	/** 法定労働時間設定. */
	private WorkingTimeSetting statutorySetting;
}
