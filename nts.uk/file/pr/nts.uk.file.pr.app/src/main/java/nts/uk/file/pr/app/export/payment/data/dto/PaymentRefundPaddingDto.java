/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingOnceDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingThreeDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingTwoDto;

/**
 * The Class PrintSettingDto.
 */
@Getter
@Setter
public class PaymentRefundPaddingDto {
	/** The print type. */
	private int printType;
	
	/** The refund padding two dto. */
	private RefundPaddingTwoDto refundPaddingTwoDto;
	
	/** The refund padding once dto. */
	private RefundPaddingOnceDto refundPaddingOnceDto;
	
	/** The refund padding three dto. */
	private RefundPaddingThreeDto refundPaddingThreeDto;
}
