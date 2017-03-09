/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;

/**
 * The Class WageTableHistoryDto.
 */
@Builder
@Getter
@Setter
public class WageTableHistoryDto {

	/** The history id. */
	private String historyId;

	/** The start month. */
	private Integer startMonth;

	/** The end month. */
	private Integer endMonth;

	/** The head. */
	private WageTableHeadDto head;

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

}
