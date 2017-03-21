/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;

/**
 * The Class CodeItem.
 */
@Getter
@Setter
@Builder
public class ElementItemDto {

	/** The uuid. */
	private ElementId uuid;

	/** The reference code. */
	private String referenceCode;

	/** The order number. */
	private Integer orderNumber;

	/** The start val. */
	private Double startVal;

	/** The end val. */
	private Double endVal;

}
