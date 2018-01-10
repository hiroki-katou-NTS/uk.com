/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Class FixedWorkSetting.
 */
@Getter
@AllArgsConstructor
// 固定勤務設定
public class FixedWorkSetting extends AggregateRoot {

	/** The working code. */
	// 就業時間帯コード
	private String workingCode;

	// // 半日用シフトを使用する
	// private Boolean useHalfDayShift;

	/** The offday work time. */
	// 休日勤務時間帯
	private FixOffdayWorkTime offdayWorkTime;

	/** The weekday work time. */
	// 平日勤務時間帯
	private Map<AmPmClassification, FixWeekdayWorkTime> weekdayWorkTimes;

	// 打刻反映時間帯
	// private List<打刻反映時間帯> stampImprintingTime

	// 残業設定
	// private 固定勤務の残業設定 overtimeSetting

	// 詳細設定
	 private WorkTimeCommonSet advancedSetting;
	
}
