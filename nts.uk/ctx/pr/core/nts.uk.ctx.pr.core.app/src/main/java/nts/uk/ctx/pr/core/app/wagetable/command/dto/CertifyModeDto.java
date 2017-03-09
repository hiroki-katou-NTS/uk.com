/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class CertifyMode.
 */
@Getter
@Setter
@Builder
public class CertifyModeDto extends BaseModeDto {

	/** The items. */

	/**
	 * Sets the items.
	 *
	 * @param items
	 *            the new items
	 */
	private List<CodeItemDto> items;

}
