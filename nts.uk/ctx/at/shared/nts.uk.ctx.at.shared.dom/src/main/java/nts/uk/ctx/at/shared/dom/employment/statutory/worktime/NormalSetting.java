/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.DomainObject;

/**
 * 通常勤務労働時間設定.
 */
public class NormalSetting extends DomainObject {
	
	/** 法定労働時間設定. */
	private WorkingTimeSetting statutorySetting;

	/** 週開始. */
	private WeekStart weekStart;
}