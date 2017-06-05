/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

/**
 * The Interface RefundPaddingGetMemento.
 */
public interface RefundPaddingGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	String getCompanyCode();

	/**
	 * Gets the prints the type.
	 *
	 * @return the prints the type
	 */
	PrintType getPrintType();

	/**
	 * Gets the padding top.
	 *
	 * @return the padding top
	 */
	SizeLimit getPaddingTop();

	/**
	 * Gets the padding left.
	 *
	 * @return the padding left
	 */
	SizeLimit getPaddingLeft();

	/**
	 * Gets the upper area padding top.
	 *
	 * @return the upper area padding top
	 */
	SizeLimit getUpperAreaPaddingTop();

	/**
	 * Gets the under area padding top.
	 *
	 * @return the under area padding top
	 */
	SizeLimit getUnderAreaPaddingTop();

	/**
	 * Gets the checks if is show break line.
	 *
	 * @return the checks if is show break line
	 */
	ShowBreakLine getIsShowBreakLine();

	/**
	 * Gets the break line margin.
	 *
	 * @return the break line margin
	 */
	SizeLimit getBreakLineMargin();

	/**
	 * Gets the middle area padding top.
	 *
	 * @return the middle area padding top
	 */
	SizeLimit getMiddleAreaPaddingTop();

	/**
	 * Gets the break line margin top.
	 *
	 * @return the break line margin top
	 */
	SizeLimit getBreakLineMarginTop();

	/**
	 * Gets the break line margin buttom.
	 *
	 * @return the break line margin buttom
	 */
	SizeLimit getBreakLineMarginButtom();

}
