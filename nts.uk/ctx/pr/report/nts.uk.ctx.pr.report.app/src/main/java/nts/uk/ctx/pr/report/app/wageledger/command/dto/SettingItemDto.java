/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * The Class SettingItemDto.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingItemDto {
	/** The linkage code. */
	public String code;
	
	/** The type. */
	public boolean isAggregate;
	
	/** The order number. */
	public int orderNumber;
}
