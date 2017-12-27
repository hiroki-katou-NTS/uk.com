/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidWorkBreakSetting;

/**
 * The Class FixedWorkSetting.
 */
@Getter
// 固定勤務設定
public class FluidWorkSetting extends AggregateRoot {

	/** The working code. */
	// 就業時間帯コード
	private nts.uk.ctx.at.shared.dom.worktime_old.SiftCode workingCode;

	/** The offday work time. */
	// 休日勤務時間帯
	private FluOffdayWorkTime offdayWorkTime;

	/** The weekday work time. */
	// 平日勤務時間帯
	private FluWeekdayWorkTime weekdayWorkTime;

	// 共通設定
	// private WorkTimeCommonSet commonSetting;

	// 専用設定
	// private 勤務専用設定 designatedSetting;

	// 休憩設定
	private FluidWorkBreakSetting restSetting;

	// 打刻反映時間帯
	// private List<打刻反映時間帯> stampImprintingTime
}
