/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.SizeLimit;

/**
 * The Class RefundPaddingThreeDto.
 */
@Getter
@Setter
public class RefundPaddingOnceOut implements RefundPaddingSetMemento {

	/** The upper area padding top. */
	private BigDecimal paddingTop;

	/** The middle area padding top. */
	private BigDecimal paddingLeft;

	@Override
	public void setCompanyCode(String companyCode) {
		// No thing code
	}

	@Override
	public void setPrintType(PrintType printType) {
		// No thing code

	}

	@Override
	public void setPaddingTop(SizeLimit paddingTop) {
		this.paddingTop = paddingTop.v();
	}

	@Override
	public void setPaddingLeft(SizeLimit paddingLeft) {
		this.paddingLeft = paddingLeft.v();
	}

	@Override
	public void setUpperAreaPaddingTop(SizeLimit upperAreaPaddingTop) {
		// No thing code

	}

	@Override
	public void setUnderAreaPaddingTop(SizeLimit underAreaPaddingTop) {
		// No thing code

	}

	@Override
	public void setIsShowBreakLine(ShowBreakLine isShowBreakLine) {
		// No thing code

	}

	@Override
	public void setBreakLineMargin(SizeLimit breakLineMargin) {
		// No thing code

	}

	@Override
	public void setMiddleAreaPaddingTop(SizeLimit middleAreaPaddingTop) {
		// No thing code

	}

	@Override
	public void setBreakLineMarginTop(SizeLimit breakLineMarginTop) {
		// No thing code

	}

	@Override
	public void setBreakLineMarginButtom(SizeLimit breakLineMarginButtom) {
		// No thing code

	}

}
