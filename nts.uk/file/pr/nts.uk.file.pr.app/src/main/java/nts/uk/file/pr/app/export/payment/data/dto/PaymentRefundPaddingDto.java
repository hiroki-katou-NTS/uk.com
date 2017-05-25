/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingOnceOut;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingThreeOut;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto.RefundPaddingTwoOut;

/**
 * The Class PrintSettingDto.
 */
@Getter
@Setter
public class PaymentRefundPaddingDto {
	/** The print type. */
	private int printType;
	
	/** The refund padding two dto. */
	private RefundPaddingTwoOut refundPaddingTwoDto;
	
	/** The refund padding once dto. */
	private RefundPaddingOnceOut refundPaddingOnceDto;
	
	/** The refund padding three dto. */
	private RefundPaddingThreeOut refundPaddingThreeDto;
}
