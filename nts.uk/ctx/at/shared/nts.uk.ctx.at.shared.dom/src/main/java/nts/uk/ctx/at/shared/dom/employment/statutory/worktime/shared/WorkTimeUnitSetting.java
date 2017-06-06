/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import nts.arc.layer.dom.DomainObject;

/**
 * 労働時間と日数の設定の利用単位の設定.
 */
public class WorkTimeUnitSetting extends DomainObject {

	/** 職場の労働時間と日数の管理をする. */
	private boolean workPlaceSetting;

	/** 社員の労働時間と日数の管理をする. */
	private boolean employeeSetting;

	/** 雇用の労働時間と日数の管理をする. */
	private boolean employmentSetting;
}
