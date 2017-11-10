/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.common.WorkTimezoneCommonSet;

/**
 * The Class FlexWorkSetting.
 */
@Getter
// フレックス勤務設定
public class FlexWorkSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private String workTimeCode;

	/** The core time setting. */
	// コアタイム時間帯
	private CoreTimeSetting coreTimeSetting;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The offday work time. */
	// 休日勤務時間帯
	private FlexOffdayWorkTime offdayWorkTime;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private boolean useHalfDayShift;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private List<FlexHalfDayWorkTime> halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezone;

	/** The calculate setting. */
	// 計算設定
	private FlexCalcSetting calculateSetting;
}
