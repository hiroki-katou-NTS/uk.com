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
 * The Class RefundPaddingTwoOut.
 */

/**
 * Gets the checks if is show break line.
 *
 * @return the checks if is show break line
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

	/**
	 * Default data.
	 */
	public void defaultData() {
		this.upperAreaPaddingTop = BigDecimal.ZERO;
		this.underAreaPaddingTop = BigDecimal.ZERO;
		this.paddingLeft = BigDecimal.ZERO;
		this.breakLineMargin = BigDecimal.ZERO;
		this.isShowBreakLine = 0;
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
		// No thing code
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
		this.upperAreaPaddingTop = upperAreaPaddingTop.v();
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
		this.underAreaPaddingTop = underAreaPaddingTop.v();
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
		this.isShowBreakLine = isShowBreakLine.value;
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
		this.breakLineMargin = breakLineMargin.v();
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
