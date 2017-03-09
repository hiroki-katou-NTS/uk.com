/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new certification find dto.
 */
@Getter
@Setter
public class WageTableDetailDto {

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

}
