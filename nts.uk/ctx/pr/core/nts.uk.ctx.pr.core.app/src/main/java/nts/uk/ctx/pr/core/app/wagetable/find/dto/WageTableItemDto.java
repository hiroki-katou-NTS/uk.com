/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import java.util.List;

import lombok.Builder;

/**
 * The Class WageTableItemDto.
 */
@Builder
public class WageTableItemDto {

	/** The wage table code. */
	public String wageTableCode;

	/** The wage table name. */
	public String wageTableName;

	/** The histories. */
	public List<WageTableHistoryItemDto> histories;

}
