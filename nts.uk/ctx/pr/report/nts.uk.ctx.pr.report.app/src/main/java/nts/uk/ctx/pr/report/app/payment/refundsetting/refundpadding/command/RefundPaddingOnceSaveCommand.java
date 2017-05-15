/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto.RefundPaddingOnceDto;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPadding;

/**
 * The Class RefundPaddingThreeSaveCommand.
 */
@Getter
@Setter
public class RefundPaddingOnceSaveCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dto. */
	private RefundPaddingOnceDto dto;

	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the refund padding
	 */
	public RefundPadding toDomain(String companyCode) {
		return this.dto.toDomain(companyCode);
	}
}
