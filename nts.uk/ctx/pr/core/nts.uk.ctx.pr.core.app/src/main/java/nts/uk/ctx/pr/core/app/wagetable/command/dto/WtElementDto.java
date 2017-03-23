/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class WtElementDto.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WtElementDto {

	/** The demension no. */
	private Integer demensionNo;

	/** The type. */
	private Integer type;

	/** The reference code. */
	private String referenceCode;

}
