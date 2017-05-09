/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.refundsetting;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class QrfdtRefundPaddingSet.
 */
@Getter
@Setter
@Entity
@Table(name = "QRFDT_REFUND_PADDING_SET")
public class QrfdtRefundPaddingSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qrfdt refund padding set PK. */
	@EmbeddedId
	protected QrfdtRefundPaddingSetPK qrfdtRefundPaddingSetPK;

	/** The padding top. */
	@Column(name = "PADDING_TOP")
	private BigDecimal paddingTop;

	/** The padding left. */
	@Column(name = "PADDING_LEFT")
	private BigDecimal paddingLeft;

	/** The upper area padding top. */
	@Column(name = "UPPER_AREA_PADDING_TOP")
	private BigDecimal upperAreaPaddingTop;

	/** The under area padding top. */
	@Column(name = "UNDER_AREA_PADDING_TOP")
	private BigDecimal underAreaPaddingTop;

	/** The is show break line. */
	@Column(name = "IS_SHOW_BREAK_LINE")
	private BigDecimal isShowBreakLine;

	/** The break line margin. */
	@Column(name = "BREAK_LINE_MARGIN")
	private BigDecimal breakLineMargin;

	/** The middle area padding top. */
	@Column(name = "MIDDLE_AREA_PADDING_TOP")
	private BigDecimal middleAreaPaddingTop;

	/** The break line margin top. */
	@Column(name = "BREAK_LINE_MARGIN_TOP")
	private BigDecimal breakLineMarginTop;

	/** The break line margin buttom. */
	@Column(name = "BREAK_LINE_MARGIN_BUTTOM")
	private BigDecimal breakLineMarginButtom;

	/**
	 * Instantiates a new qrfdt refund padding set.
	 */
	public QrfdtRefundPaddingSet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qrfdtRefundPaddingSetPK != null ? qrfdtRefundPaddingSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QrfdtRefundPaddingSet)) {
			return false;
		}
		QrfdtRefundPaddingSet other = (QrfdtRefundPaddingSet) object;
		if ((this.qrfdtRefundPaddingSetPK == null && other.qrfdtRefundPaddingSetPK != null)
				|| (this.qrfdtRefundPaddingSetPK != null
						&& !this.qrfdtRefundPaddingSetPK.equals(other.qrfdtRefundPaddingSetPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.qrfdtRefundPaddingSetPK;
	}

}
