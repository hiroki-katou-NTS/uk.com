/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class RefMode.
 */
@Getter
@Setter
public class RefModeDto extends BaseModeDto {

	/** The company code. */
	private String companyCode;

	/** The ref no. */
	private String refNo;

	/** The items. */
	private List<CodeItemDto> items;

}
