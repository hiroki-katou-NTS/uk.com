/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class UsageUnitSetting.
 */
// 労働時間と日数の設定の利用単位の設定
@Getter
@Setter
public class UsageUnitSetting extends DomainObject {

	/** The employee setting. */
	// 社員の労働時間と日数の管理をする
	private boolean employeeSetting;

	/** The workplace setting. */
	// 職場の労働時間と日数の管理をする
	private boolean workplaceSetting;

	/** The employment setting. */
	// 雇用の労働時間と日数の管理をする
	private boolean employmentSetting;
}
