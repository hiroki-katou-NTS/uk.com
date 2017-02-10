/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class InsuBizRateItemDto {

	/** The insu biz type. */
	private Integer insuBizType;

	/** The insu rate. */
	private Double insuRate;

	/** The insu round. */
	private Integer insuRound;


	/** The insurance business type. */
	private String insuranceBusinessType;

}
