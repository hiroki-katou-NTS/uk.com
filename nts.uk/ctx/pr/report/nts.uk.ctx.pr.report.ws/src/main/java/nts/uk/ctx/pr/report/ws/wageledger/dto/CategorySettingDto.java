/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.wageledger.dto;

import java.util.List;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.wageledger.WageLedgerCategory;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.PaymentType;

/**
 * The Class CategorySettingDto.
 */
@Builder
public class CategorySettingDto {
	
	/** The category. */
	public WageLedgerCategory category;
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The output items. */
	public List<SettingItemDto> outputItems;
}
