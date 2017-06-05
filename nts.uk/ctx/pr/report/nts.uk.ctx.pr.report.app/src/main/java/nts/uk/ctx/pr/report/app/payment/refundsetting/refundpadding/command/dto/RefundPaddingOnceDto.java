/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.command.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.PrintType;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPadding;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.RefundPaddingGetMemento;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.ShowBreakLine;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.SizeLimit;

/**
 * The Class RefundPaddingThreeDto.
 */
@Getter
@Setter
public class RefundPaddingOnceDto {

	/** The upper area padding top. */
	private BigDecimal paddingTop;

	/** The middle area padding top. */
	private BigDecimal paddingLeft;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the refund padding
	 */
	public RefundPadding toDomain(String companyCode) {
		return new RefundPadding(new RefundPaddingOnceGetMementoImpl(companyCode, this));
	}

	/**
	 * The Class RefundPaddingThreeGetMementoImpl.
	 */
	public class RefundPaddingOnceGetMementoImpl implements RefundPaddingGetMemento {

		/** The company code. */
		private String companyCode;

		/** The dto. */
		private RefundPaddingOnceDto dto;

		/**
		 * Instantiates a new refund padding three get memento impl.
		 *
		 * @param companyCode
		 *            the company code
		 * @param dto
		 *            the dto
		 */
		public RefundPaddingOnceGetMementoImpl(String companyCode, RefundPaddingOnceDto dto) {
			this.companyCode = companyCode;
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getCompanyCode()
		 */
		@Override
		public String getCompanyCode() {
			return this.companyCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getPrintType()
		 */
		@Override
		public PrintType getPrintType() {
			return PrintType.A4_ONCE_PERSON;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getPaddingTop()
		 */
		@Override
		public SizeLimit getPaddingTop() {
			return new SizeLimit(this.dto.getPaddingTop());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getPaddingLeft()
		 */
		@Override
		public SizeLimit getPaddingLeft() {
			return new SizeLimit(this.dto.paddingLeft);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getUpperAreaPaddingTop()
		 */
		@Override
		public SizeLimit getUpperAreaPaddingTop() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getUnderAreaPaddingTop()
		 */
		@Override
		public SizeLimit getUnderAreaPaddingTop() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getIsShowBreakLine()
		 */
		@Override
		public ShowBreakLine getIsShowBreakLine() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getBreakLineMargin()
		 */
		@Override
		public SizeLimit getBreakLineMargin() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getMiddleAreaPaddingTop()
		 */
		@Override
		public SizeLimit getMiddleAreaPaddingTop() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getBreakLineMarginTop()
		 */
		@Override
		public SizeLimit getBreakLineMarginTop() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getBreakLineMarginButtom()
		 */
		@Override
		public SizeLimit getBreakLineMarginButtom() {
			return null;
		}

	}
}
