/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlexCalcSetting.
 */
// フレックス計算設定
@Getter
public class FlexCalcSetting extends WorkTimeDomainObject {

	/** The remove from work time. */
	// コアタイム内の外出を就業時間から控除する
	private UseAtr removeFromWorkTime;

	/** The calculate sharing. */
	// コアタイム内外の外出を分けて計算する
	private UseAtr calculateSharing;

	/**
	 * Instantiates a new flex calc setting.
	 *
	 * @param memento the memento
	 */
	public FlexCalcSetting(FlexCalcSettingGetMemento memento) {
		this.removeFromWorkTime = memento.getRemoveFromWorkTime();
		this.calculateSharing = memento.getCalculateSharing();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexCalcSettingSetMemento memento){
		memento.setRemoveFromWorkTime(this.removeFromWorkTime);
		memento.setCalculateSharing(this.calculateSharing);
	}
}
