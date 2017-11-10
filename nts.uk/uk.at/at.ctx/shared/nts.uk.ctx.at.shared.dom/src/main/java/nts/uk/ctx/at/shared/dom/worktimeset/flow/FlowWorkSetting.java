/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktimeset.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktimeset.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktimeset.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.LegalOTSetting;

/**
 * The Class FlowWorkSetting.
 */

/**
 * Gets the flow setting.
 *
 * @return the flow setting
 */
@Getter
// 流動勤務設定
public class FlowWorkSetting extends AggregateRoot {

	/** The company code. */
	// 会社ID
	private String companyCode;

	/** The working code. */
	// 就業時間帯コード
	private WorkTimeCode workingCode;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FlowOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private FlowHalfDayWorkTimezone halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private FlowStampReflectTimezone stampReflectTimezone;

	/** The designated setting. */
	// 法定内残業設定
	private LegalOTSetting designatedSetting;

	/** The flow setting. */
	// 流動設定
	private FlowWorkDedicateSetting flowSetting;
}
