/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class FlexWorkSetting.
 */
@Getter
// フレックス勤務設定
public class FlexWorkSetting extends AggregateRoot {

	/** The working code. */
	// 就業時間帯コード
	private String workingCode;

	/** The weekday work time. */
	// 平日勤務時間帯
	private FleWeekdayWorkTime weekdayWorkTime;

	/** The offday work time. */
	// 休日勤務時間帯
	private FleOffdayWorkTime offdayWorkTime;

	// 共通設定
	// private WorkTimeCommonSet commonSetting;

	// 休憩設定: 固定勤務の休憩設定
	// private 固定勤務の休憩設定 restSetting;

	// 打刻反映時間帯
	// private List<打刻反映時間帯> stampImprintingTime

	// 半日用シフトを使用する
	// private Boolean useHalfDayShift;

	// コアタイム時間帯設定
	// private コアタイム時間帯設定 coreTimeSetting

}
