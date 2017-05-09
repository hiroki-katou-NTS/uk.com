/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.refundsetting;

import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.SizeLimit;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.QrfdtRefundPaddingSet;

/**
 * The Class JpaRefundPaddingGetMemento.
 */
public class JpaRefundPaddingGetMemento implements RefundPaddingGetMemento {

	/** The type value. */
	private QrfdtRefundPaddingSet typeValue;

	/**
	 * Instantiates a new jpa refund padding get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaRefundPaddingGetMemento(QrfdtRefundPaddingSet typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.typeValue.getQrfdtRefundPaddingSetPK().getCcd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getPrintType()
	 */
	@Override
	public PrintType getPrintType() {
		return PrintType.valueOf(this.typeValue.getQrfdtRefundPaddingSetPK().getPrintType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getPaddingTop()
	 */
	@Override
	public SizeLimit getPaddingTop() {
		return new SizeLimit(this.typeValue.getPaddingTop());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getPaddingLeft()
	 */
	@Override
	public SizeLimit getPaddingLeft() {
		return new SizeLimit(this.typeValue.getPaddingLeft());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getUpperAreaPaddingTop()
	 */
	@Override
	public SizeLimit getUpperAreaPaddingTop() {
		return new SizeLimit(this.typeValue.getUpperAreaPaddingTop());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getUnderAreaPaddingTop()
	 */
	@Override
	public SizeLimit getUnderAreaPaddingTop() {
		return new SizeLimit(this.typeValue.getUnderAreaPaddingTop());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getIsShowBreakLine()
	 */
	@Override
	public ShowBreakLine getIsShowBreakLine() {
		return ShowBreakLine.valueOf(this.typeValue.getIsShowBreakLine());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getBreakLineMargin()
	 */
	@Override
	public SizeLimit getBreakLineMargin() {
		return new SizeLimit(this.typeValue.getBreakLineMargin());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getMiddleAreaPaddingTop()
	 */
	@Override
	public SizeLimit getMiddleAreaPaddingTop() {
		return new SizeLimit(this.typeValue.getMiddleAreaPaddingTop());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getBreakLineMarginTop()
	 */
	@Override
	public SizeLimit getBreakLineMarginTop() {
		return new SizeLimit(this.typeValue.getBreakLineMarginTop());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingGetMemento#getBreakLineMarginButtom()
	 */
	@Override
	public SizeLimit getBreakLineMarginButtom() {
		return new SizeLimit(this.typeValue.getBreakLineMarginButtom());
	}

}
