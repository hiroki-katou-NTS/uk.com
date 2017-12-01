/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * The Interface FlexCalcSettingSetMemento.
 */
public interface FlexCalcSettingSetMemento {

	/**
	 * Sets the removes the from work time.
	 *
	 * @param removeFromWorkTime the new removes the from work time
	 */
	void setRemoveFromWorkTime(UseAtr removeFromWorkTime);
	
	
	/**
	 * Sets the calculate sharing.
	 *
	 * @param calculateSharing the new calculate sharing
	 */
	void setCalculateSharing(UseAtr calculateSharing);
}
