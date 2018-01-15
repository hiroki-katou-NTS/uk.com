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
public class EmTimezoneLateEarlyCommonSetDto implements  EmTimezoneLateEarlyCommonSetSetMemento{
	
	/** The del from em time. */
	private boolean delFromEmTime;

	/**
	 * Instantiates a new em timezone late early common set dto.
	 */
	public EmTimezoneLateEarlyCommonSetDto() {}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * EmTimezoneLateEarlyCommonSetSetMemento#setDelFromEmTime(boolean)
	 */
	@Override
	public void setDelFromEmTime(boolean val) {
		this.delFromEmTime = val;		
	}

}
