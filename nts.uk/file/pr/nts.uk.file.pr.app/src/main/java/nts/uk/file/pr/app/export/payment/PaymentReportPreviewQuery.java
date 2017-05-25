/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingOnceDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingThreeDto;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingTwoDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentRefundPaddingDto;

/**
 * The Class PaymentReportQuery.
 */

@Setter
@Getter
public class PaymentReportPreviewQuery {

	/** The page layout. */
	private int pageLayout;

	/** The refund padding two dto. */
	private RefundPaddingTwoDto refundPaddingTwoDto;

	/** The refund padding once dto. */
	private RefundPaddingOnceDto refundPaddingOnceDto;

	/** The refund padding three dto. */
	private RefundPaddingThreeDto refundPaddingThreeDto;

	/**
	 * To dto.
	 *
	 * @param layoutItem the layout item
	 * @return the payment refund padding dto
	 */
	public PaymentRefundPaddingDto toDto() {
		PaymentRefundPaddingDto dto = new PaymentRefundPaddingDto();
		int printType = 0;
		switch (this.getPageLayout()) {
		case 0:
			printType = 1;
			break;
		case 4:
			printType = 1;
			break;
		case 5:
			printType = 1;
			break;
		case 1:
			printType = 2;
			break;
		case 3:
			printType = 2;
			break;
		case 2:
			printType = 3;
			break;
		default:
			break;
		}
		dto.setPrintType(printType);
		dto.setRefundPaddingOnceDto(this.refundPaddingOnceDto);
		dto.setRefundPaddingTwoDto(this.refundPaddingTwoDto);
		dto.setRefundPaddingThreeDto(this.refundPaddingThreeDto);
		return dto;
	}
}
