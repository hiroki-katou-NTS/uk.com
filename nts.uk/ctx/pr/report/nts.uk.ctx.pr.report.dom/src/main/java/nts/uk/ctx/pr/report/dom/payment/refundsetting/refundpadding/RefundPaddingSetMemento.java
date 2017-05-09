/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

/**
 * The Interface RefundPaddingSetMemento.
 */
public interface RefundPaddingSetMemento {

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(String companyCode);

	/**
	 * Sets the prints the type.
	 *
	 * @param printType the new prints the type
	 */
	void setPrintType(PrintType printType);

	/**
	 * Sets the padding top.
	 *
	 * @param paddingTop the new padding top
	 */
	void setPaddingTop(SizeLimit paddingTop);

	/**
	 * Sets the padding left.
	 *
	 * @param paddingLeft the new padding left
	 */
	void setPaddingLeft(SizeLimit paddingLeft);

	/**
	 * Sets the upper area padding top.
	 *
	 * @param upperAreaPaddingTop the new upper area padding top
	 */
	void setUpperAreaPaddingTop(SizeLimit upperAreaPaddingTop);

	/**
	 * Sets the under area padding top.
	 *
	 * @param underAreaPaddingTop the new under area padding top
	 */
	void setUnderAreaPaddingTop(SizeLimit underAreaPaddingTop);

	/**
	 * Sets the checks if is show break line.
	 *
	 * @param isShowBreakLine the new checks if is show break line
	 */
	void setIsShowBreakLine(ShowBreakLine isShowBreakLine);

	/**
	 * Sets the break line margin.
	 *
	 * @param breakLineMargin the new break line margin
	 */
	void setBreakLineMargin(SizeLimit breakLineMargin);

	/**
	 * Sets the middle area padding top.
	 *
	 * @param middleAreaPaddingTop the new middle area padding top
	 */
	void setMiddleAreaPaddingTop(SizeLimit middleAreaPaddingTop);

	/**
	 * Sets the break line margin top.
	 *
	 * @param breakLineMarginTop the new break line margin top
	 */
	void setBreakLineMarginTop(SizeLimit breakLineMarginTop);

	/**
	 * Sets the break line margin buttom.
	 *
	 * @param breakLineMarginButtom the new break line margin buttom
	 */
	void setBreakLineMarginButtom(SizeLimit breakLineMarginButtom);

}
