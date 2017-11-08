/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.common.WorkTimezoneCommonSet;

/**
 * The Class TimeDiffWorkSetting.
 */

@Getter
// 時差勤務設定
public class TimeDiffWorkSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The employment timezone code. */
	// 就業時間帯コード
	private EmploymentTimezoneCode employmentTimezoneCode;

	/** The rest set. */
	// 休憩設定
	private FixedWorkRestSet restSet;

	/** The dayoff work timezone. */
	// 休日勤務時間帯
	private TimeDiffDayOffWorkTimezone dayoffWorkTimezone;

	/** The common set. */
	// 共通設定
	private WorkTimezoneCommonSet commonSet;

	/** The is use half day shift. */
	// 半日用シフトを使用する
	private boolean isUseHalfDayShift;

	/** The change extent. */
	// 変動可能範囲
	private EmTimezoneChangeExtent changeExtent;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private List<TimeDiffHalfDayWorkTimezone> halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private TimeDiffWorkStampReflectTimezone stampReflectTimezone;

	/** The overtime setting. */
	// 残業設定
	private InLegalOTSet overtimeSetting;

}
