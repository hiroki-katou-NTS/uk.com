/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class RefundPadding.
 */
@Getter
public class RefundPadding extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The print type. */
	private PrintType printType;

	/** The padding top. */
	// 余白上 E_INP_001
	private SizeLimit paddingTop;

	/** The padding left. */
	// 余白左 E_INP_002, F_INP_003, G_INP_004
	private SizeLimit paddingLeft;

	/** The upper area padding top. */
	// 上段上 F_INP_001, G_INP_001
	private SizeLimit upperAreaPaddingTop;

	/** The under area padding top. */
	// 下段上 F_INP_002, G_INP_003
	private SizeLimit underAreaPaddingTop;

	/** The is show break line. */
	// 区切線の出力 F_SEL_001, G_SEL_001
	private ShowBreakLine isShowBreakLine;

	/** The break line margin. */
	// 区切線の補正 F_INP_004
	private SizeLimit breakLineMargin;

	/** The middle area padding top. */
	// 中段上 G_INP_002
	private SizeLimit middleAreaPaddingTop;

	/** The break line margin top. */
	// 区切線の補正上 G_INP_005
	private SizeLimit breakLineMarginTop;

	/** The break line margin buttom. */
	// 区切線の補正下 G_INP_006
	private SizeLimit breakLineMarginButtom;

	/**
	 * Instantiates a new refund padding.
	 */
	public RefundPadding() {
		super();
	}

	/**
	 * Creates the A 4 once person.
	 *
	 * @param companyCode
	 *            the company code
	 * @param paddingTop
	 *            the padding top
	 * @param paddingLeft
	 *            the padding left
	 * @return the refund padding
	 */
	public static RefundPadding createA4OncePerson(String companyCode, SizeLimit paddingTop,
			SizeLimit paddingLeft) {
		RefundPadding refundPadding = new RefundPadding();
		refundPadding.companyCode = companyCode;
		refundPadding.printType = PrintType.A4_ONCE_PERSON;
		refundPadding.paddingTop = paddingTop;
		refundPadding.paddingLeft = paddingLeft;
		return refundPadding;
	}

	/**
	 * Creates the A 4 two person.
	 *
	 * @param companyCode
	 *            the company code
	 * @param paddingTop
	 *            the padding top
	 * @param paddingLeft
	 *            the padding left
	 * @return the refund padding
	 */
	public static RefundPadding createA4TwoPerson(String companyCode, SizeLimit paddingLeft,
			SizeLimit upperAreaPaddingTop, SizeLimit underAreaPaddingTop,
			ShowBreakLine isShowBreakLine, SizeLimit breakLineMargin) {
		RefundPadding refundPadding = new RefundPadding();
		refundPadding.companyCode = companyCode;
		refundPadding.printType = PrintType.A4_TWO_PERSON;
		refundPadding.paddingLeft = paddingLeft;
		refundPadding.upperAreaPaddingTop = upperAreaPaddingTop;
		refundPadding.underAreaPaddingTop = underAreaPaddingTop;
		refundPadding.isShowBreakLine = isShowBreakLine;
		refundPadding.breakLineMargin = breakLineMargin;
		return refundPadding;
	}

	/**
	 * Creates the A 4 three person.
	 *
	 * @param companyCode
	 *            the company code
	 * @param paddingLeft
	 *            the padding left
	 * @param upperAreaPaddingTop
	 *            the upper area padding top
	 * @param underAreaPaddingTop
	 *            the under area padding top
	 * @param isShowBreakLine
	 *            the is show break line
	 * @param middleAreaPaddingTop
	 *            the middle area padding top
	 * @param breakLineMarginTop
	 *            the break line margin top
	 * @param breakLineMarginButtom
	 *            the break line margin buttom
	 * @return the refund padding
	 */
	public static RefundPadding createA4ThreePerson(String companyCode, SizeLimit paddingLeft,
			SizeLimit upperAreaPaddingTop, SizeLimit underAreaPaddingTop,
			ShowBreakLine isShowBreakLine, SizeLimit middleAreaPaddingTop,
			SizeLimit breakLineMarginTop, SizeLimit breakLineMarginButtom) {
		RefundPadding refundPadding = new RefundPadding();
		refundPadding.companyCode = companyCode;
		refundPadding.printType = PrintType.A4_THREE_PERSON;
		refundPadding.paddingLeft = paddingLeft;
		refundPadding.upperAreaPaddingTop = upperAreaPaddingTop;
		refundPadding.underAreaPaddingTop = underAreaPaddingTop;
		refundPadding.isShowBreakLine = isShowBreakLine;
		refundPadding.middleAreaPaddingTop = middleAreaPaddingTop;
		refundPadding.breakLineMarginTop = breakLineMarginTop;
		refundPadding.breakLineMarginButtom = breakLineMarginButtom;
		return refundPadding;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public RefundPadding(RefundPaddingGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.printType = memento.getPrintType();
		this.paddingTop = memento.getPaddingTop();
		this.paddingLeft = memento.getPaddingLeft();
		this.upperAreaPaddingTop = memento.getUpperAreaPaddingTop();
		this.underAreaPaddingTop = memento.getUnderAreaPaddingTop();
		this.isShowBreakLine = memento.getIsShowBreakLine();
		this.breakLineMargin = memento.getBreakLineMargin();
		this.middleAreaPaddingTop = memento.getMiddleAreaPaddingTop();
		this.breakLineMarginTop = memento.getBreakLineMarginTop();
		this.breakLineMarginButtom = memento.getBreakLineMarginButtom();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(RefundPaddingSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setPrintType(this.printType);
		memento.setPaddingTop(this.paddingTop);
		memento.setPaddingLeft(this.paddingLeft);
		memento.setUpperAreaPaddingTop(this.upperAreaPaddingTop);
		memento.setUnderAreaPaddingTop(this.underAreaPaddingTop);
		memento.setIsShowBreakLine(this.isShowBreakLine);
		memento.setBreakLineMargin(this.breakLineMargin);
		memento.setMiddleAreaPaddingTop(this.middleAreaPaddingTop);
		memento.setBreakLineMarginTop(this.breakLineMarginTop);
		memento.setBreakLineMarginButtom(this.breakLineMarginButtom);
	}

}
