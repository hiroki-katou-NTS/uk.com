/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TimeRoundingSetting.
 */
@Getter
// 時間丸め設定
public class TimeRoundingSetting extends AggregateRoot {

	/** The rounding time. */
	// 単位
	private Unit unit;

	/** The rounding. */
	// 端数処理
	private Rounding rounding;

}
