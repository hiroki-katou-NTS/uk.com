/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.element.BaseItem;

/**
 * The Class CodeItem.
 */
@Getter
@Setter
@Builder
public class CodeItemDto implements BaseItem {

	/** The reference code. */
	private String referenceCode;

	/** The uuid. */
	private String uuid;

}
