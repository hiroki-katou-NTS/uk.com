/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.ElementItemDto;

/**
 * The Class SettingInfoOutModel.
 */
@Getter
@Setter
public class SettingInfoOutModel {

	/** The item list. */
	private List<ElementItemDto> itemList;

}
