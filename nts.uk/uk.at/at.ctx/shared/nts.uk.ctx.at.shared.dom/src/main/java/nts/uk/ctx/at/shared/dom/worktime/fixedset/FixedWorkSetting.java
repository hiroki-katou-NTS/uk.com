/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Class FixedWorkSetting.
 */
@Getter
// 固定勤務設定
public class FixedWorkSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FixOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private Boolean useHalfDayShift;

	/** The fixed work rest setting. */
	//固定勤務の休憩設定
	private FixedWorkRestSet fixedWorkRestSetting;
	
	/** The half day work timezone. */
	// 平日勤務時間帯
	private List<FixHalfDayWorkTimezone> halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezone;

	/** The legal OT setting. */
	// 法定内残業設定
    private LegalOTSetting legalOTSetting;
}
