/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.refundsetting.refundpadding;

import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.SizeLimit;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.refundpadding.QrfdtRefundPaddingSet;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.refundpadding.QrfdtRefundPaddingSetPK;

/**
 * The Class JpaRefundPaddingSetMemento.
 */
public class JpaRefundPaddingSetMemento implements RefundPaddingSetMemento {

	/** The type value. */
	private QrfdtRefundPaddingSet typeValue;

	/**
	 * Instantiates a new jpa refund padding set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaRefundPaddingSetMemento(QrfdtRefundPaddingSet typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
	 * RefundPaddingSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		QrfdtRefundPaddingSetPK qrfdtRefundPaddingSetPK = new QrfdtRefundPaddingSetPK();
		qrfdtRefundPaddingSetPK.setCcd(companyCode);
		this.typeValue.setQrfdtRefundPaddingSetPK(qrfdtRefundPaddingSetPK);
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
		QrfdtRefundPaddingSetPK pk = this.typeValue.getQrfdtRefundPaddingSetPK();
		pk.setPrintType(printType.value);
		this.typeValue.setQrfdtRefundPaddingSetPK(pk);
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
		if (paddingTop == null) {
			return;
		}
		this.typeValue.setPaddingTop(paddingTop.v());

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
		if (paddingLeft == null) {
			return;
		}
		this.typeValue.setPaddingLeft(paddingLeft.v());
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
		if (upperAreaPaddingTop == null) {
			return;
		}
		this.typeValue.setUpperAreaPaddingTop(upperAreaPaddingTop.v());

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
		if (underAreaPaddingTop == null) {
			return;
		}
		this.typeValue.setUnderAreaPaddingTop(underAreaPaddingTop.v());
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
		if (isShowBreakLine == null) {
			return;
		}
		this.typeValue.setIsShowBreakLine(isShowBreakLine.value);
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
		if (breakLineMargin == null) {
			return;
		}
		this.typeValue.setBreakLineMargin(breakLineMargin.v());

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
		if (middleAreaPaddingTop == null) {
			return;
		}
		this.typeValue.setMiddleAreaPaddingTop(middleAreaPaddingTop.v());

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
		if (breakLineMarginTop == null) {
			return;
		}
		this.typeValue.setBreakLineMarginTop(breakLineMarginTop.v());
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
		if (breakLineMarginButtom == null) {
			return;
		}
		this.typeValue.setBreakLineMarginButtom(breakLineMarginButtom.v());
	}

}
