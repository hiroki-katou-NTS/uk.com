/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSetGetMemento;

/**
 * The Class EmTimezoneLateEarlyCommonSetDto.
 */
@Getter
@Setter
public class EmTimezoneLateEarlyCommonSetDto implements EmTimezoneLateEarlyCommonSetGetMemento{
	
	private boolean delFromEmTime;
	private boolean includeByApp;
	
	@Override
	public boolean getDelFromEmTime() {
		return this.delFromEmTime;
	}

	@Override
	public boolean getIncludeByApp() {
		return this.includeByApp;
	}
}
