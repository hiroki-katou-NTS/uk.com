/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowRestTimezone.
 */
//流動休憩時間帯

/**
 * Gets the here after rest set.
 *
 * @return the here after rest set
 */
@Getter
public class FlowRestTimezone extends DomainObject {

	/** The flow rest set. */
	// 流動休憩設定
	private List<FlowRestSetting> flowRestSet;

	/** The use here after rest set. */
	// 設定以降の休憩を使用する
	private boolean useHereAfterRestSet;

	/** The here after rest set. */
	// 設定以降の休憩設定
	private FlowRestSetting hereAfterRestSet;
}
