/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

/**
 * Instantiates a new insu biz rate item dto.
 */
@Data
public class InsuBizRateItemFindOutDto {

	/** The insu biz type. */
	private Integer insuBizType;

	/** The insu rate. */
	private Double insuRate;

	/** The insu round. */
	private Integer insuRound;

	/** The insurance business type. */
	private String insuranceBusinessType;

	public InsuBizRateItemFindOutDto(InsuBizRateItem item) {
		this.insuBizType = item.getInsuBizType().value;
		this.insuRate = item.getInsuRate();
		this.insuRound = item.getInsuRound().value;
	}

}
