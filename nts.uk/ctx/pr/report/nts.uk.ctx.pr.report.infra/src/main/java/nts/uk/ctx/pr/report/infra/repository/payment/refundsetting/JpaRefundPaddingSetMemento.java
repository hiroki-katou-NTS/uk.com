/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.payment.refundsetting;

import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingSetMemento;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.SizeLimit;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.QrfdtRefundPaddingSet;
import nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.QrfdtRefundPaddingSetPK;

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
