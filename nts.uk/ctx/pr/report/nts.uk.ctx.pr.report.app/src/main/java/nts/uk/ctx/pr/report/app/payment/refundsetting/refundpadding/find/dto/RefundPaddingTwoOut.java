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
 * The Class RefundPaddingTwoDto.
 */

@Getter
@Setter
public class RefundPaddingTwoOut implements RefundPaddingSetMemento {

	/** The upper area padding top. */
	private BigDecimal upperAreaPaddingTop;

	/** The under area padding top. */
	private BigDecimal underAreaPaddingTop;

	/** The padding left. */
	private BigDecimal paddingLeft;

	/** The break line margin. */
	private BigDecimal breakLineMargin;

	/** The is show break line. */
	private int isShowBreakLine;

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
		// No thing code
	}

	@Override
	public void setPaddingLeft(SizeLimit paddingLeft) {
		this.paddingLeft = paddingLeft.v();

	}

	@Override
	public void setUpperAreaPaddingTop(SizeLimit upperAreaPaddingTop) {
		this.upperAreaPaddingTop = upperAreaPaddingTop.v();
	}

	@Override
	public void setUnderAreaPaddingTop(SizeLimit underAreaPaddingTop) {
		this.underAreaPaddingTop = underAreaPaddingTop.v();
	}

	@Override
	public void setIsShowBreakLine(ShowBreakLine isShowBreakLine) {
		this.isShowBreakLine = isShowBreakLine.value;
	}

	@Override
	public void setBreakLineMargin(SizeLimit breakLineMargin) {
		this.breakLineMargin = breakLineMargin.v();
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
