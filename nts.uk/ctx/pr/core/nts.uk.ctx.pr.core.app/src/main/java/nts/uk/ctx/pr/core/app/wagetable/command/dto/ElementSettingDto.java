/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WtElementSetting.
 */
@Getter
@Setter
public class ElementSettingDto {

	/** The demension no. */
	private Integer demensionNo;

	/** The type. */
	private Integer type;

	/** The item list. */
	private List<ElementItemDto> itemList;

}
