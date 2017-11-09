/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * The Class FlexCalcSetting.
 */
//フレックス計算設定

/**
 * Gets the calculate sharing.
 *
 * @return the calculate sharing
 */
@Getter
public class FlexCalcSetting extends DomainObject {

	/** The remove from work time. */
	// コアタイム内の外出を就業時間から控除する
	private UseAtr removeFromWorkTime;

	/** The calculate sharing. */
	// コアタイム内外の外出を分けて計算する
	private UseAtr calculateSharing;
}
