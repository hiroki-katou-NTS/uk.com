/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find.dto;

import java.util.List;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class CategorySettingDto.
 */
@Builder
public class CategorySettingDto {
	
	/** The category. */
	public WLCategory category;
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The output items. */
	public List<SettingItemDto> outputItems;
}
