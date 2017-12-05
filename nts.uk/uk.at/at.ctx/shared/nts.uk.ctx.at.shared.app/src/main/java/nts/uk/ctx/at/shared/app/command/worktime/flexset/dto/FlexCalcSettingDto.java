/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento;

/**
 * The Class FlexCalcSettingDto.
 */
@Getter
@Setter
public class FlexCalcSettingDto implements FlexCalcSettingGetMemento{

	/** The remove from work time. */
	private Integer removeFromWorkTime;

	/** The calculate sharing. */
	private Integer calculateSharing;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento#getRemoveFromWorkTime()
	 */
	@Override
	public UseAtr getRemoveFromWorkTime() {
		return UseAtr.valueOf(this.removeFromWorkTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento#getCalculateSharing()
	 */
	@Override
	public UseAtr getCalculateSharing() {
		return UseAtr.valueOf(this.calculateSharing);
	}

	
}
