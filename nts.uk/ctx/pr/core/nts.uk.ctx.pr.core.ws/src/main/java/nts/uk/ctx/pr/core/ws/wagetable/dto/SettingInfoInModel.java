/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;

import lombok.Data;

/**
 * The Class SettingInfoInModel.
 */
@Data
public class SettingInfoInModel {

	/** The company code. */
	private String companyCode;

	/** The history id. */
	private String historyId;

	/** The element setting. */
	private List<ElementSettingDto> elementSettings;

}
