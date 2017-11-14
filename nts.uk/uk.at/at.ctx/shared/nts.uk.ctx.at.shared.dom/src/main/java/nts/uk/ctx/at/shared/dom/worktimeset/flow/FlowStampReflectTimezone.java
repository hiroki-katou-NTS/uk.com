/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flow;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktimeset.fixed.StampReflectTimezone;

/**
 * The Class FlowStampReflectTimezone.
 */
//流動打刻反映時間帯

/**
 * Gets the stamp reflect timezone.
 *
 * @return the stamp reflect timezone
 */
@Getter
public class FlowStampReflectTimezone extends DomainObject {

	// TODO primitive ２回目勤務の反映基準時間
	// private twoTimesWorkReflectBasicTime

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> stampReflectTimezone;
}
