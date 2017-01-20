/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.core.app.insurance.labor.accidentrate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccidentInsuranceRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The short name. */
	private List<InsuBizRateItemDto> rateItems;

}
