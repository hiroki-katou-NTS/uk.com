/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.guideline;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CommonEstablishment.
 */
@Getter
// 共通目安設定
public class CommonGuidelineSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The alarm colors. */
	// アラーム色
	private EstimatedAlarmColor alarmColors;

	/** The estimate time. */
	// 目安時間条件
	private ReferenceCondition estimateTime;

	/** The estimate price. */
	// 目安金額条件
	private ReferenceCondition estimatePrice;

	/** The estimate number of days. */
	// 目安日数条件
	private ReferenceCondition estimateNumberOfDays;
}
