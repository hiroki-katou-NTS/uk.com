/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SubHolTransferSetDto.
 */
@Getter
@Setter
public class SubHolTransferSetDto {

	/** The certain time. */
	private Integer certainTime;
	
	/** The use division. */
	private boolean useDivision;
	
	/** The designated time. */
	private DesignatedTimeDto designatedTime;
	
	/** The Sub hol transfer set atr. */
	private Integer SubHolTransferSetAtr;
}
