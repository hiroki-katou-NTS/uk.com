/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingSetMemento;

/**
 * The Class FlexCalcSettingDto.
 */
@Getter
@Setter
public class FlexCalcSettingDto implements FlexCalcSettingSetMemento{

	/** The remove from work time. */
	private Integer removeFromWorkTime;

	/** The calculate sharing. */
	private Integer calculateSharing;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingSetMemento#
	 * setRemoveFromWorkTime(nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * UseAtr)
	 */
	@Override
	public void setRemoveFromWorkTime(UseAtr removeFromWorkTime) {
		this.removeFromWorkTime = removeFromWorkTime.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingSetMemento#
	 * setCalculateSharing(nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * UseAtr)
	 */
	@Override
	public void setCalculateSharing(UseAtr calculateSharing) {
		this.calculateSharing = calculateSharing.value;
	}
	
}
