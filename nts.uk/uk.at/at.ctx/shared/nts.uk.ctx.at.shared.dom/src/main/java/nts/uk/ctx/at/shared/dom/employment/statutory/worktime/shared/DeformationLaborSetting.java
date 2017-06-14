/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import lombok.Value;
import nts.arc.layer.dom.DomainObject;

/**
 * 変形労働労働時間設定.
 */
@Value
public class DeformationLaborSetting extends DomainObject {

	/** 法定労働時間設定. */
	private WorkingTimeSetting statutorySetting;

	/** 週開始. */
	private WeekStart weekStart;
}
