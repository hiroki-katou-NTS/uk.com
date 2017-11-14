/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;

/**
 * The Class EmTimezoneLateEarlyCommonSet.
 */
// 就業時間帯の遅刻・早退共通設定
@Getter
public class EmTimezoneLateEarlyCommonSet {

	/** The del from em time. */
	// 就業時間から控除する
	private boolean delFromEmTime;
}
