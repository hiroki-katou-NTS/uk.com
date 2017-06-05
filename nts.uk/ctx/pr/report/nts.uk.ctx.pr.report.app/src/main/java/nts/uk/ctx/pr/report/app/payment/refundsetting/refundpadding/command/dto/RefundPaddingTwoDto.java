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
 * The Class RefundPaddingTwoDto.
 */

@Getter
@Setter
public class RefundPaddingTwoDto {

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
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the refund padding
	 */
	public RefundPadding toDomain(String companyCode) {
		return new RefundPadding(new RefundPaddingTwoGetMementoImpl(companyCode, this));
	}

	/**
	 * The Class RefundPaddingTwoGetMementoImpl.
	 */
	public class RefundPaddingTwoGetMementoImpl implements RefundPaddingGetMemento {

		/** The company code. */
		private String companyCode;

		/** The dto. */
		private RefundPaddingTwoDto dto;

		/**
		 * Instantiates a new refund padding Two get memento impl.
		 *
		 * @param companyCode
		 *            the company code
		 * @param dto
		 *            the dto
		 */
		public RefundPaddingTwoGetMementoImpl(String companyCode, RefundPaddingTwoDto dto) {
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
			return PrintType.A4_TWO_PERSON;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getPaddingTop()
		 */
		@Override
		public SizeLimit getPaddingTop() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getPaddingLeft()
		 */
		@Override
		public SizeLimit getPaddingLeft() {
			return new SizeLimit(dto.paddingLeft);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getUpperAreaPaddingTop()
		 */
		@Override
		public SizeLimit getUpperAreaPaddingTop() {
			return new SizeLimit(dto.upperAreaPaddingTop);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getUnderAreaPaddingTop()
		 */
		@Override
		public SizeLimit getUnderAreaPaddingTop() {
			return new SizeLimit(dto.underAreaPaddingTop);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getIsShowBreakLine()
		 */
		@Override
		public ShowBreakLine getIsShowBreakLine() {
			return ShowBreakLine.valueOf(dto.isShowBreakLine);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding.
		 * RefundPaddingGetMemento#getBreakLineMargin()
		 */
		@Override
		public SizeLimit getBreakLineMargin() {
			return new SizeLimit(dto.breakLineMargin);
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
