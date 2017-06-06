/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 会社労働時間設定.
 */
public class CompanySetting extends AggregateRoot {

	/** フレックス勤務労働時間設定. */
	private FlexSetting flexSetting;

	/** 変形労働労働時間設定. */
	private DeformationLaborSetting deformationLaborSetting;

	/** 年. */
	private Year year;

	/** 会社ID. */
	private CompanyId companyId;

	/** 通常勤務労働時間設定. */
	private NormalSetting normalSetting;
}
