/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ListHealthInsuranceAvgearnDto.
 */
@Builder
@Data
public class ListHealthInsuranceAvgearnDto {

	/** The history id. */
	private String historyId;

	/** The list health insurance avgearn. */
	private List<HealthInsuranceAvgearnDto> listHealthInsuranceAvgearn;
}
