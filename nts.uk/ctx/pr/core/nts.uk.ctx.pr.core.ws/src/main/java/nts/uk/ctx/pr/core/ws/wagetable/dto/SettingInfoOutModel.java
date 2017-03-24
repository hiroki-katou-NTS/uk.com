/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;

import lombok.Data;

/**
 * The Class SettingInfoOutModel.
 */
@Data
public class SettingInfoOutModel {

	/** The element settings. */
	private List<ElementSettingDto> elementSettings;

}
