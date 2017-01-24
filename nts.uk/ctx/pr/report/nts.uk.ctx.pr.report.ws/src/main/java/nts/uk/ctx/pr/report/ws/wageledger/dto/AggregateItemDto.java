/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.wageledger.dto;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WageLedgerCategory;

/**
 * The Class AggregateItemDto.
 */
@Builder
public class AggregateItemDto {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The category. */
	public WageLedgerCategory category;
	
	/** The show name zero value. */
	public boolean showNameZeroValue;
	
	/** The show value zero value. */
	public boolean showValueZeroValue;
}
