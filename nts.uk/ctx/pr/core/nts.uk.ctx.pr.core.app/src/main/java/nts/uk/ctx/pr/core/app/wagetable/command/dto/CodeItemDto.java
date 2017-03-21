/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.Item;

/**
 * The Class CodeItem.
 */
@Getter
@Setter
@Builder
public class CodeItemDto implements Item {

	/** The reference code. */
	private String referenceCode;

	/** The uuid. */
	private String uuid;

}
