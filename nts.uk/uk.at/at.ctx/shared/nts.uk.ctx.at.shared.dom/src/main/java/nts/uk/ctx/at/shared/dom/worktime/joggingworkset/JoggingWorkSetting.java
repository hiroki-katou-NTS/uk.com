/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class FixedWorkSetting.
 */
@Getter
// 時差勤務設定
public class JoggingWorkSetting extends AggregateRoot {

	/** The working code. */
	// 就業時間帯コード
	private String workingCode;

	/** The weekday work time. */
	// 平日勤務時間帯
	private JogWeekdayWorkTime weekdayWorkTime;

	// 共通設定
	// private WorkTimeCommonSet commonSetting;

	// 変動可能範囲
	// private 就業時間帯変動可能範囲 variableRange;

	// 休憩設定: 固定勤務の休憩設定
	// private 固定勤務の休憩設定 restSetting;

	/** The offday work time. */
	// 休日勤務時間帯
	private JogOffdayWorkTime offdayWorkTime;

	// 打刻反映時間帯
	// private List<打刻反映時間帯> stampImprintingTime

	// 半日用シフトを使用する
	// private Boolean useHalfDayShift;

	// 残業設定
	// private 固定勤務の残業設定 overtimeSetting

}
