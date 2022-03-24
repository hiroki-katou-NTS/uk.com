/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneLateEarlyCommonSetSetMemento;

/**
 * The Class EmTimezoneLateEarlyCommonSetDto.
 */
@Getter
@Setter
public class EmTimezoneLateEarlyCommonSetDto implements EmTimezoneLateEarlyCommonSetSetMemento{
	
	private boolean delFromEmTime = false;
	
	private boolean includeByApp = false;

	/**
	 * Instantiates a new em timezone late early common set dto.
	 */
	public EmTimezoneLateEarlyCommonSetDto() {}
}
