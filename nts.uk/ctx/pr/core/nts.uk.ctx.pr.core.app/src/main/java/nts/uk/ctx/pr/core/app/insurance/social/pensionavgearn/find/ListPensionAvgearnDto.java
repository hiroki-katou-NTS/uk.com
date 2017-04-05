/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ListPensionAvgearnDto.
 */
@Data
@Builder
public class ListPensionAvgearnDto {

	/** The list pension avgearn. */
	private List<PensionAvgearnDto> listPensionAvgearn;

	/** The history id. */
	private String historyId;
}
