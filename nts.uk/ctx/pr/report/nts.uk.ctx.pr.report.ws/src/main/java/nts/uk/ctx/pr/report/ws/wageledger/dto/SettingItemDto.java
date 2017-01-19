/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.wageledger.dto;

import lombok.Builder;

/**
 * The Class SettingItemDto.
 */
@Builder
public class SettingItemDto {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The is aggregate. */
	public Boolean isAggregateItem;
}
