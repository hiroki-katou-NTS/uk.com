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
 * The Class RefMode.
 */
@Getter
@Setter
@Builder
public class RefModeDto extends BaseModeDto {

	/** The ref no. */
	private String refNo;

	/** The items. */
	private List<CodeItemDto> items;

}
