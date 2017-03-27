/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;

@Data
public class UnemployeeInsuranceRateItemSettingDto {
	
	/** The round atr. */
	private Integer roundAtr;

	/** The code. */
	private Double rate;
}
