/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento;

/**
 * The Class WageTableDemensionDetailDto.
 */
@Setter
@Getter
public class WageTableDemensionDetailDto implements WageTableDetailGetMemento {

	/** The demension no. */
	private Integer demensionNo;

	/** The element mode setting. */
	private ElementModeDto elementModeSetting;

	@Override
	public DemensionNo getDemensionNo() {
		return DemensionNo.valueOf(this.demensionNo);
	}

	@Override
	public ElementMode getElementModeSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
