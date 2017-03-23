/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import lombok.Builder;
import lombok.Data;

/**
 * The Class CodeItem.
 */
@Data
@Builder
public class ElementItemDto {

	/** The uuid. */
	private String uuid;

	/** The reference code. */
	private String referenceCode;

	/** The order number. */
	private Integer orderNumber;

	/** The start val. */
	private Double startVal;

	/** The end val. */
	private Double endVal;

}
