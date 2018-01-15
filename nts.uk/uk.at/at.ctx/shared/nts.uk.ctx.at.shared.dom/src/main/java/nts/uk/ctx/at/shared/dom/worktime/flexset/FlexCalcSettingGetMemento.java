/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * The Interface FlexCalcSettingGetMemento.
 */
public interface FlexCalcSettingGetMemento {
	
	/**
	 * Gets the removes the from work time.
	 *
	 * @return the removes the from work time
	 */
	UseAtr getRemoveFromWorkTime();

	
	/**
	 * Gets the calculate sharing.
	 *
	 * @return the calculate sharing
	 */
	UseAtr getCalculateSharing();
}
