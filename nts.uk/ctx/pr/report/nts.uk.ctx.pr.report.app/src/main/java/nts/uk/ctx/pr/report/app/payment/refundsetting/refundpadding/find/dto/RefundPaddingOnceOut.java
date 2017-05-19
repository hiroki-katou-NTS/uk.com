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
 * The Class RefundPaddingOnceOut.
 */

@Getter
@Setter
public class RefundPaddingOnceOut implements RefundPaddingSetMemento {

	/** The padding top. */
	private BigDecimal paddingTop;

	/** The padding left. */
	private BigDecimal paddingLeft;

	/**
	 * Default data.
	 */
	public void defaultData() {
		this.paddingTop = BigDecimal.ZERO;
		this.paddingLeft = BigDecimal.ZERO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setPrintType(nts.uk.ctx.pr.report.dom.payment.
	 * refundsetting.refundpadding.PrintType)
	 */
	@Override
	public void setPrintType(PrintType printType) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setPaddingTop(nts.uk.ctx.pr.report.dom.payment.
	 * refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setPaddingTop(SizeLimit paddingTop) {
		this.paddingTop = paddingTop.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setPaddingLeft(nts.uk.ctx.pr.report.dom.payment.
	 * refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setPaddingLeft(SizeLimit paddingLeft) {
		this.paddingLeft = paddingLeft.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setUpperAreaPaddingTop(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setUpperAreaPaddingTop(SizeLimit upperAreaPaddingTop) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setUnderAreaPaddingTop(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setUnderAreaPaddingTop(SizeLimit underAreaPaddingTop) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setIsShowBreakLine(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.ShowBreakLine)
	 */
	@Override
	public void setIsShowBreakLine(ShowBreakLine isShowBreakLine) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setBreakLineMargin(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setBreakLineMargin(SizeLimit breakLineMargin) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setMiddleAreaPaddingTop(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setMiddleAreaPaddingTop(SizeLimit middleAreaPaddingTop) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setBreakLineMarginTop(nts.uk.ctx.pr.report.dom.
	 * payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setBreakLineMarginTop(SizeLimit breakLineMarginTop) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setBreakLineMarginButtom(nts.uk.ctx.pr.report.dom
	 * .payment.refundsetting.refundpadding.SizeLimit)
	 */
	@Override
	public void setBreakLineMarginButtom(SizeLimit breakLineMarginButtom) {
		// No thing code

	}

}
